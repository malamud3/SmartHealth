package superapp.Boundary;

import superapp.Boundary.User.UserId;

import java.util.Objects;

public class CreatedBy {

    private UserId userId;

    public CreatedBy(){

    }

    public CreatedBy(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreatedBy other = (CreatedBy) obj;
        return this.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}

