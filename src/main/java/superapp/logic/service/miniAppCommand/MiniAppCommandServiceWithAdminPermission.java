package superapp.logic.service.miniAppCommand;

import superapp.Boundary.User.UserId;

public interface MiniAppCommandServiceWithAdminPermission extends MiniAppCommandServiceWithAsyncSupport {

	public void deleteAllCommands(UserId userId);

}
