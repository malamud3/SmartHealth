package SuperApp.Controller;

import SuperApp.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController



public class AdminRelatedAPIController
{
    private UsersService userService;
    private MiniAppCommandService miniAppCommandService;
    private ObjectsService objectsService;

    @Autowired
    public AdminRelatedAPIController(UsersService userService,MiniAppCommandService miniAppCommandService,ObjectsService objectsService) {
        this.userService = userService;
        this.miniAppCommandService=miniAppCommandService;
        this.objectsService=objectsService;

    }

    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})
    public void deleteAllUsers() {
    	
    	userService.deleteAllUsers();

    }

    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})
    public void deleteAllObjects() {
        
    	objectsService.deleteAllObjects();
    	
    }


    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.DELETE})
    public void deleteAllCommands() {
    	
    	miniAppCommandService.deleteAllCommands();
        
    }


    // GET: ALL USERS
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserBoundary> getAllUsers() {
        
    	return userService.getAllUsers();
    }




    // GET: ALL MINI-APPS COMMANDS HISTORY
    @RequestMapping(
            path ={"/superapp/admin/miniapp"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MiniAppCommandBoundary> ExportAllMiniAppsHistory()
    {
        return miniAppCommandService.getAllCommands();
    }

    
    // GET: Specific MINI-APPS COMMANDS HISTORY
    @RequestMapping(
            path ={"/superapp/admin/miniapp/{miniAppName}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE}
            ,consumes = {MediaType.APPLICATION_JSON_VALUE})

    public List<MiniAppCommandBoundary> getSpecificMiniAppHistory(@PathVariable("miniAppName") String miniapp  )
    {
        return miniAppCommandService.getAllMiniAppCommands(miniapp);
    }
}
