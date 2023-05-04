package superapp.logic.service;

import superapp.Boundary.ObjectBoundary;
import superapp.data.mainEntity.SuperAppObjectEntity;

import java.util.Set;

public interface SuperAppObjectRelationshipService {
    public void bindParentAndChild(String parentId, String childId);
    public Set<SuperAppObjectEntity> getAllParents(SuperAppObjectEntity object);
    public Set<SuperAppObjectEntity> getAllChildren(String objectId);

}
