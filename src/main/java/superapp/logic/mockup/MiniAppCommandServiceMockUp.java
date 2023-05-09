package superapp.logic.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;
import superapp.dal.MiniAppCommandRepository;
import superapp.data.mainEntity.MiniAppCommandEntity;
import superapp.logic.service.MiniAppCommandService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.utilitys.GeneralUtility;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class MiniAppCommandServiceMockUp implements MiniAppCommandService {

    private  String springApplicationName;
    private final MiniAppCommandRepository repository;
    private final MongoTemplate mongoTemplate;

    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    @PostConstruct
    public void init() {
        if (!mongoTemplate.collectionExists("COMMAND")) {
            mongoTemplate.createCollection("COMMAND");
        }
    }

    @Autowired
    public MiniAppCommandServiceMockUp(MongoTemplate mongoTemplate,
                                       MiniAppCommandRepository repository) {

        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public MiniAppCommandBoundary invokeCommand(MiniAppCommandBoundary miniAppCommandBoundary) throws RuntimeException {
        try {
            validatMiniappCommand(miniAppCommandBoundary);
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        miniAppCommandBoundary.getCommandId().setSuperapp(springApplicationName);
        miniAppCommandBoundary.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
        miniAppCommandBoundary.setInvocationTimestamp(new Date());
        MiniAppCommandEntity entity = boundaryToEntity(miniAppCommandBoundary);
        entity = this.repository.save(entity);
        return this.entityToBoundary(entity);


    }


    private void validatMiniappCommand(MiniAppCommandBoundary miniAppCommandBoundary) throws RuntimeException {
        GeneralUtility generalUtility = new GeneralUtility();
        if (miniAppCommandBoundary.getCommandAttributes() == null) {
            throw new RuntimeException("Command attributes are missing");
        }
        if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getCommand())) {
            throw new RuntimeException("Command details are missing");
        }
        if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getInvokedBy().getEmail()) ||
        generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getInvokedBy().getSuperapp())) {
            throw new RuntimeException("Invoked by is missing");
        }
        if (generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getTargetObject().getInternalObjectId()) ||
                generalUtility.isStringEmptyOrNull(miniAppCommandBoundary.getTargetObject().getSuperapp())) {
            throw new RuntimeException("Target object is missing");
        }
    }


    @Override
    public void deleteAllCommands() throws RuntimeException {
        try {
            this.repository.deleteAll();
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<MiniAppCommandBoundary> getAllCommands() throws RuntimeException {
        List<MiniAppCommandEntity> entities = this.repository.findAll();
        if (entities.isEmpty()){
            throw new RuntimeException("there aren't any commands");
        }
        List<MiniAppCommandBoundary> boundaries = new ArrayList<>();
        for (MiniAppCommandEntity entity : entities) {
            boundaries.add(this.entityToBoundary(entity));
        }
        return boundaries;
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) throws RuntimeException {
        List<MiniAppCommandEntity> entities = repository.findAllByCommandIdMiniapp(miniAppName);
        if (entities.isEmpty()){
            throw new RuntimeException("mini app history is empty");
        }
        return entities.stream()
                .map(this::entityToBoundary)
                .collect(Collectors.toList());
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
            entity.setTargetObject(new ObjectId());
        }
        else
            entity.setTargetObject(obj.getTargetObject());


        if (obj.getInvokedBy() == null) {
            entity.setInvokedBy(new UserId());
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
