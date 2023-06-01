package superapp.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.scheduling.annotation.EnableAsync;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.UserId;

import java.util.Objects;

@EnableAsync
@Document(collection = "USERS")
public class UserEntity {
	@Id
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(userId, other.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public String toString() {
		return "UserEntity{" +
				"userId=" + userId +
				", role=" + role +
				", username='" + username + '\'' +
				", avatar='" + avatar + '\'' +
				'}';
	}
}
