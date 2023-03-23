package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRelatedAPIController {
	
		@RequestMapping(
				path = {"/superapp/users/login/{superapp}/{email}"},
				method = {RequestMethod.GET},
				produces = {MediaType.APPLICATION_JSON_VALUE})
		
		public UserBoundary validuser(@PathVariable("superapp") String superapp,
				@PathVariable("email") String email)
		{
			return new UserBoundary(superapp, email);
			
		}
		

	

}
