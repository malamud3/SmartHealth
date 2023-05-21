package superapp.Boundary;

public class TargetObject {

    private ObjectId objectId;


    public TargetObject(){
        this.objectId = new ObjectId();

    }

    public TargetObject(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return new ObjectId(objectId.getSuperapp(), objectId.getInternalObjectId());
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = new ObjectId(objectId.getSuperapp(), objectId.getInternalObjectId());
    }
}
