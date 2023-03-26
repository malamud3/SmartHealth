package demo.Controller;

import demo.Location;
import demo.Model.UserBoundary;
import demo.Model.UserID;
import demo.ObjectBoundary;
import demo.ObjectID;
import demo.OurObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

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

	//GET: all objects from DB
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public ArrayList<UserBoundary> getAllObjects()
	{
		return new ArrayList<UserBoundary>();
	}



	//POST : Create an object
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary createObject(@RequestBody ObjectBoundary objectBoundary) {
		// Create a new object with hardcoded data
		ObjectBoundary createdObject = new ObjectBoundary();
		createdObject.setObjectId(new ObjectID("hardcoded-superapp", "12345"));
		createdObject.setType("hardcoded-type");
		createdObject.setAlias("hardcoded-alias");
		createdObject.setActive(true);
		createdObject.setCreationTimestamp(new Date());
		createdObject.setLocation(new Location(3.124 , 3.5656));
		createdObject.setCreatedBy(new UserID("hardcoded-user"));
		createdObject.setOurObject(new OurObject("hardcoded-ourobject"));

		// Return the created object to the client
		return createdObject;
	}
}




