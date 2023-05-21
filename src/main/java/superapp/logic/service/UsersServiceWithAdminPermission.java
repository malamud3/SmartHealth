package superapp.logic.service;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.User.UserBoundary;

import java.util.List;

public interface UsersServiceWithAdminPermission extends UsersService {

	public void deleteAllUsers(String userSuperApp, String userEmail) throws RuntimeException;

	public List<UserBoundary> exportAllUsers(String userSuperApp, String userEmail, int size, int page ) throws RuntimeException;

	public List<MiniAppCommandBoundary> exportAllCommands(String userSuperApp, String userEmail, int size, int page ) throws RuntimeException;

	public MiniAppCommandBoundary exportSpecificCommands(String userSuperApp,String userEmail,int size, int page ) throws RuntimeException;

}
