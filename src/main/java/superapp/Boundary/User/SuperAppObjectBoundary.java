package superapp.Boundary.User;

import superapp.Boundary.ObjectId;

public class SuperAppObjectBoundary {
    private ObjectId id;

    public SuperAppObjectBoundary(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
