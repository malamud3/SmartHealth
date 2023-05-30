package superapp.logic.service.MiniAppServices;

import java.util.List;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.Exceptions.PermissionDeniedException;

public interface MiniAppCommandServiceWithAsyncSupport extends MiniAppCommandService {

    public MiniAppCommandBoundary asyncHandle(MiniAppCommandBoundary miniAppCommandBoundary);
    
    List<MiniAppCommandBoundary> exportSpecificCommands(String miniAppName, String userSuperApp, String userEmail, int size, int page) throws PermissionDeniedException;


}
