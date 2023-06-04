package superapp.logic.service.SuperAppObjService;

import superapp.logic.service.SuperAppObjService.ObjectServicePaginationSupported;

public interface SuperAppObjectRelationshipService extends ObjectsServiceWithAdminPermission {

    void bindParentAndChild(String parentId, String childId, String userSuperApp, String userEmail);
}
