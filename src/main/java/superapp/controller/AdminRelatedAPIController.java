package superapp.controller;

import superapp.Boundary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.logic.service.MiniAppServices.MiniAppCommandServiceWithAdminPermission;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.service.UserServices.UsersServiceWithAdminPermission;
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
			path = {"/superapp/admin/users"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public List<UserBoundary> exportAllUsers(
			@RequestParam(name = "userSuperapp",required = true) String userSuperApp,
			@RequestParam(name ="userEmail",required = true) String email,
			@RequestParam(name = "size" , required = false , defaultValue = "8") int size,
			@RequestParam(name = "page" , required = false , defaultValue = "0") int page)
					throws RuntimeException {
		return userService.exportAllUsers(userSuperApp, email, size, page);
	}

	//GET: Get All MiniApps Commands History(export all commands)
	@RequestMapping(
			path = {"/superapp/admin/miniapp"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<MiniAppCommandBoundary> exportAllMiniAppsHistory(
			@RequestParam(name = "userSuperapp" , required = true ) String userSuperApp,
			@RequestParam(name ="userEmail",required = true ) String userEmail,
			@RequestParam(name="size" , defaultValue = "10" , required = false) int size ,
			@RequestParam(name = "page", defaultValue = "0" , required = false) int page){

		return miniAppCommandService.exportAllCommands(userSuperApp,userEmail,size,page);
	}

	//GET: Get Specific MiniApp Command History
	@RequestMapping(
			path = {"/superapp/admin/miniapp/{miniAppName}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<MiniAppCommandBoundary> getSpecificMiniAppHistory(
			@PathVariable("miniAppName") String miniapp ,
			@RequestParam(name = "userSuperapp" , required = true ) String userSuperApp,
			@RequestParam(name ="userEmail",required = true ) String userEmail,
			@RequestParam(name="size" , defaultValue = "10" , required = false) int size ,
			@RequestParam(name = "page", defaultValue = "0" , required = false) int page)
	{

		return miniAppCommandService.exportSpecificCommands(miniapp,userSuperApp,userEmail,size,page);

	}
}
