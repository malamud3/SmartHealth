package superapp.logic.service.SuperAppObjService;

import superapp.Boundary.User.UserId;
import superapp.Boundary.SuperAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectsServiceWithAdminPermission extends ObjectServicePaginationSupported {

	//pagination Support


	public void deleteAllObjects(UserId userId) throws RuntimeException;

	public SuperAppObjectBoundary updateObject(String superapp, String internal_obj_id, SuperAppObjectBoundary update , String userSuperApp , String userEmail) throws RuntimeException;

}
