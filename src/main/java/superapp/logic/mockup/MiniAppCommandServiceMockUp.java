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
    public MiniAppCommandBoundary invokeCommand(MiniAppCommandBoundary miniAppCommandBoundary) {
        if (miniAppCommandBoundary.getCommandAttributes() == null) {
            throw new RuntimeException("Command attributes are missing");
        }
        if (miniAppCommandBoundary.getCommand() == null) {
            throw new RuntimeException("Command details are missing");
        }
        if (miniAppCommandBoundary.getInvokedBy() == null) {
            throw new RuntimeException("Invoked by is missing");
        }
        if (miniAppCommandBoundary.getTargetObject() == null) {
            throw new RuntimeException("Target object is missing");
        }
        miniAppCommandBoundary.getCommandId().setSuperapp(springApplicationName);

        if (miniAppCommandBoundary.getCommand() != null) {
            miniAppCommandBoundary.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
            miniAppCommandBoundary.setInvocationTimestamp(new Date());
            MiniAppCommandEntity entity = boundaryToEntity(miniAppCommandBoundary);
            entity = this.repository.save(entity);
            return this.entityToBoundary(entity);
        } else {
            throw new RuntimeException("Command details are missing");
        }
    }


    @Override
    public void deleteAllCommands() {
        this.repository.deleteAll();
    }

    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
        List<MiniAppCommandEntity> entities = this.repository.findAll();
        List<MiniAppCommandBoundary> boundaries = new ArrayList<>();
        for (MiniAppCommandEntity entity : entities) {
            boundaries.add(this.entityToBoundary(entity));
        }
        return boundaries;
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
        List<MiniAppCommandEntity> entities = repository.findAllByCommandIdMiniapp(miniAppName);
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
