package superapp.data.mainEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import superapp.Boundary.ObjectId;
import superapp.data.mainEntity.SuperAppObjectEntity;

@Document(collection = "SUPER_APP_OBJECTS_RELATIONSHIPS")
public class SuperAppObjectRelationshipEntity {
    @Id
    private ObjectId id;
    @DBRef
    private ObjectId parentObject;
    @DBRef
    private ObjectId childObject;

    public SuperAppObjectRelationshipEntity(ObjectId id, SuperAppObjectEntity parentObject, SuperAppObjectEntity childObject) {
        this.id = id;
        this.parentObject = parentObject.getObjectId();
        this.childObject = childObject.getObjectId();
    }

    public SuperAppObjectRelationshipEntity() {

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getParentObject() {
        return parentObject;
    }

    public void setParentObject(ObjectId parentObject) {
        this.parentObject = parentObject;
    }

    public ObjectId getChildObject() {
        return childObject;
    }

    public void setChildObject(ObjectId childObject) {
        this.childObject = childObject;
    }
}
