package superapp.logic.service.MiniAppServices;

import superapp.Boundary.MiniAppCommandBoundary;

public interface MiniAppCommandServiceWithAsyncSupport extends MiniAppCommandService {

    public MiniAppCommandBoundary asyncHandle(MiniAppCommandBoundary miniAppCommandBoundary);


}
