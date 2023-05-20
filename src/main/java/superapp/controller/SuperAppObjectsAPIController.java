package superapp.controller;

import superapp.Boundary.*;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.service.ObjectServicePaginationSupported;
import superapp.logic.service.ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.logic.service.SuperAppObjectRelationshipService;

import java.util.List;
import java.util.Optional;

@RestController
public class SuperAppObjectsAPIController {


    private final ObjectServicePaginationSupported objectsService;
    private final SuperAppObjectRelationshipService superAppObjectRelationshipService;

    //private final ObjectServicePaginationSupported objectServicePaginationSupported;

    @Autowired
    public SuperAppObjectsAPIController(ObjectServicePaginationSupported objectsService,
                                        SuperAppObjectRelationshipService superAppObjectRelationshipService) {
        this.objectsService = objectsService;
        this.superAppObjectRelationshipService = superAppObjectRelationshipService;

        //this.objectServicePaginationSupported = objectServicePaginationSupported;
    }


    //POST: Create Object
    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public superAppObjectBoundary createObject(@RequestBody superAppObjectBoundary superAppObjectBoundary) throws RuntimeException {
        try {
            return objectsService.createObject(superAppObjectBoundary);
        }catch (RuntimeException e){
            throw new RuntimeException("can't create object: "+ e.getMessage());
        }

    }

    // PUT: Update Object
    @RequestMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateObject(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam("userSuperApp") String userSuperApp,
            @RequestParam("userEmail") String userEmail,
            @RequestBody superAppObjectBoundary superAppObjectBoundary
    ) {
        try {

            objectsService.updateObject(superapp, internalObjectId, superAppObjectBoundary);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't update objects: " + e.getMessage());
        }
    }

    // GET: Get specific Object
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public superAppObjectBoundary retrieveObject(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name="userSuperApp") String userSuperApp,
            @RequestParam(name="userEmail") String userEmail) throws RuntimeException {
    	
        return objectsService.getSpecificObject(superapp, internalObjectId, userSuperApp, userEmail)
                .orElseThrow(() -> new RuntimeException("Could not find object with id: " + superapp + "_" + internalObjectId));
    }

    // GET: Get All Objects
    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<superAppObjectBoundary> getAllObjects(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name ="size" , required = false , defaultValue = "12") int size,
            @RequestParam(name = "page" , required = false ,defaultValue = "0") int page) {
        try {
            // Call the service method to retrieve all objects with the specified size and page
            return objectsService.getAllObjects(size,page);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all objects: " + e.getMessage());
        }
    }



    //PUT: Bind an existing object to an existing child object
    @PutMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/children",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void bindObjectToChild(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name="userSuperApp") String userSuperApp,
            @RequestParam(name="userEmail") String userEmail,
            @RequestBody SuperAppObjectIdBoundary objectIdBoundary
    ) throws RuntimeException {
        try {
            Optional<superAppObjectBoundary> objectBoundaryParent = objectsService.getSpecificObject(superapp, internalObjectId);
            assert objectBoundaryParent.orElse(null) != null;
            superAppObjectRelationshipService.bindParentAndChild(objectBoundaryParent.get().getObjectId().getInternalObjectId(), objectIdBoundary.getInternalObjectId());
        } catch (RuntimeException e) {
            throw new RuntimeException("could not bind parent and child: " + e.getMessage());
        }
    }


    //GET: Get all children of an existing object
    @GetMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/children",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<superAppObjectBoundary> getAllObjectChildren(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId) {
        try {
            Optional<superAppObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
            return superAppObjectRelationshipService.getAllChildren(objectBoundary.orElseThrow(() -> new ObjectNotFoundException("could not find Object with id:" + internalObjectId)).getObjectId().getInternalObjectId()).stream().toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("can't get all children: " + e.getMessage());
        }
    }


    //GET: Get all parents of an existing object
    @GetMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/parents",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<superAppObjectBoundary> getAllObjectParents(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId) throws RuntimeException {
        try {
            Optional<superAppObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
            return superAppObjectRelationshipService.getAllParents(objectBoundary.orElseThrow(() -> new ObjectNotFoundException("could not find Object with id:" + internalObjectId)).getObjectId().getInternalObjectId()).stream().toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("can't get all parents: " + e.getMessage());
        }

    }

}



