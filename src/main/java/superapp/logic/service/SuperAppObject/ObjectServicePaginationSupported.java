package superapp.logic.service.SuperAppObject;

import superapp.Boundary.superAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectServicePaginationSupported extends ObjectsService {
    public Optional<superAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);

    public List<superAppObjectBoundary> getAllObjects(int size, int page);


}
