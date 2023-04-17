package superapp.data;

import superapp.model.CommandId;
import superapp.model.ObjectId;
import superapp.model.UserId;

import java.util.Date;
import java.util.Map;

public class MiniAppCommandEntity {


    private CommandId commandId;
    private String command;
    private ObjectId targetObject;
    private Date invokcationTimeStap;
    private UserId invokedBy;
    private Map<String, Object> commandAttributed; // key-value

    public CommandId getCommandId() {
        return commandId;
    }

    public MiniAppCommandEntity() {
    }

    public MiniAppCommandEntity(String miniapp_name){
        this.commandId.setMiniApp(miniapp_name);
        this.commandId.setSuperapp("12");
        this.commandId.setInternalCommandId("231");
    }

    public MiniAppCommandEntity(CommandId commandId, String command, ObjectId targetObject, Date invocationTimestamp,
                                  UserId invokedBy, Map<String, Object> commandAttributes) {
        this.commandId = commandId;
        this.command = command;
        this.targetObject = targetObject;
        this.invokcationTimeStap = invocationTimestamp;
        this.invokedBy = invokedBy;
        this.commandAttributed = commandAttributes;
    }

    public MiniAppCommandEntity(String command, ObjectId targetObject, Date invocationTimestamp, UserId invokedBy,
                                  Map<String, Object> commandAttributes) {
        this(null, command, targetObject, invocationTimestamp, invokedBy, commandAttributes);
    }

    // GETS
    public String getCommand() {
        return command;
    }

    public ObjectId getTargetObject() {
        return targetObject;
    }

    public Date getInvokcationTimeStap() {
        return invokcationTimeStap;
    }

    public UserId getInvokedby() {
        return invokedBy;
    }

    public Map getCommandAttributed() {
        return commandAttributed;
    }

    // sets
    public void setCommandId(CommandId commandId) {
        this.commandId = commandId;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setTargetObject(ObjectId targetObject) {
        this.targetObject = targetObject;
    }

    public void setInvokcationTimeStap(Date invokcationTimeStap) {
        this.invokcationTimeStap = invokcationTimeStap;
    }

    public void setInvokedby(UserId invokedby) {
        this.invokedBy = invokedby;
    }

    public void setCommandAttributed(Map<String, Object> commandAttributed) {
        this.commandAttributed = commandAttributed;
    }
}
