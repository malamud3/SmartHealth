package superapp.logic.service.SuperAppObject;

import superapp.Boundary.superAppObjectBoundary;

import java.util.Set;

public interface SuperAppObjectRelationshipService {
    public void bindParentAndChild(String parentId, String childId) throws RuntimeException;
    public Set<superAppObjectBoundary> getAllParents(String objectId) throws RuntimeException ;
    public Set<superAppObjectBoundary> getAllChildren(String objectId) throws RuntimeException;


}
