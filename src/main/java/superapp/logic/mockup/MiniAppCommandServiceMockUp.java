package superapp.logic.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;
import superapp.dal.MiniAppCommandRepository;
import superapp.data.mainEntity.MiniAppCommandEntity;
import superapp.data.mainEntity.UserEntity;
import superapp.logic.service.MiniAppCommandService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class MiniAppCommandServiceMockUp implements MiniAppCommandService {

    private String springApplicationName;
    private MiniAppCommandRepository repository;
    private MongoTemplate mongoTemplate;

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
    public void MiniAppCommandServiceMockUp(MongoTemplate mongoTemplate ,
                                            MiniAppCommandRepository repository)
    {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Object InvokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary) {

        if (MiniAppCommandBoundary.getCommandAttributes() == null) {
            throw new RuntimeException("Command attributes are missing");
        }
        if (MiniAppCommandBoundary.getCommandId() == null) {
            throw new RuntimeException("Command ID is missing");
        }
        if (MiniAppCommandBoundary.getCommand() == null) {
            throw new RuntimeException("Command details are missing");
        }
        if (MiniAppCommandBoundary.getInvocationTimestamp() == null) {
            throw new RuntimeException("Invocation timestamp is missing");
        }
        if (MiniAppCommandBoundary.getInvokedBy() == null) {
            throw new RuntimeException("Invoked by is missing");
        }
        if (MiniAppCommandBoundary.getTargetObject() == null) {
            throw new RuntimeException("Target object is missing");
        }
        MiniAppCommandBoundary.getCommandId().setSuperapp(springApplicationName);
        MiniAppCommandBoundary.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
        MiniAppCommandEntity entity = this.boundaryToEntity(MiniAppCommandBoundary);
        this.repository.save(entity);
        return this.entityToBoundary(entity);
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
        boundary.setCommand(entity.getCommand());
        boundary.setTargetObject(entity.getTargetObject());
        boundary.setInvocationTimestamp(entity.getInvocationTimestamp());
        boundary.setInvokedBy(entity.getInvokedBy());

        return boundary;
    }


    public  MiniAppCommandEntity boundaryToEntity(MiniAppCommandBoundary obj)
    {
        MiniAppCommandEntity entity = new MiniAppCommandEntity();

        entity.setCommand(obj.getCommand().toString());
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

        //Date
        entity.setInvocationTimestamp(obj.getInvocationTimestamp());

        //Data
        entity.setCommandAttributes(obj.getCommandAttributes());

        return entity;
    }
}
