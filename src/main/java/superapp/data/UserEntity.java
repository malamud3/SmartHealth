package superapp.data;


import java.util.List;

public class UserEntity {
	
	private String superapp;
	private String email;
	//private UserRole role;//can change to enum ?
	private String role;
    private String username;
    private String avatar;
    //private UserType userType; we don't have user type in boundary
    
    private List<UserEntity> connections; //TODO - check optional condition
	
    
    //TODO - find a way to get user miniapp origin and 'inject' it into userId 
    
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


	public UserEntity() {
		super();
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
    
    
   
    
	

}
