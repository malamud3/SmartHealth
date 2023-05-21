package superapp.logic.service;

import superapp.Boundary.superAppObjectBoundary;

import java.util.Set;

public interface SuperAppObjectRelationshipService extends ObjectServicePaginationSupported {


    @Deprecated
    public void bindParentAndChild(String parentId, String childId) throws RuntimeException;

    @Deprecated
    public Set<superAppObjectBoundary> getAllParents(String objectId) throws RuntimeException ;

    @Deprecated
    public Set<superAppObjectBoundary> getAllChildren(String objectId) throws RuntimeException;

    void bindParentAndChild(String parentId, String childId, String userSuperApp, String userEmail);
}
