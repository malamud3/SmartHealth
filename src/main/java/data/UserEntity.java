package data;

import java.util.UUID;


import SuperApp.Model.UserID;

import java.util.List;
import java.util.Optional;

public class UserEntity {
	
	private UUID user_ID;
	private UserRole role;//can change to enum
    private String username;
    private String avatar;
    private UserType userType;
    
    private List<UserEntity> connections; //TODO - check optional condition
	
    
    public UserEntity() {
		super();
	}


	public UUID getUser_ID() {
		return user_ID;
	}


	public void setUser_ID(UUID user_ID) {
		this.user_ID = user_ID;
	}


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
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
