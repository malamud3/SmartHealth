package superapp.logic.service.SuperAppObjService;

import superapp.Boundary.User.UserId;
import superapp.Boundary.superAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectsServiceWithAdminPermission extends ObjectsService, ObjectServicePaginationSupported {

	//pagination Support


	public void deleteAllObjects(UserId userId) throws RuntimeException;

	public Optional<superAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);


	public superAppObjectBoundary updateObject(String superapp, String internal_obj_id, superAppObjectBoundary update , String userSuperApp , String userEmail) throws RuntimeException;

}
