package superapp.logic.service;

import java.util.List;

import superapp.Boundary.MiniAppCommandBoundary;

public interface MiniAppCommandService {


        public MiniAppCommandBoundary invokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary) throws RuntimeException;


        @Deprecated
        public List<MiniAppCommandBoundary> getAllCommands() throws RuntimeException;

        @Deprecated
        public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) throws RuntimeException;
        
        @Deprecated
        public void deleteAllCommands() throws RuntimeException;

}

