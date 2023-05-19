package superapp.logic.service;

import superapp.Boundary.MiniAppCommandBoundary;

public interface AsyncInvoke extends MiniAppCommandService{

    public MiniAppCommandBoundary asyncHandle(MiniAppCommandBoundary miniAppCommandBoundary);


}
