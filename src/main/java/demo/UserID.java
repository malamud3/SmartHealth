package demo;

public class UserID {
	private String superApp;
	private String email;
	
	public UserID() {
	}
	
	
	
	public UserID(String email) {
		super();
		this.email = email;
		this.superApp = "2023b.Gil.Azani"; //TODO change to constant!
	}
	
	public UserID(String superapp, String email) {
		super();
		this.email = email;
		this.superApp = superapp;
	}

	public String getSuperApp() {
		return superApp;
	}
	
	public void setSuperApp(String superApp) {
		this.superApp = superApp;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
