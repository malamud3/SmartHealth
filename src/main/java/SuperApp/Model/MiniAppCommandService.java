package SuperApp.Model;

import java.util.List;

public interface MiniAppCommandService {

        public Object InvokeCommand(MiniAppCommandBoundary MiniAppCommandBoundary);
        public List<MiniAppCommandBoundary> getAllCommands();
        public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);
        public void deleteAllCommands();

}

