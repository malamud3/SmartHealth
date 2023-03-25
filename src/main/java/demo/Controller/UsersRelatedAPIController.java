package demo.Controller;

import demo.Model.UserBoundary;
import demo.Model.UserID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersRelatedAPIController {

	//GET : USER LOGIN
	@RequestMapping(
			path = {"/superapp/users/login/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary validuser(@PathVariable("superapp") String superapp,
								  @PathVariable("email") String email)
	{
		return new UserBoundary(superapp, email);

	}

	// GET: USER
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary retrieveUser(@PathVariable("superapp") String superapp,@PathVariable("email") String email) {
		return new UserBoundary(superapp,email);
	}

	// GET: ALL USERS
	@RequestMapping(
			path = {"/superapp/users/{superapp}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<UserBoundary> getAllUsers() {
		return new ArrayList<UserBoundary>();
	}


	//PUT: Update USER
	@RequestMapping(
			path = {"/superapp/users/update/{superapp}/{email}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary updateUser(@PathVariable("superapp") String superapp,
								   @PathVariable("email") String email,
								   @RequestBody UserBoundary updatedUser) {
		UserBoundary user = retrieveUser(superapp, email);
		user.setRole(updatedUser.getRole());
		user.setUsername(updatedUser.getUsername());
		user.setAvatar(updatedUser.getAvatar());
		return user;
	}

	// POST: Create USER
	@RequestMapping(
			path = {"/user"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary createUser(@PathVariable("superapp") String superapp, @RequestBody UserBoundary user) {
		// TODO - create the new user in the database and return the new UserBoundary object
		String email = user.getUserId().getEmail();
		String role = user.getRole();
		String username = user.getUsername();
		String avatar = user.getAvatar();
		UserBoundary newUser = new UserBoundary();
		newUser.setUserId(new UserID(superapp, email));
		newUser.setRole(role);
		newUser.setUsername(username);
		newUser.setAvatar(avatar);

		// Return the new UserBoundary object
		return newUser;
	}
}
