package superapp.logic.service;

import java.util.List;
import java.util.Optional;

import superapp.Boundary.ObjectBoundary;

public interface ObjectsService
{
        public  ObjectBoundary createObject(ObjectBoundary obj) throws RuntimeException;

        public ObjectBoundary updateObject(String obj , String internal_obj_id , ObjectBoundary update) throws RuntimeException;

        public Optional<ObjectBoundary> getSpecificObject (String obj , String internal_obj_id) throws RuntimeException;

        public List<ObjectBoundary> getAllObjects() throws RuntimeException;

        public void deleteAllObjects() throws RuntimeException;
}
