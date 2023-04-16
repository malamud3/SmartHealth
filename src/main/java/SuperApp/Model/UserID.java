package SuperApp.Model;

public class UserID {
	private String superapp;
	private String email;
	
	public UserID() {
		
	}


	public UserID(String email) {
		super();
		this.email = email;
		this.superapp = "2023b.Gil.Azani"; //TODO change to constant
	}
	
	public UserID(String superapp, String email) {
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
		UserID other = (UserID) obj;
		return this.email.equals(other.email) && this.superapp.equals(other.superapp);
	}
}
