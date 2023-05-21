package superapp.logic.service;

import superapp.Boundary.User.UserId;
import superapp.Boundary.superAppObjectBoundary;
import superapp.logic.Exceptions.PermissionDeniedException;

import java.util.List;
import java.util.Optional;

public interface ObjectsServiceWithAdminPermission extends  ObjectsService , ObjectServicePaginationSupported{

	//pagination Support

	public void deleteAllObjects(UserId userId) throws RuntimeException;


	public Optional<superAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail) throws PermissionDeniedException;


	public superAppObjectBoundary updateObject(String obj, String internal_obj_id, superAppObjectBoundary update , String userSuperApp , String userEmail) throws PermissionDeniedException;

}
