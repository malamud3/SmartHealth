package superapp.logic.service;

import superapp.Boundary.superAppObjectBoundary;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ObjectServicePaginationSupported extends ObjectsService {
    public Optional<superAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);

    public List<superAppObjectBoundary> getAllObjects(int size, int page);

    public Set<superAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page);

    public Set<superAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page);
}
