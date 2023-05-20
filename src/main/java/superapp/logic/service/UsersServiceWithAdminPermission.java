package superapp.logic.service;

import superapp.Boundary.User.UserId;

public interface UsersServiceWithAdminPermission extends UsersService {
	
	public void deleteAllUsers(UserId userId) throws RuntimeException;

}
