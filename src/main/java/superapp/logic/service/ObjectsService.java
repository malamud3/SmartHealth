package superapp.logic.service;

import java.util.List;
import java.util.Optional;

import superapp.Boundary.ObjectBoundary;

public interface ObjectsService
{
        public  ObjectBoundary createObject(ObjectBoundary obj);

        public ObjectBoundary updateObject(String obj , String internal_obj_id , ObjectBoundary update);

        public Optional<ObjectBoundary> getSpecificObject (String obj , String internal_obj_id);

        public List<ObjectBoundary> getAllObjects();

        public void deleteAllObjects();
}
