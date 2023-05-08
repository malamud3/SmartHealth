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
public class SuperAppObjectsAPIController {


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

    public ObjectBoundary createObject(@RequestBody ObjectBoundary objectBoundary) throws RuntimeException {
        try {
            return objectsService.createObject(objectBoundary);
        }catch (RuntimeException e){
            throw new RuntimeException("can't create object: "+ e.getMessage());
        }

    }

    //PUT: Update Object
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})

    public void updateObject(@PathVariable("superapp") String superapp,
                             @PathVariable("internalObjectId") String internalObjectid,
                             @RequestBody ObjectBoundary objectBoundary) throws  RuntimeException{
        try {
            objectsService.updateObject(superapp, internalObjectid, objectBoundary);
        }catch (RuntimeException e){
            throw new RuntimeException("can't update objects:" +e.getMessage());
        }

    }

    //GET: Get Object
    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{internalObjectId}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary retrieveObject(@PathVariable("superapp") String superapp,
                                         @PathVariable("internalObjectId") String internalObjectId) throws RuntimeException{
        return objectsService.getSpecificObject(superapp, internalObjectId)
                .orElseThrow(() -> new RuntimeException("could not find object with id: " + superapp + "_" + internalObjectId));

    }


    //GET: Get All Objects
    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public List<ObjectBoundary> getAllObjects() throws RuntimeException {
        try {
            return objectsService.getAllObjects();
        }catch (RuntimeException e){
            throw new RuntimeException("can't get all objects: "+e.getMessage());
        }
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
            @RequestBody SuperAppObjectIdBoundary objectIdBoundary
    ) throws RuntimeException {
        try {
            Optional<ObjectBoundary> objectBoundaryParent = objectsService.getSpecificObject(superapp, internalObjectId);
            assert objectBoundaryParent.orElse(null) != null;
            superAppObjectRelationshipService.bindParentAndChild(objectBoundaryParent.get().getObjectId().getInternalObjectId(), objectIdBoundary.getInternalObjectId());
        } catch (RuntimeException e) {
            throw new RuntimeException("could not bind parent and child: " + e.getMessage());
        }
    }


    //GET: Get all children of an existing object
    @GetMapping(
            path = "/superapp/objects/{superapp}/{internalObjectId}/children",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ObjectBoundary> getAllObjectChildren(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId) {
        try {
            Optional<ObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
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
    public List<ObjectBoundary> getAllObjectParents(
            @PathVariable("superapp") String superapp,
            @PathVariable("internalObjectId") String internalObjectId) throws RuntimeException {
        try {
            Optional<ObjectBoundary> objectBoundary = objectsService.getSpecificObject(superapp, internalObjectId);
            return superAppObjectRelationshipService.getAllParents(objectBoundary.orElseThrow(() -> new ObjectNotFoundException("could not find Object with id:" + internalObjectId)).getObjectId().getInternalObjectId()).stream().toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("can't get all parents: " + e.getMessage());
        }

    }

}



