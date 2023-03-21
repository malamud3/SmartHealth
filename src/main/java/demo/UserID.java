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
