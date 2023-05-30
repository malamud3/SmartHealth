package superapp.controller;

import superapp.Boundary.*;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.service.SuperAppObjService.ObjectServicePaginationSupported;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class SuperAppObjectsAPIController {

    private final ObjectServicePaginationSupported objectsService;
    private final SuperAppObjectRelationshipService superAppObjectRelationshipService;

    private final ObjectsServiceWithAdminPermission objectsServiceWithAdminPermission;


    @Autowired
    public SuperAppObjectsAPIController(ObjectServicePaginationSupported objectsService,
                                        SuperAppObjectRelationshipService superAppObjectRelationshipService, ObjectsServiceWithAdminPermission objectsServiceWithAdminPermission) {
        this.objectsService = objectsService;
        this.superAppObjectRelationshipService = superAppObjectRelationshipService;

        this.objectsServiceWithAdminPermission = objectsServiceWithAdminPermission;
    }

    //POST: Create Object
    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public SuperAppObjectBoundary createObject(@RequestBody SuperAppObjectBoundary superAppObjectBoundary) throws RuntimeException {
            return objectsService.createObject(superAppObjectBoundary);

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
            @RequestParam("userSuperapp") String userSuperApp,
            @RequestParam("userEmail") String userEmail,
            @RequestBody SuperAppObjectBoundary superAppObjectBoundary){
        

            objectsServiceWithAdminPermission.updateObject(superapp, internalObjectId, superAppObjectBoundary , userSuperApp , userEmail);
        
    }

    // GET: Get specific Object
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary retrieveObject(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name="userSuperapp") String userSuperApp,
            @RequestParam(name="userEmail") String userEmail) throws RuntimeException {

        return objectsService.getSpecificObject(superapp, internalObjectId, userSuperApp, userEmail);
    	
    }

    // GET: Get All Objects
    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SuperAppObjectBoundary> getAllObjects(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name ="size" , required = false , defaultValue = "12") int size,
            @RequestParam(name = "page" , required = false ,defaultValue = "0") int page) {
           
    		// Call the service method to retrieve all objects with the specified size and page
            return objectsService.getAllObjects(userSuperapp,userEmail,size,page);
        
    }



    //PUT: Bind an existing object to an existing child object
    @PutMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/children",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void bindObjectToChild(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId,
            @RequestParam(name="userSuperapp") String userSuperApp,
            @RequestParam(name="userEmail") String userEmail,
            @RequestBody ObjectId objectIdBoundary
    ) throws RuntimeException {
        
            SuperAppObjectBoundary objectBoundaryParent = objectsService.getSpecificObject(superapp, internalObjectId , userSuperApp , userEmail);
            superAppObjectRelationshipService.bindParentAndChild(objectBoundaryParent.getObjectId().getInternalObjectId(), objectIdBoundary.getInternalObjectId(),userSuperApp,userEmail);
    }


    //GET: Get all children of an existing object
    @GetMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/children",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Object> getAllObjectChildren(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId ,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name ="size" , required = false , defaultValue = "12") int size,
            @RequestParam(name = "page" , required = false ,defaultValue = "0") int page)
        {
            SuperAppObjectBoundary objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId , userSuperapp , userEmail );
            return Collections
                    .singletonList(superAppObjectRelationshipService.getAllChildren(objectBoundary.getObjectId().getInternalObjectId(),userSuperapp,userEmail, size, page)
                    .stream()
                    .toList());
        
    }


    //GET: Get all parents of an existing object
    @GetMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/parents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SuperAppObjectBoundary> getAllObjectParents(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId ,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name ="size" , required = false , defaultValue = "12") int size,
            @RequestParam(name = "page" , required = false ,defaultValue = "0") int page)
            throws RuntimeException {
            SuperAppObjectBoundary objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId,userSuperapp,userEmail);
            return superAppObjectRelationshipService.getAllParents(objectBoundary
                    .getObjectId().getInternalObjectId(),userSuperapp,userEmail,size,page).stream().toList();
       

    }

  //GET: Get all user's objects by alias
    @GetMapping(
            path     = "/superapp/objects/search/byAlias/{alias}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SuperAppObjectBoundary> searchByAlias(
            @PathVariable("alias") String alias,
            @RequestParam("userSuperapp") String superapp,
            @RequestParam("userEmail") String email,
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return  objectsService.searchByAlias(alias, superapp, email, size, page);
    }

    //GET: Get all user's objects by type
    @GetMapping(
            path     = "/superapp/objects/search/byType/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SuperAppObjectBoundary> searchByType(
            @PathVariable("type") String type,
            @RequestParam("userSuperapp") String superapp,
            @RequestParam("userEmail") String email,
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return objectsService.searchByType(type, superapp, email, size, page);
    }

  //GET: Get all user's objects by location
    @GetMapping(
            path = "/superapp/objects/search/byLocation/{lat}/{lng}/{distance}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SuperAppObjectBoundary> searchByLocation(
            @PathVariable("lat") double latitude,
            @PathVariable("lng") double longitude,
            @PathVariable("distance") double distance,
            @RequestParam(value = "units", defaultValue = "NEUTRAL") String distanceUnits,
            @RequestParam(name = "userSuperapp") String superapp,
            @RequestParam(name = "userEmail") String email,
            @RequestParam(name = "size" , required = false) int size,
            @RequestParam(name = "page" , required = false) int page) {
    	
        return objectsService.searchByLocation(latitude, longitude, distance, distanceUnits, superapp, email, size, page);
    }

}
