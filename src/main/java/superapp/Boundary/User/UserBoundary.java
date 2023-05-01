package superapp.Boundary.User;


import superapp.data.Enum.UserRole;

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

	public static class NewUserBoundary {

		private String email;
		private String role;
		private String username;
		private String avatar;


		public NewUserBoundary() {
			super();
		}


		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
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

		public UserBoundary newUserBoundaryToUserBoundary(String springAppName) {
			UserBoundary user = new UserBoundary();

			user.setUserId(new UserId(springAppName, this.email));
			user.setRole(this.role);
			user.setUsername(this.username);
			user.setAvatar(this.avatar);

			return user;
		}

	}
}
