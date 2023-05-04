package superapp.data.mainEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;

import java.util.*;

@Document(collection = "SUPER_APP_OBJECTS")
public class SuperAppObjectEntity {
    @Id
    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private Location location;
    private UserId createdBy;
    private Map<String, Object> objectDetails;
    @DBRef
    @Field("parentObjects")
    private Set<SuperAppObjectEntity> parentObjects;
    @DBRef
    @Field("childObjects")
    private Set<SuperAppObjectEntity> childObjects;

    public SuperAppObjectEntity(String superapp,String internalObjectId) {
        objectId = new ObjectId(superapp, internalObjectId);
    }

    public SuperAppObjectEntity() {

    }

    public ObjectId getObjectId() {
        return objectId;
    }
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public UserId getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public boolean equals(Object obj) {
        ObjectBoundary other = (ObjectBoundary) obj;
        return objectId.equals(other.getObjectId());
    }

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

    public Set<SuperAppObjectEntity> getParentObjects() {
        return parentObjects;
    }

    public void setParentObjects(Set<SuperAppObjectEntity> parentObjects) {
        this.parentObjects = parentObjects;
    }

    public Set<SuperAppObjectEntity> getChildObjects() {
        return childObjects;
    }

    public void setChildObjects(Set<SuperAppObjectEntity> childObjects) {
        this.childObjects = childObjects;
    }

    public void addParentObject(SuperAppObjectEntity parentObject) {
        if (parentObjects == null) {
            parentObjects = new HashSet<>();
        }
        parentObjects.add(parentObject);
        parentObject.addChildObject(this);
    }

    public void addChildObject(SuperAppObjectEntity childObject) {
        if (childObjects == null) {
            childObjects = new HashSet<>();
        }
        childObjects.add(childObject);
        childObject.addParentObject(this);
    }

    public void removeParentObject(SuperAppObjectEntity parentObject) {
        if (parentObjects != null) {
            parentObjects.remove(parentObject);
            parentObject.removeChildObject(this);
        }
    }

    public void removeChildObject(SuperAppObjectEntity childObject) {
        if (childObjects != null) {
            childObjects.remove(childObject);
            childObject.removeParentObject(this);
        }
    }

}