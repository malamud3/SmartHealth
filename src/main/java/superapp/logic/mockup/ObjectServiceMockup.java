package superapp.logic.mockup;

import superapp.model.ObjectBoundary;
import superapp.model.ObjectId;
import superapp.logic.ObjectsService;
import superapp.data.SuperAppObjectEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ObjectServiceMockup implements ObjectsService {

    private Map<String, SuperAppObjectEntity> dbMockup;
    private String springAppName;

    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @PostConstruct
    public void init() {
        this.dbMockup = Collections.synchronizedMap(new HashMap<>());
        System.err.println("******" + this.springAppName);
    }

    @Override
    public ObjectBoundary createObject(ObjectBoundary obj) {

        if (obj.getAllObjects() == null) {
            obj.setAllObjects(new HashMap<>());
        }
        obj.getAllObjects().put(springAppName, obj);

        SuperAppObjectEntity entity = this.boundaryToEntity(obj);
        entity.setObjectId(new ObjectId(springAppName, UUID.randomUUID().toString()));
        entity.setCreationTimestamp(new Date());

        this.dbMockup.put(entity.getObjectId().getInternalObjectId(), entity);

        return this.entityToBoundary(entity);

    }

    @Override
    public ObjectBoundary updateObject(String obj, String internal_obj_id, ObjectBoundary update) {

        SuperAppObjectEntity entity = this.dbMockup.get(internal_obj_id);
        boolean dirtyFlag = false;
        if(entity == null)
            throw  new NullPointerException("internal id not exist");
        if (update.getType() != null) {
                entity.setType(update.getType());
                dirtyFlag=true;
            }
        if (update.getAlias() != null) {
                entity.setAlias(update.getAlias());
                dirtyFlag=true;

        }
        if (update.getActive() != null) {
                entity.setActive(update.getActive());
            dirtyFlag=true;
            }
        if (update.getLocation() != null) {
                entity.setLocation(update.getLocation());
            dirtyFlag=true;
            }
        if (update.getCreatedBy() != null) {
                entity.setCreatedBy(update.getCreatedBy());
            dirtyFlag=true;
            }
        if (update.getOurObject() != null) {
                entity.setOurObject(update.getOurObject());
            dirtyFlag=true;
            }
        if(update.getObjectId() != null){
            entity.setObjectId(update.getObjectId());
            dirtyFlag=true;
        }

        if(dirtyFlag)
            this.dbMockup.put(internal_obj_id , entity);

        return entityToBoundary(entity);
    }


    @Override
    public Optional<Object> getSpecificObject(String superAppId, String internal_obj_id) {
        SuperAppObjectEntity entity = this.dbMockup.get(superAppId);
        if (entity == null)
            return Optional.empty();
        ObjectBoundary obj = this.entityToBoundary(entity);
        return Optional.of(obj);
    }

    @Override
    public List<ObjectBoundary> getAllObjects() {
        return this.dbMockup.values()
                .stream() // Stream<MessageEntity>
                .map(this::entityToBoundary) // Stream<Message>
                .toList(); // List<Message>
    }

    @Override
    public void deleteAllObjects() {
        this.dbMockup.clear();

    }


    public ObjectBoundary entityToBoundary( SuperAppObjectEntity entity) {
        ObjectBoundary obj = new ObjectBoundary();

        // convert entity to boundary
        obj.setObjectId(entity.getObjectId());
        obj.setActive(entity.getActive());
        obj.setCreatedBy(entity.getCreatedBy());
        obj.setAlias(entity.getAlias());
        obj.setOurObject(entity.getOurObject());
        obj.setCreationTimestamp(entity.getCreationTimestamp());
        obj.setType(entity.getType());
        obj.setLocation(entity.getLocation());
        obj.setAllObjects(entity.getAllObjects());
        return obj;
    }




    public SuperAppObjectEntity boundaryToEntity (ObjectBoundary obj) {
        SuperAppObjectEntity rv = new SuperAppObjectEntity();

        rv.setObjectId(obj.getObjectId());
        rv.setActive(obj.getActive());
        rv.setLocation(obj.getLocation());
        rv.setType(obj.getType());
        rv.setOurObject(obj.getOurObject());
        rv.setAlias(obj.getAlias());
        rv.setCreatedBy(obj.getCreatedBy());
        rv.setCreationTimestamp(obj.getCreationTimestamp());
        rv.setAllObjects(obj.getAllObjects());

        return rv;
    }
}
