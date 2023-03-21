package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRelatedAPIController {
	
		@RequestMapping(
				path = {"/superapp/{email}"},
				method = {RequestMethod.GET},
				produces = {MediaType.APPLICATION_JSON_VALUE})
		
		public UserBoundary validuser(@PathVariable("email") String email)
		{
			return new UserBoundary("323","liwaa",email);
			
		}
		

	

}
