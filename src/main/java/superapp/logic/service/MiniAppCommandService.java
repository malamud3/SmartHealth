package superapp.logic.service;

import java.util.List;

import superapp.Boundary.MiniAppCommandBoundary;

public interface MiniAppCommandService {


        public MiniAppCommandBoundary invokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary) throws RuntimeException;
        public List<MiniAppCommandBoundary> getAllCommands() throws RuntimeException;
        public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) throws RuntimeException;
        public void deleteAllCommands() throws RuntimeException;

}

