package superapp.Boundary.User;


public class UserBoundary {
	
    private UserId userId;
    private String role;
    private String username;
    private String avatar;

	public UserBoundary(UserId userId, String role, String username, String avatar) {
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public UserBoundary() {

	}

	public UserBoundary(UserId userId) {
	}
	public UserBoundary(NewUserBoundary newUser, String springAppName) {
		this.userId = new UserId(springAppName,newUser.getEmail());
		this.role = newUser.getRole();
		this.username = newUser.getUsername();
		this.avatar = newUser.getAvatar();
	}


	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
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

	@Override
	public boolean equals(Object obj){
		UserBoundary other = (UserBoundary) obj;
		return this.userId.equals(other.userId);
	}
}
