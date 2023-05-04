package superapp.Boundary;

import org.springframework.data.mongodb.core.mapping.Document;
import superapp.Boundary.User.UserId;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ObjectBoundary {
	
    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private Location location;
    private UserId createdBy;
    private Map<String, Object> objectDetails;

	private Set<ObjectBoundary> parentObjects;
	private Set<ObjectBoundary> childObjects;
    
	public ObjectBoundary(String superapp,String internalObjectId) {
		objectId = new ObjectId(superapp, internalObjectId);
		
	}
	
	public ObjectBoundary() {
		
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
	
//	public OurObject getOurObject() {
//		return ourObject;
//	}
//	public void setOurObject(OurObject ourObject) {
//		this.ourObject = ourObject;
//		
//	}


	public Set<ObjectBoundary> getParentObjects() {
		return parentObjects;
	}

	public void setParentObjects(Set<ObjectBoundary> parentObjects) {
		this.parentObjects = parentObjects;
	}

	public Set<ObjectBoundary> getChildObjects() {
		return childObjects;
	}

	public void setChildObjects(Set<ObjectBoundary> childObjects) {
		this.childObjects = childObjects;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}
	
	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		ObjectBoundary other = (ObjectBoundary) obj;
		return objectId.equals(other.objectId);
	}
}
