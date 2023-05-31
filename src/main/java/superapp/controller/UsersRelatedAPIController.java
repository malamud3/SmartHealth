package superapp.controller;

import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.NewUserBoundary;
import superapp.logic.service.UserServices.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersRelatedAPIController {
	private final UsersService usersService;
	private String springAppName;


	// this method injects a configuration value of spring
	@Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
	public void setSpringApplicationName(String springApplicationName) {
		this.springAppName = springApplicationName;
	}

	@Autowired
	public UsersRelatedAPIController(UsersService usersService) {
		super();
		this.usersService = usersService;
	}

	//POST: Create User
	@RequestMapping(
			path = {"/superapp/users"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary createUser(@RequestBody NewUserBoundary user) throws RuntimeException {
		return this.usersService.createUser(user);
	}

	//GET: User Login And Retrieve User Details
	@RequestMapping(
			path = {"/superapp/users/login/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary validUser(@PathVariable("superapp") String superapp,
			@PathVariable("email") String email) {

		return this.usersService.login(superapp, email);
	}

	//PUT: Update User
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary updateUser(
			@PathVariable("superapp") String superapp,
			@PathVariable("userEmail") String email,
			@RequestBody UserBoundary updatedUser) throws RuntimeException{

		return this.usersService.updateUser(superapp, email, updatedUser);

	}

}
