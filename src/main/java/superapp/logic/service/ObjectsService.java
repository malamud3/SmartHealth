package superapp.logic.service;

import java.util.List;
import java.util.Optional;

import superapp.Boundary.superAppObjectBoundary;

public interface ObjectsService {
        public superAppObjectBoundary createObject(superAppObjectBoundary obj) throws RuntimeException;


        @Deprecated
        public superAppObjectBoundary updateObject(String obj, String internal_obj_id, superAppObjectBoundary update) throws RuntimeException;

        @Deprecated
        public Optional<superAppObjectBoundary> getSpecificObject(String obj, String internal_obj_id) throws RuntimeException;

        @Deprecated
        public List<superAppObjectBoundary> getAllObjects() throws RuntimeException;

        @Deprecated
        public void deleteAllObjects() throws RuntimeException;

}
