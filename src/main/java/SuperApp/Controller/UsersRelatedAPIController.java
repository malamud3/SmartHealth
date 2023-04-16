package SuperApp.Controller;

import SuperApp.Model.UserBoundary;
import SuperApp.Model.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersRelatedAPIController {
	private UsersService usersService;
	
	@Autowired
	public UsersRelatedAPIController(UsersService usersService) {
		super();
		this.usersService = usersService;
	}

	//GET : USER LOGIN
	@RequestMapping(
			path = {"/superapp/users/login/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary validuser(@PathVariable("superapp") String superapp,
								  @PathVariable("email") String email)
	{
		return this.usersService.login(superapp, email)
				.orElseThrow(()->new RuntimeException("could not find user with id: " + superapp + "_" + email));

	}


	//PUT: Update USER
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary updateUser(@PathVariable("superapp") String superapp,
								   @PathVariable("userEmail") String email,
								   @RequestBody UserBoundary updatedUser) {
		
		return usersService.updateUser(superapp, email, updatedUser);
	}

	// POST: Create USER
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UserBoundary createUser(@RequestBody UserBoundary user) {
		
		return usersService.createUser(user);
	}

}
