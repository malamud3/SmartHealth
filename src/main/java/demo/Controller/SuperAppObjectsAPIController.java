package demo.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.ObjectBoundary;

@RestController
public class SuperAppObjectsAPIController {
	
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	
	public ObjectBoundary retrieveObject(@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId)
	{
		return new ObjectBoundary(superapp,internalObjectId);
		
	}
	
	
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	
	public ObjectBoundary getAllObjects()
	{
		//TODO - get all objects from DB
		return new ObjectBoundary();
		
	}
	

}
