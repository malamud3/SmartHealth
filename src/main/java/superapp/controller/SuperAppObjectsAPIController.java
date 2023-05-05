package superapp.controller;

import superapp.Boundary.*;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.service.ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.logic.service.SuperAppObjectRelationshipService;

import java.util.List;
import java.util.Optional;

@RestController
public class SuperAppObjectsAPIController  {


	private final ObjectsService objectsService;
	private final SuperAppObjectRelationshipService superAppObjectRelationshipService;
	@Autowired
	public SuperAppObjectsAPIController(ObjectsService objectsService,
										SuperAppObjectRelationshipService superAppObjectRelationshipService) {
		this.objectsService = objectsService;
		this.superAppObjectRelationshipService = superAppObjectRelationshipService;
	}

	
	//POST: Create Object
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	
	public ObjectBoundary createObject(@RequestBody ObjectBoundary objectBoundary) {

		return objectsService.createObject(objectBoundary);

	}
	
	//PUT: Update Object
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	
	public void updateObject(@PathVariable("superapp") String superapp,
										  @PathVariable("internalObjectId") String internalObjectid,
										  @RequestBody ObjectBoundary objectBoundary) {

			objectsService.updateObject(superapp, internalObjectid, objectBoundary);

		}

//GET: Get Object
	@RequestMapping(
			path = {"/superapp/objects/{superapp}/{internalObjectId}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary retrieveObject(@PathVariable("superapp") String superapp,
										 @PathVariable("internalObjectId") String internalObjectId)
	{
		return objectsService.getSpecificObject(superapp, internalObjectId)
				.orElseThrow(() -> new RuntimeException("could not find object with id: " + superapp + "_" + internalObjectId));

	}


	//GET: Get All Objects
	@RequestMapping(
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})

	public  List<ObjectBoundary> getAllObjects()
	{
		return  objectsService.getAllObjects();
	}

	//PUT: Bind an existing object to an existing child object
	@PutMapping(
			path = "/superapp/objects/{superapp}/{internalObjectId}/children",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public void bindObjectToChild(
			@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId,
			@RequestBody  SuperAppObjectIdBoundary objectIdBoundary
	) {
		Optional<ObjectBoundary> objectBoundaryParent = objectsService.getSpecificObject(superapp, internalObjectId);
		assert objectBoundaryParent.orElse(null) != null;
		superAppObjectRelationshipService.bindParentAndChild(objectBoundaryParent.get().getObjectId().getInternalObjectId(), objectIdBoundary.getInternalObjectId());
	}



	//GET: Get all children of an existing object
	@GetMapping(
			path = "/superapp/objects/{superapp}/{internalObjectId}/children",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<ObjectBoundary> getAllObjectChildren(
			@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId){
		Optional<ObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
		assert objectBoundary.orElse(null) != null;
		superAppObjectRelationshipService.getAllChildren(objectBoundary.orElse(null).getObjectId().getInternalObjectId());
		return superAppObjectRelationshipService.getAllChildren(objectBoundary.orElse(null).getObjectId().getInternalObjectId()).stream().toList();
	}


	//GET: Get all parents of an existing object
	@GetMapping(
			path = "/superapp/objects/{superapp}/{internalObjectId}/parents",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<ObjectBoundary> getAllObjectParents(
			@PathVariable("superapp") String superapp,
			@PathVariable("internalObjectId") String internalObjectId){
		Optional<ObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
		return superAppObjectRelationshipService.getAllParents(objectBoundary.orElseThrow(()->new ObjectNotFoundException("could not find Object with id:"+internalObjectId)).getObjectId().getInternalObjectId()).stream().toList();
	}

}



