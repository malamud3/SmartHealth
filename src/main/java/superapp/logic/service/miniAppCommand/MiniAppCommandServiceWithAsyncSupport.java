package superapp.logic.service.miniAppCommand;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.miniAppCommand.MiniAppCommandService;

public interface MiniAppCommandServiceWithAsyncSupport extends MiniAppCommandService {

    public MiniAppCommandBoundary asyncHandle(MiniAppCommandBoundary miniAppCommandBoundary);


}
