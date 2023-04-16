package SuperApp.Controller;

import SuperApp.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SuperAppObjectsAPIController {


	private ObjectsService objectsService;

	@Autowired
	public SuperAppObjectsAPIController(ObjectsService objectsService) {
		this.objectsService = objectsService;

	}


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

	public  static List<ObjectBoundary> getAllObjects()
	{
		return  new ArrayList<ObjectBoundary>();
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


	// PUT: Update Object
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectid}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary updateObject(@PathVariable("superapp") String superapp,
									   @PathVariable("internalObjectid") String internalObjectid,
									   @RequestBody ObjectBoundary objectBoundary) {
		// Set the updated data in the ObjectBoundary object
		objectBoundary.setObjectId(new ObjectID(superapp, internalObjectid));
		objectBoundary.setAlias("updated-alias");
		objectBoundary.setActive(false);
		objectBoundary.setLocation(new Location(4.5678, 9.1234));

		// Return the updated ObjectBoundary object to the client
		return objectBoundary;
	}

}




