package superapp.controller;

import superapp.Boundary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.logic.service.miniAppCommand.MiniAppCommandServiceWithAdminPermission;
import superapp.logic.service.SuperAppObject.ObjectsServiceWithAdminPermission;
import superapp.logic.service.UsersServiceWithAdminPermission;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminRelatedAPIController {
    private UsersServiceWithAdminPermission userService;
    private MiniAppCommandServiceWithAdminPermission miniAppCommandService;
    private ObjectsServiceWithAdminPermission objectsService;

    @Autowired
    public AdminRelatedAPIController(UsersServiceWithAdminPermission userService,
    		MiniAppCommandServiceWithAdminPermission miniAppCommandService, ObjectsServiceWithAdminPermission objectsService) {
        this.userService = userService;
        this.miniAppCommandService = miniAppCommandService;
        this.objectsService = objectsService;

    }

    //DELETE: Delete All Users
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})

    public void deleteAllUsers(
    		@RequestParam(name="userSuperapp") String userSuperapp,
			@RequestParam(name="userEmail") String userEmail){
    	userService.deleteAllUsers(userSuperapp, userEmail);
    }

    //DELETE: Delete All Objects
    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})

    public void deleteAllObjects(
    		@RequestParam(name="userSuperapp") String userSuperapp,
			@RequestParam(name="userEmail") String userEmail){

    	objectsService.deleteAllObjects(new UserId(userSuperapp, userEmail));

    }

    //DELETE: Delete All Commands History
    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.DELETE})

    public void deleteAllCommands(
    		@RequestParam(name="userSuperapp") String userSuperapp,
			@RequestParam(name="userEmail") String userEmail){

    	miniAppCommandService.deleteAllCommands(new UserId(userSuperapp, userEmail));
    }


    //GET: Get All Users
    @RequestMapping(
            path = {"/superapp/admin/users/userSuperapp={superapp}&userEmail={email}&size={size}&page={page}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public List<UserBoundary> exportAllUsers(
            @PathVariable("superapp") String userSuperApp,
            @PathVariable("email") String email,
            @PathVariable("size") int size,
            @PathVariable("page") int page)
      throws RuntimeException {
        try {
            return userService.exportAllUsers( userSuperApp,  email,  size,  page);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get all users: " + e.getMessage());
        }
    }

    //GET: Get All MiniApps Commands History
    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MiniAppCommandBoundary> exportAllMiniAppsHistory() {
        try {
            List<MiniAppCommandBoundary> commands = miniAppCommandService.getAllCommands();
            if (commands.isEmpty()) {
                return new ArrayList<>();
            } else {
                return commands;
            }
        } catch (RuntimeException e) {
            System.err.println("Failed to get all commands: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //GET: Get Specific MiniApp Command History
    @RequestMapping(
            path = {"/superapp/admin/miniapp/{miniAppName}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MiniAppCommandBoundary> getSpecificMiniAppHistory(@PathVariable("miniAppName") String miniapp) {
        try {
            List<MiniAppCommandBoundary> miniappCommands = miniAppCommandService.getAllMiniAppCommands(miniapp);
            if (miniappCommands.isEmpty()) {
                return new ArrayList<>();
            } else {
                return miniappCommands;
            }
        } catch (RuntimeException e) {
            System.err.println("Failed to get miniapp history: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
