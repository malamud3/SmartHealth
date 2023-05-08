package superapp.controller;

import superapp.Boundary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.Boundary.User.UserBoundary;
import superapp.logic.service.MiniAppCommandService;
import superapp.logic.service.ObjectsService;
import superapp.logic.service.UsersService;

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
        this.miniAppCommandService = miniAppCommandService;
        this.objectsService = objectsService;

    }

    //DELETE: Delete All Users
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})

    public void deleteAllUsers() throws RuntimeException {
        try {
            userService.deleteAllUsers();
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to delete all users: " + e.getMessage());
        }


    }

    //DELETE: Delete All Objects
    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})

    public void deleteAllObjects() throws RuntimeException{
        try {
            objectsService.deleteAllObjects();
        }catch (RuntimeException e){
            throw new RuntimeException("can't delete all objects: "+e.getMessage());
        }

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

    public List<UserBoundary> getAllUsers() throws RuntimeException{
        try {
            return userService.getAllUsers();
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to get all users: " + e.getMessage());
        }
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
