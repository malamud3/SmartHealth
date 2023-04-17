package superapp.model;

import java.util.Date;
import java.util.Map;

public class ObjectBoundary {
	
    private ObjectID objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private Location location;
    private UserID createdBy;

	private Map<String , ObjectBoundary> allObjects;

	public Map<String, ObjectBoundary> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(Map<String, ObjectBoundary> allObjects) {
		this.allObjects = allObjects;
	}

	private OurObject ourObject;


  
    
	public ObjectBoundary(String superapp,String internalObjectId) {
		objectId = new ObjectID(superapp, internalObjectId);
		
	}
	public ObjectBoundary() {
		
	}
	public ObjectID getObjectId() {
		return objectId;
	}
	public void setObjectId(ObjectID objectId) {
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
	public UserID getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserID createdBy) {
		this.createdBy = createdBy;
	}
	public OurObject getOurObject() {
		return ourObject;
	}
	public void setOurObject(OurObject ourObject) {
		this.ourObject = ourObject;
	}

	@Override
	public boolean equals(Object obj) {
		ObjectBoundary other = (ObjectBoundary) obj;
		return objectId.equals(other.objectId);
	}
}
