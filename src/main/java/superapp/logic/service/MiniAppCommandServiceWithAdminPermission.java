package superapp.logic.service;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.User.UserId;

import java.util.List;

public interface MiniAppCommandServiceWithAdminPermission extends MiniAppCommandServiceWithAsyncSupport {
	
	public void deleteAllCommands(UserId userId);

	public List<MiniAppCommandBoundary> exportAllCommands(String userSuperApp, String userEmail, int size, int page ) throws RuntimeException;

	public MiniAppCommandBoundary exportSpecificCommands(String userSuperApp,String userEmail,int size, int page ) throws RuntimeException;

}
