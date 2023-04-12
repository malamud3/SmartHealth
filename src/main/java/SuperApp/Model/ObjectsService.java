package SuperApp.Model;

import java.util.List;
import java.util.Optional;

public interface ObjectsService
{
        public  ObjectBoundary createObject(ObjectBoundary obj);

        public ObjectBoundary updateObject(String obj , String internal_obj_id , ObjectBoundary update);

        public Optional<Object> getSpecificObject (String obj , String internal_obj_id);

        public List<ObjectBoundary> getAllObjects();

        public void deleteAllObjects();
}
