package superapp.Boundary;

import org.apache.catalina.User;
import superapp.Boundary.User.UserId;
import superapp.data.SuperAppObjectEntity;

import java.util.Objects;

public class InvokedBy{

    private UserId userId;

    public InvokedBy() {
        this.userId=new UserId();
    }
    public InvokedBy(UserId userId) {
        this.userId=userId;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InvokedBy other = (InvokedBy) obj;
        return this.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public UserId getUserId() {
        return new UserId(userId.getSuperapp(), userId.getEmail());
    }

    public void setUserId(UserId userId) {
        this.userId = new UserId(userId.getSuperapp(),userId.getEmail());
    }
}
