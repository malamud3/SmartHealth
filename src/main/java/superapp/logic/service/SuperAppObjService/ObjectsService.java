package superapp.logic.service.SuperAppObjService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import superapp.Boundary.SuperAppObjectBoundary;

        @Service
        public interface ObjectsService {
                 SuperAppObjectBoundary createObject(SuperAppObjectBoundary obj) throws RuntimeException;


                @Deprecated
                 SuperAppObjectBoundary updateObject(String obj, String internal_obj_id, SuperAppObjectBoundary update) throws RuntimeException;

                @Deprecated
                 Optional<SuperAppObjectBoundary> getSpecificObject(String obj, String internal_obj_id) throws RuntimeException;

                @Deprecated
                 List<SuperAppObjectBoundary> getAllObjects() throws RuntimeException;

                @Deprecated
                 void deleteAllObjects() throws RuntimeException;

        }