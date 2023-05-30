package superapp.logic.service.SuperAppObjService;

import superapp.Boundary.SuperAppObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectServicePaginationSupported extends ObjectsService {
    public SuperAppObjectBoundary getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail) throws RuntimeException;

    public List<SuperAppObjectBoundary> getAllObjects(int size, int page);

    public List<SuperAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page);

    public List<SuperAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page);

    List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String userEmail, int size, int page);

    List<SuperAppObjectBoundary> searchByAlias(String alias, String superapp, String email, int size, int page);

    List<SuperAppObjectBoundary> searchByLocation(double latitude, double longitude, double distance, String distanceUnits, String superapp, String email, int size, int page);

    List<SuperAppObjectBoundary> searchByType(String type, String superapp, String email, int size, int page);
}
