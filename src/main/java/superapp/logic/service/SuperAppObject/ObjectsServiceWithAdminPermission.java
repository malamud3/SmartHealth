package superapp.logic.service.SuperAppObject;

import superapp.Boundary.User.UserId;
import superapp.logic.service.SuperAppObject.ObjectServicePaginationSupported;

public interface ObjectsServiceWithAdminPermission extends ObjectServicePaginationSupported {

	public void deleteAllObjects(UserId userId) throws RuntimeException;

}
