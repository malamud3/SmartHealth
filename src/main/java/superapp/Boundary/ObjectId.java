package superapp.Boundary;

import java.util.Objects;

public class ObjectId {
	
	private String superapp;
	private String internalObjectId;
	
	public ObjectId() {
	}
	
	public ObjectId(String superapp,String internalObjectId) {
		this.superapp = superapp;
		this.internalObjectId = internalObjectId;
	}

    public String getSuperapp() {
		return superapp;
	}
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}
	public String getInternalObjectId() {
		return internalObjectId;
	}
	public void setInternalObjectId(String internalObjectId) {
		this.internalObjectId = internalObjectId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectId other = (ObjectId) obj;
		return other.superapp.equals(this.superapp) && other.internalObjectId.equals(this.internalObjectId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(superapp, internalObjectId);
	}
}
