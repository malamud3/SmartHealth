package superapp.logic.service;

import superapp.Boundary.User.UserId;

public interface ObjectsServiceWithAdminPermission extends ObjectServicePaginationSupported{

	public void deleteAllObjects(UserId userId) throws RuntimeException;

}
