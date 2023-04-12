package Logic.Mockup;

import SuperApp.Model.ObjectBoundary;
import SuperApp.Model.ObjectsService;
import data.SuperAppObjectEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class ObjectServiceMockup implements ObjectsService
{

    private Map<String,SuperAppObjectEntity> dbMockup;
    private  String springAppName;

    @Value("2023b.gil.azani")
    public  void setSpringAppName(String springAppName)
    {
        this.springAppName = springAppName;
    }

    @PostConstruct
    public void init()
    {
        this.dbMockup = Collections.synchronizedMap(new HashMap<>());
        System.err.println("******"+this.springAppName);
    }
    @Override
    public ObjectBoundary createObject(ObjectBoundary obj) {
        return null;
    }

    @Override
    public ObjectBoundary updateObject(String obj, String internal_obj_id, ObjectBoundary update) {
        return null;
    }

    @Override
    public Optional<Object> getSpecificObject(String obj, String internal_obj_id) {
        SuperAppObjectEntity entity = this.dbMockup.get(obj);
        if (entity == null)
            return Optional.empty();

        return null;
    }

    @Override
    public List<ObjectBoundary> getAllObjects() {
        return null;
    }

    @Override
    public void deleteAllObjects() {

    }
}
