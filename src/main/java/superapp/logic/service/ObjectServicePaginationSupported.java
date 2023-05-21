package superapp.logic.service;

import superapp.Boundary.superAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectServicePaginationSupported extends ObjectsService {
    public Optional<superAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);

    public List<superAppObjectBoundary> getAllObjects(int size, int page);

    public List<superAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page);

    public List<superAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page);
}
