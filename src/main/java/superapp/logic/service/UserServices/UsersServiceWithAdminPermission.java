package superapp.logic.service.UserServices;

import superapp.Boundary.User.UserBoundary;
import superapp.logic.service.UserServices.UsersService;

import java.util.List;

public interface UsersServiceWithAdminPermission extends UsersServiceWithNewUser {

	public void deleteAllUsers(String userSuperApp, String userEmail) throws RuntimeException;

	public List<UserBoundary> exportAllUsers(String userSuperApp, String userEmail, int size, int page ) throws RuntimeException;
}
