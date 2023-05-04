package superapp.Boundary;

public class SuperAppObjectIdBoundary {
    private String superapp;
    private String internalObjectId;

    public SuperAppObjectIdBoundary(String superapp, String internalObjectId) {
        this.superapp = superapp;
        this.internalObjectId = internalObjectId;
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getInternalObjectId() {
        return internalObjectId;
    }

    public void setInternalObjectId(String internalObjectId) {
        this.internalObjectId = internalObjectId;
    }
}
