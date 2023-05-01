package superapp.Boundary.User;

public class UserId {
	private String superapp;
	private String email;
	
	public UserId() {
		
	}


	public UserId(String email) {
		super();
		this.email = email;
		this.superapp = "2023b.Gil.Azani"; //TODO change to constant
	}
	
	public UserId(String superapp, String email) {
		super();
		this.email = email;
		this.superapp = superapp;
	}

	public String getSuperapp() {
		return superapp;
	}
	
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj){
		UserId other = (UserId) obj;
		return this.email.equals(other.email) && this.superapp.equals(other.superapp);
	}
}
