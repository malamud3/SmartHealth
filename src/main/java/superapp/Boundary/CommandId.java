package superapp.Boundary;


import java.util.Objects;

public class CommandId {
    private String superapp;
    private String miniapp;
    private String internalCommandId;

    public CommandId() {
    }

    public CommandId(String superapp, String miniapp, String internalCommandId)
    {
        this.superapp = superapp;
        this.miniapp = miniapp;
        this.internalCommandId = internalCommandId;
    }

    public String getSuperapp() {
        return superapp;
    }
    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }
    public String getMiniapp() {
        return miniapp;
    }
    public void setMiniapp(String miniApp) {
        this.miniapp = miniApp;
    }
    public String getInternalCommandId() {
        return internalCommandId;
    }
    public void setInternalCommandId(String internalCommandId) {
        this.internalCommandId = internalCommandId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommandId other = (CommandId) obj;
        return internalCommandId.equals(other.internalCommandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCommandId);
    }
}
