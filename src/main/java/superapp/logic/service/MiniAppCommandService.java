package superapp.logic.service;

import java.util.List;

import superapp.Boundary.MiniAppCommandBoundary;

public interface MiniAppCommandService {

        public Object InvokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary);
        public List<MiniAppCommandBoundary> getAllCommands();
        public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);
        public void deleteAllCommands();

}
