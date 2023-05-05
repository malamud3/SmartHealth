package superapp.logic.service;

import superapp.Boundary.ObjectBoundary;
import superapp.data.mainEntity.SuperAppObjectEntity;

import java.util.Set;

public interface SuperAppObjectRelationshipService {
    public void bindParentAndChild(String parentId, String childId);
    public Set<ObjectBoundary> getAllParents(String objectId);
    public Set<ObjectBoundary> getAllChildren(String objectId);


}
