package superapp.Boundary;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SuperAppObjectBoundary {

    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private Location location;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

	public SuperAppObjectBoundary(String superapp, String internalObjectId) {
		objectId = new ObjectId(superapp, internalObjectId);

	}
	public SuperAppObjectBoundary(SuperAppObjectBoundary other) {
		this.objectId = other.objectId;
		this.type = other.type;
		this.alias = other.alias;
		this.active = other.active;
		this.creationTimestamp = other.creationTimestamp;
		this.location = other.location;
		this.createdBy = other.createdBy;
		this.objectDetails = other.objectDetails;

	}

	public SuperAppObjectBoundary() {

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

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}




	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}


	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}
	public void insertToObjectDetails(Object obj) {
		if (objectDetails.isEmpty()){
			objectDetails = new HashMap<>();
		}
		objectDetails.put(obj.toString(),obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperAppObjectBoundary other = (SuperAppObjectBoundary) obj;
		return objectId.equals(other.objectId);
	}
}
