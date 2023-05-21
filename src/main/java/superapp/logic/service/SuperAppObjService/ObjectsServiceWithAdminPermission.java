package superapp.logic.service.SuperAppObjService;

import superapp.Boundary.User.UserId;
import superapp.Boundary.SuperAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectsServiceWithAdminPermission extends ObjectsService, ObjectServicePaginationSupported {

	//pagination Support
	List<SuperAppObjectBoundary> getAllObjects(int size, int page);

	public void deleteAllObjects(UserId userId) throws RuntimeException;

	public Optional<SuperAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);


	public SuperAppObjectBoundary updateObject(String obj, String internal_obj_id, SuperAppObjectBoundary update , String userSuperApp , String userEmail) throws RuntimeException;

}
