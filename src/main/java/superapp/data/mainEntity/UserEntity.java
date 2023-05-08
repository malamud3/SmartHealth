package superapp.data.mainEntity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.data.Enum.UserRole;

@Document(collection = "USERS")
public class UserEntity {

	private UserId userId;
	private UserRole role;
    private String username;
    private String avatar;

	public UserEntity(UserId userId, UserRole role, String username, String avatar) {
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}


	public UserEntity() {

	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
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
