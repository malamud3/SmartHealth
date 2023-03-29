package SuperApp.Model;

public class ObjectID {
	
	private String superapp;
	private String internalObjectId;
	
	public ObjectID() {
	}
	
	public ObjectID(String superapp,String internalObjectId) {
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
		ObjectID other = (ObjectID) obj;
		return other.superapp.equals(this.superapp) && other.internalObjectId.equals(this.internalObjectId);
	}
}
