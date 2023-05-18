package superapp.Boundary;

public class TargetObject {

    private ObjectId objectId;


    public TargetObject(){

    }

    public TargetObject(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}
