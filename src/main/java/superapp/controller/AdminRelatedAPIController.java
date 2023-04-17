package superapp.controller;

import superapp.model.*;
import superapp.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


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
    
    //DELETE: Delete All Users
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})
    
    public void deleteAllUsers() {
    	
    	userService.deleteAllUsers();

    }
    
    //DELETE: Delete All Objects
    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})
    
    public void deleteAllObjects() {
        
    	objectsService.deleteAllObjects();
    	
    }

    //DELETE: Delete All Commands History
    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.DELETE})
    
    public void deleteAllCommands() {
    	
    	miniAppCommandService.deleteAllCommands();
        
    }


    //GET: Get All Users
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    
    public List<UserBoundary> getAllUsers() {
        
    	return userService.getAllUsers();
    }




    //GET: Get All MiniApps Commands History
    @RequestMapping(
            path ={"/superapp/admin/miniapp"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    
    public List<MiniAppCommandBoundary> ExportAllMiniAppsHistory()
    {
        return miniAppCommandService.getAllCommands();
    }

    
    //GET: Get Specific MiniApp Command History
    @RequestMapping(
            path ={"/superapp/admin/miniapp/{miniAppName}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public List<MiniAppCommandBoundary> getSpecificMiniAppHistory(@PathVariable("miniAppName") String miniapp  )
    {
        return miniAppCommandService.getAllMiniAppCommands(miniapp);
    }
}
