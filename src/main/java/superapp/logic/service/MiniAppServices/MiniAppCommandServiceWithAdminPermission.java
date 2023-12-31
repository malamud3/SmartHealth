package superapp.logic.service.MiniAppServices;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.User.UserId;
import superapp.logic.Exceptions.PermissionDeniedException;

import java.util.List;

public interface MiniAppCommandServiceWithAdminPermission extends MiniAppCommandServiceWithAsyncSupport {

	public void deleteAllCommands(UserId userId);

	public List<MiniAppCommandBoundary> exportAllCommands(String userSuperApp, String userEmail, int size, int page ) throws PermissionDeniedException;
}
