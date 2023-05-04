package superapp.controller;

import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.NewUserBoundary;
import superapp.logic.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersRelatedAPIController {
	private UsersService usersService;
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
	
	public UserBoundary createUser(@RequestBody NewUserBoundary user) {
	     return usersService.createUser(user);
	}

	//GET: User Login And Retrieve User Details
	@RequestMapping(
			path = {"/superapp/users/login/{superapp}/{email}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public UserBoundary validUser(@PathVariable("superapp") String superapp,
								  @PathVariable("email") String email)
	{
		return this.usersService.login(superapp, email)
				.orElseThrow(()->new RuntimeException("could not find user with id: " + superapp + "_" + email));

	}


	//PUT: Update User
	@RequestMapping(
			path = {"/superapp/users/{superapp}/{userEmail}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
			/*produces = {MediaType.APPLICATION_JSON_VALUE} in the REST API 'update user' has no output
			but the userService's method 'updatedUser' returns user boundary, so to avoid troubles this method doesn't return User
			Boundary as a JSON*/  

	public void updateUser(@PathVariable("superapp") String superapp,
								   @PathVariable("userEmail") String email,
								   @RequestBody UserBoundary updatedUser) {
		
		usersService.updateUser(superapp, email, updatedUser);
	}

}
