package superapp.logic.Mongo;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.UserRole;
import superapp.data.MiniAppCommandEntity;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import superapp.logic.Exceptions.*;
import superapp.logic.service.MiniAppServices.MiniAppCommandServiceWithAdminPermission;
import superapp.logic.utilitys.GeneralUtility;
import superapp.logic.utilitys.UserUtility;
import superapp.miniapps.commands.Command;
import superapp.miniapps.commands.CommandInterface;
import superapp.miniapps.commands.CommandsEnum;

import java.util.*;

@Service
public class MiniAppCommandServiceRepo implements MiniAppCommandServiceWithAdminPermission, CommandInterface {

	private  String springApplicationName;
	private final MiniAppCommandCrud commandRepository;
	private final UserCrud userCrud;//for permission checks
	private final MongoTemplate mongoTemplate;
	private final UserUtility userUtility;

	private final SuperAppObjectCrud objectRepository;

	private JmsTemplate jmsTemplate;

	private ObjectMapper jackson;

	private ApplicationContext applicationContext;


    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }



	// this method injects a configuration value of spring
	@Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
	public void setSpringApplicationName(String springApplicationName) {
		this.springApplicationName = springApplicationName;
	}

	@Autowired
	public MiniAppCommandServiceRepo(MongoTemplate mongoTemplate,
									 MiniAppCommandCrud repository, ObjectMapper jackson, UserCrud userCrud, SuperAppObjectCrud objectRepository) {

		this.commandRepository = repository;
		this.userCrud = userCrud;
		this.mongoTemplate = mongoTemplate;
		this.jackson = jackson;
		this.userUtility = new UserUtility(userCrud);
		this.objectRepository = objectRepository;
	}



	@PostConstruct
	public void init() {
		if (!mongoTemplate.collectionExists("COMMAND")) {
			mongoTemplate.createCollection("COMMAND");
			this.jackson = new ObjectMapper();
		}
		if (!mongoTemplate.collectionExists("USERS")) {
			mongoTemplate.createCollection("USERS");
		}
	}

	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
		this.jmsTemplate.setDeliveryDelay(3000L);
	}


	@Override
	public MiniAppCommandBoundary asyncHandle(MiniAppCommandBoundary miniAppCommandBoundary) {
		miniAppCommandBoundary.setInvocationTimestamp(miniAppCommandBoundary.getInvocationTimestamp());
		if (miniAppCommandBoundary.getCommandAttributes() == null) {
			miniAppCommandBoundary.setCommandAttributes(new HashMap<>());
		}
		miniAppCommandBoundary.getCommandAttributes().put("status", "in-process...");
		miniAppCommandBoundary.setCommand(miniAppCommandBoundary.getCommand());
		miniAppCommandBoundary.setTargetObject(new TargetObject(miniAppCommandBoundary.getTargetObject().getObjectId()));
		miniAppCommandBoundary.setInvokedBy(new InvokedBy(miniAppCommandBoundary.getInvokedBy().getUserId()));

		try {
			String json = this.jackson
					.writeValueAsString(miniAppCommandBoundary);

			// send json to InvocationMiniAppQueue
			System.err.println("*** sending: " + json);
			this.jmsTemplate
			.convertAndSend("InvocationMiniAppQueue", json);
			return miniAppCommandBoundary;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@JmsListener(destination = "InvocationMiniAppQueue")
	public void handleInvokeMiniApp(String json) {
		try {
			System.err.println(
					this.entityToBoundary(
							this.mongoTemplate
							.save(
									this.boundaryToEntity(
											this.setStatus(
													this.jackson
													.readValue(json, MiniAppCommandBoundary.class),
													"accepted..AsyncDone")))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MiniAppCommandBoundary setStatus(MiniAppCommandBoundary miniAppCommandBoundary, String status) {
		if (miniAppCommandBoundary.getCommandAttributes() == null) {
			miniAppCommandBoundary.setCommandAttributes(new HashMap<>());
		}
		miniAppCommandBoundary.getCommandAttributes().put("status", status);

		return miniAppCommandBoundary;
	}



	public boolean isObjectActive(ObjectId objectId) {
		Optional<SuperAppObjectEntity> optionalEntity = this.objectRepository.findById(objectId);
		if (optionalEntity.isPresent()) {
			SuperAppObjectEntity objectEntity = optionalEntity.get();
			return objectEntity.getActive();
		} else {
			throw new ObjectNotFoundException("Could not find object with ID: " + objectId);
		}
	}

	@Override
	public MiniAppCommandBoundary invokeCommand(MiniAppCommandBoundary miniAppCommandBoundary) throws RuntimeException {
		validateMiniappCommand(miniAppCommandBoundary);
		UserEntity userEntity = userUtility.checkUserExist(miniAppCommandBoundary.getInvokedBy().getUserId());

		if(userEntity.getRole().equals(UserRole.MINIAPP_USER) && isObjectActive(miniAppCommandBoundary.getTargetObject().getObjectId())) {
			miniAppCommandBoundary.setInvokedBy(new InvokedBy(new UserId(miniAppCommandBoundary.getInvokedBy().getUserId().getSuperapp(), miniAppCommandBoundary.getInvokedBy().getUserId().getEmail())));
			miniAppCommandBoundary.setTargetObject(new TargetObject(new ObjectId(miniAppCommandBoundary.getTargetObject().getObjectId().getSuperapp(), miniAppCommandBoundary.getTargetObject().getObjectId().getInternalObjectId())));
			miniAppCommandBoundary.setInvocationTimestamp(new Date());
			if (miniAppCommandBoundary.getCommandAttributes() == null) {
				miniAppCommandBoundary.setCommandAttributes(new HashMap<>());
			}else {
				createCommand(CommandsEnum.valueOf(miniAppCommandBoundary.getCommand()), miniAppCommandBoundary);
			}
			MiniAppCommandEntity entity = boundaryToEntity(miniAppCommandBoundary);
			entity = this.commandRepository.save(entity);
			return this.entityToBoundary(entity);
		}
		throw new ObjectBadRequest("Can't invoke");
	}


	@Override
	public Object createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary){
		Command commandBean = null;

		try {
			commandBean = this.applicationContext
					.getBean(commandsEnum.toString(), Command.class);
		}catch (Exception e) {
			throw new CommandNotFoundException("could not find command: " + commandBoundary.getCommand());
		}

        return commandBean.execute(commandBoundary);

    }





	private void validateMiniappCommand(MiniAppCommandBoundary miniAppCommandBoundary) throws RuntimeException {
		GeneralUtility generalUtility = new GeneralUtility();
		if (miniAppCommandBoundary.getCommandAttributes() == null) {
			throw new RuntimeException("Command attributes are missing");
		}
		if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getCommand())) {
			throw new RuntimeException("Command details are missing");
		}
		if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getInvokedBy().getUserId().getSuperapp()) ||
				generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getInvokedBy().getUserId().getEmail())) {
			throw new RuntimeException("Invoked by is missing");
		}
		if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getTargetObject().getObjectId().getInternalObjectId()) ||
				generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getTargetObject().getObjectId().getSuperapp())) {
			throw new RuntimeException("Target object is missing");
		}
	}


	@Override
	@Deprecated
	public void deleteAllCommands() throws RuntimeException {
		throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
	}

	@Override
	public void deleteAllCommands(UserId userId) {
		UserEntity userEntity = userUtility.checkUserExist(userId);

		if (!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new PermissionDeniedException("You do not have permission to delete all commands");
		}
		this.commandRepository.deleteAll();
		}


	@Override
	public List<MiniAppCommandBoundary> exportAllCommands(String userSuperApp, String userEmail, int size, int page) throws RuntimeException {

		UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));

		if (!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new PermissionDeniedException("User doesn't have permission to export all commands");
		}

		return this.commandRepository
				.findAll(PageRequest.of(page, size, Sort.Direction.ASC,"invocationTimestamp‏"))
				.stream() // Stream<CommandEntity>
				.map(this::entityToBoundary) // Stream<CommandBoundary>
				.toList(); // List<CommandBoundary>
	}

	@Override
	public List<MiniAppCommandBoundary> exportSpecificCommands(String miniAppName, String userSuperApp, String userEmail, int size, int page) throws PermissionDeniedException {
		UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));

		if (!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new PermissionDeniedException("User doesn't have permission to access all commands");
		}

		return this.commandRepository
				.findAllByCommandIdMiniapp(miniAppName,
						PageRequest.of(page, size, Sort.Direction.ASC, "command", "invocationTimestamp", "commandId"))
				.stream() // Stream<CommandEntity>
				.map(this::entityToBoundary) // Stream<CommandBoundary>
				.toList(); // List<CommandBoundary>
	}

	@Override
	@Deprecated
	public List<MiniAppCommandBoundary> getAllCommands() throws RuntimeException {
		throw new DepreacatedOpterationException("Dont use this methode anymore");
	}

	@Override
	@Deprecated
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) throws RuntimeException {
		throw new DepreacatedOpterationException();
	}




	public MiniAppCommandBoundary entityToBoundary(MiniAppCommandEntity entity) {
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();

		boundary.setCommandId(entity.getCommandId());
		boundary.setCommandAttributes(entity.getCommandAttributes());
		boundary.setCommand(entity.getCommand());
		boundary.setTargetObject(entity.getTargetObject());
		boundary.setInvocationTimestamp(entity.getInvocationTimestamp());
		boundary.setInvokedBy(entity.getInvokedBy());

		return boundary;
	}


	public  MiniAppCommandEntity boundaryToEntity(MiniAppCommandBoundary obj)
	{
		MiniAppCommandEntity entity = new MiniAppCommandEntity();

		entity.setCommand(obj.getCommand());
		if (obj.getCommandId() == null) {
			entity.setCommandId(new CommandId());
		}
		else
			entity.setCommandId(obj.getCommandId());

		if (obj.getCommand() == null) {
			entity.setCommand("");
		}
		else
			entity.setCommand(obj.getCommand());


		if (obj.getTargetObject() == null) {
			entity.setTargetObject(new TargetObject(new ObjectId()));
		}
		else
			entity.setTargetObject(obj.getTargetObject());


		if (obj.getInvokedBy() == null) {
			entity.setInvokedBy(new InvokedBy());
		}else {
			entity.setInvokedBy(obj.getInvokedBy());
		}
		if (obj.getCommandAttributes() == null){
			entity.setCommandAttributes(new HashMap<>() {
			});
		}else {
			entity.setCommandAttributes(obj.getCommandAttributes());
		}

		//Date
		entity.setInvocationTimestamp(obj.getInvocationTimestamp());

		//Data
		entity.setCommandAttributes(obj.getCommandAttributes());

		return entity;
	}
}
