package superapp.miniapps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.logic.service.SuperAppObjService.ObjectServicePaginationSupported;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;

@RestController
public class DietitianController {

    private final ObjectServicePaginationSupported objectsService;
    private final SuperAppObjectRelationshipService superAppObjectRelationshipService;
    private final ObjectsServiceWithAdminPermission objectsServiceWithAdminPermission;


    @Autowired
    public DietitianController(ObjectServicePaginationSupported objectsService,
                                        SuperAppObjectRelationshipService superAppObjectRelationshipService, ObjectsServiceWithAdminPermission objectsServiceWithAdminPermission) {
        this.objectsService = objectsService;
        this.superAppObjectRelationshipService = superAppObjectRelationshipService;
        this.objectsServiceWithAdminPermission = objectsServiceWithAdminPermission;
    }

    //POST: Create Recipe
    @RequestMapping(
            path = {"/superapp/"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public SuperAppObjectBoundary createObject(@RequestBody SuperAppObjectBoundary superAppObjectBoundary) throws RuntimeException {
        return objectsService.createObject(superAppObjectBoundary);

    }
}
