package superapp.Boundary;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TargetObject other = (TargetObject) obj;
        return objectId.equals(other.objectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }
}
