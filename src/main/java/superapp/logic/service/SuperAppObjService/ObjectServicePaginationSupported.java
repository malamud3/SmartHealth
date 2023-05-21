package superapp.logic.service.SuperAppObjService;

import org.springframework.data.domain.Page;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.data.mainEntity.SuperAppObjectEntity;

import java.util.List;
import java.util.Optional;

public interface ObjectServicePaginationSupported extends ObjectsService {
    public Optional<SuperAppObjectBoundary> getSpecificObject(String superapp, String internalObjectId, String userSuperApp, String userEmail);

    public List<SuperAppObjectBoundary> getAllObjects(int size, int page);

    public List<SuperAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page);

    public List<SuperAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page);

    public List<SuperAppObjectBoundary> searchByType(String alias, String userSuperapp, String userEmail, int size, int page) ;

    public List<SuperAppObjectBoundary> searchByAlias(String alias, String userSuperapp, String userEmail, int size, int page);

    public List<SuperAppObjectBoundary> searchByLocation(double latitude, double longitude, double distance, String distanceUnits, String superapp, String email, int size, int page) ;

    }
