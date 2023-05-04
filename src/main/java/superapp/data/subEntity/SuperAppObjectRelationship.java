package superapp.data.subEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import superapp.Boundary.ObjectId;
import superapp.data.mainEntity.SuperAppObjectEntity;

@Document(collection = "SUPER_APP_OBJECTS_RELATIONSHIPS")
public class SuperAppObjectRelationship {
    @Id
    private ObjectId id;
    @DBRef
    @Field("parentObjectId")
    private ObjectId parentObject;
    @DBRef
    @Field("childObjectId")
    private ObjectId childObject;

    public SuperAppObjectRelationship(ObjectId id, SuperAppObjectEntity parentObject, SuperAppObjectEntity childObject) {
        this.id = id;
        this.parentObject = parentObject.getObjectId();
        this.childObject = childObject.getObjectId();
    }

    public SuperAppObjectRelationship() {

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
