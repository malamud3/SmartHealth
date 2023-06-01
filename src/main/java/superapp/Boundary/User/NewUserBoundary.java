package superapp.Boundary.User;

import superapp.Boundary.MiniAppCommandBoundary;

import java.util.Objects;

public class NewUserBoundary {

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewUserBoundary other = (NewUserBoundary) obj;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

