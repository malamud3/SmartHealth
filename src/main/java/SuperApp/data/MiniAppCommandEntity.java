package SuperApp.data;

import SuperApp.Model.CommandId;
import SuperApp.Model.ObjectID;
import SuperApp.Model.UserID;

import java.util.Date;
import java.util.Map;

public class MiniAppCommandEntity {


    private CommandId commandId;
    private String command;
    private ObjectID targetObject;
    private Date invokcationTimeStap;
    private UserID invokedBy;
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

    public MiniAppCommandEntity(CommandId commandId, String command, ObjectID targetObject, Date invocationTimestamp,
                                  UserID invokedBy, Map<String, Object> commandAttributes) {
        this.commandId = commandId;
        this.command = command;
        this.targetObject = targetObject;
        this.invokcationTimeStap = invocationTimestamp;
        this.invokedBy = invokedBy;
        this.commandAttributed = commandAttributes;
    }

    public MiniAppCommandEntity(String command, ObjectID targetObject, Date invocationTimestamp, UserID invokedBy,
                                  Map<String, Object> commandAttributes) {
        this(null, command, targetObject, invocationTimestamp, invokedBy, commandAttributes);
    }

    // GETS
    public String getCommand() {
        return command;
    }

    public ObjectID getTargetObject() {
        return targetObject;
    }

    public Date getInvokcationTimeStap() {
        return invokcationTimeStap;
    }

    public UserID getInvokedby() {
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

    public void setTargetObject(ObjectID targetObject) {
        this.targetObject = targetObject;
    }

    public void setInvokcationTimeStap(Date invokcationTimeStap) {
        this.invokcationTimeStap = invokcationTimeStap;
    }

    public void setInvokedby(UserID invokedby) {
        this.invokedBy = invokedby;
    }

    public void setCommandAttributed(Map<String, Object> commandAttributed) {
        this.commandAttributed = commandAttributed;
    }
}
