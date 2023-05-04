package superapp.logic.mockup;

import superapp.Boundary.*;
import superapp.Boundary.User.UserId;
import superapp.data.mainEntity.MiniAppCommandEntity;
import superapp.logic.service.MiniAppCommandService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MiniAppCommandServiceMockUp implements MiniAppCommandService {

    private Map<String, MiniAppCommandEntity> dbMockup;
    private String springApplicationName;

    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    // this method is invoked after values are injected to instance
    @PostConstruct
    public void init() {
        // create a thread safe map
        this.dbMockup = Collections.synchronizedMap(new HashMap<>());
        System.err.println("***** " + this.springApplicationName);
    }


    @Override
    public Object InvokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary) {
        if (MiniAppCommandBoundary.getCommand() == null) {
            throw new RuntimeException("Command is missing");
        }
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
        this.dbMockup.put(springApplicationName,entity);
        return this.boundaryToEntity(MiniAppCommandBoundary);
    }

    @Override
    public List<MiniAppCommandBoundary> getAllCommands() {
        return this.dbMockup.values()
                .stream() // Stream<MessageEntity>
                .map(this::entityToBoundary) // Stream<Message>
                .toList(); // List<Message>
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
        List<MiniAppCommandBoundary> allminiAppCommand = getAllCommands();
        List<MiniAppCommandBoundary> rv = new ArrayList<>();
        return allminiAppCommand
                .stream() // Stream<MessageEntity>
                .filter(obj->obj.getCommandId().getMiniapp().equalsIgnoreCase(miniAppName))
                .toList();
    }

    @Override
    public void deleteAllCommands() {
        this.dbMockup.clear();

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
