package superapp.logic.service;

import superapp.Boundary.User.UserId;

public interface UsersServiceWithAdminPermission extends UsersService {

	public void deleteAllUsers(UserId userId) throws RuntimeException;

	public void exportAllUsers(String superapp,String userEmail,String userSuperApp,int size, int page ) throws RuntimeException;

	public void exportAllCommands(String superapp,String userEmail,String userSuperApp,int size, int page ) throws RuntimeException;

	public void exportSpecificCommands(String superapp,String userEmail,String userSuperApp,int size, int page ) throws RuntimeException;

}
