package superapp.logic.service.SuperAppObjService;

import java.util.List;
import java.util.Optional;

import superapp.Boundary.SuperAppObjectBoundary;

public interface ObjectsService {
        public SuperAppObjectBoundary createObject(SuperAppObjectBoundary obj) throws RuntimeException;


        @Deprecated
        public SuperAppObjectBoundary updateObject(String obj, String internal_obj_id, SuperAppObjectBoundary update) throws RuntimeException;

        @Deprecated
        public Optional<SuperAppObjectBoundary> getSpecificObject(String obj, String internal_obj_id) throws RuntimeException;

        @Deprecated
        public List<SuperAppObjectBoundary> getAllObjects() throws RuntimeException;

        @Deprecated
        public void deleteAllObjects() throws RuntimeException;

}
