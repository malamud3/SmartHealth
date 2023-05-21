package superapp.logic.service;

import superapp.Boundary.superAppObjectBoundary;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ObjectServicePaginationSupported extends ObjectsService {

    //pagination Support
    List<superAppObjectBoundary> getAllObjects(String userSuperApp, String userEmail, int size, int page) throws RuntimeException;

    public List<superAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page) throws RuntimeException;

    public List<superAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page) throws RuntimeException;
}
