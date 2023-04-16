package SuperApp.Controller;

import SuperApp.Model.UserBoundary;
import SuperApp.Model.UserID;
import demo.logic.DataManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import Logic.Mockup.UsersServiceMockup;

@RestController
public class UsersRelatedAPIController {
	private UsersServiceMockup userDataManager;
	
	@Autowired
	public UsersRelatedAPIController(UsersServiceMockup userDataManager) {
		super();
		this.userDataManager = userDataManager;
	}

	//GET : USER LOGIN
	@RequestMapping(
			path = {"/superapp/users/login/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary validuser(@PathVariable("superapp") String superapp,
								  @PathVariable("email") String email)
	{
		return this.userDataManager.login(superapp, email)
				.orElseThrow(()->new RuntimeException("could not find user with id: " + superapp + "_" + email));

	}

	// GET: USER
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary retrieveUser(@PathVariable("superapp") String superapp,@PathVariable("email") String email) {
		return new UserBoundary(superapp,email);
	}




	//PUT: Update USER
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{useremail}"},
			method = {RequestMethod.PUT},
//			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary updateUser(@PathVariable("superapp") String superapp,
								   @PathVariable("useremail") String email,
								   @RequestBody UserBoundary updatedUser) {
		UserBoundary user = retrieveUser(superapp, email);
		user.setRole(updatedUser.getRole());
		user.setUsername(updatedUser.getUsername());
		user.setAvatar(updatedUser.getAvatar());
		return user;
	}

	// POST: Create USER
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary createUser(@RequestBody UserBoundary user) {
		String email = user.getUserId().getEmail();
		String role = user.getRole();
		String username = user.getUsername();
		String avatar = user.getAvatar();
		UserBoundary newUser = new UserBoundary();
		newUser.setUserId(new UserID(user.getUserId().getSuperapp(), email));
		newUser.setRole(role);
		newUser.setUsername(username);
		newUser.setAvatar(avatar);
		return newUser;
	}






}
