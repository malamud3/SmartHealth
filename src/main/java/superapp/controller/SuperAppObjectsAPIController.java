package superapp.controller;

import superapp.model.*;
import superapp.logic.ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<Object> retrieveObject(@PathVariable("superapp") String superapp,
										   @PathVariable("internalObjectId") String internalObjectId)
	{
		return objectsService.getSpecificObject(superapp,internalObjectId);
		
	}

	//GET: all objects from DB
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public  List<ObjectBoundary> getAllObjects()
	{
		return  objectsService.getAllObjects();
	}


	//POST : Create an object
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary createObject(@RequestBody ObjectBoundary objectBoundary) {
		// Create a new object

		return objectsService.createObject(objectBoundary);

	}


	// PUT: Update Object
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectid}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public void updateObject(@PathVariable("superapp") String superapp,
									   @PathVariable("internalObjectid") String internalObjectid,
									   @RequestBody ObjectBoundary objectBoundary) {

		objectsService.updateObject(superapp, internalObjectid, objectBoundary);

	}

}




