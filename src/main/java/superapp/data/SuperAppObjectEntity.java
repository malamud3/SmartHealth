package superapp.data;

import org.springframework.data.mongodb.core.mapping.Document;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;

import java.util.Date;
import java.util.Map;
@Document
public class SuperAppObjectEntity{

    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private Location location;
    private UserId createdBy;
    private Map<String, Object> objectDetails;
    

//    private Map <String , ObjectBoundary> allObjects;
//
//    public void setAllObjects(Map<String, ObjectBoundary> allObjects) {
//        this.allObjects = allObjects;
//    }
//
//    public Map<String, ObjectBoundary> getAllObjects() {
//        return allObjects;
//    }
//
//    private OurObject ourObject;



    public SuperAppObjectEntity(String superapp,String internalObjectId) {
        objectId = new ObjectId(superapp, internalObjectId);

    }

    public SuperAppObjectEntity() {

    }

    //check
    public void SuperAppObjectEntity() {

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
        return createdBy;
    }
    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }
//    public OurObject getOurObject() {
//        return ourObject;
//    }
//    public void setOurObject(OurObject ourObject) {
//        this.ourObject = ourObject;
//    }

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



}
