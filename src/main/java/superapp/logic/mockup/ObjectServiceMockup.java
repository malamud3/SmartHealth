package superapp.logic.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import superapp.Boundary.ObjectBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRelationshipRepository;
import superapp.dal.SuperAppObjectRepository;
import superapp.data.mainEntity.SuperAppObjectEntity;
import superapp.logic.service.ObjectsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectServiceMockup implements ObjectsService {

    private final SuperAppObjectRepository objectRepository;
    private final SuperAppObjectRelationshipRepository relationshipRepository;
    private String springAppName;
    private MongoTemplate mongoTemplate;

    @Autowired
    public ObjectServiceMockup(SuperAppObjectRepository objectRepository, SuperAppObjectRelationshipRepository relationshipRepository) {
        this.objectRepository = objectRepository;
        this.relationshipRepository = relationshipRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public ObjectBoundary createObject(ObjectBoundary obj) {
        SuperAppObjectEntity entity = boundaryToEntity(obj);

        Set<SuperAppObjectEntity> parentObjects = new HashSet<>();
        Set<SuperAppObjectEntity> childObjects = new HashSet<>();

        if (obj.getParentObjects() != null) {
            for (ObjectBoundary parent : obj.getParentObjects()) {
                SuperAppObjectEntity parentEntity = objectRepository.findById(parent.getObjectId()).orElse(null);
                if (parentEntity != null) {
                    parentObjects.add(parentEntity);
                }
            }
        }
        if (obj.getChildObjects() != null) {
            for (ObjectBoundary child : obj.getChildObjects()) {
                SuperAppObjectEntity childEntity = objectRepository.findById(child.getObjectId()).orElse(null);
                if (childEntity != null) {
                    childObjects.add(childEntity);
                }
            }
        }
        entity.setParentObjects(parentObjects);
        entity.setChildObjects(childObjects);
        entity = objectRepository.save(entity);
        return entityToBoundary(entity);
    }


    @Override
    public ObjectBoundary updateObject(String superAppId, String internal_obj_id, ObjectBoundary update) {
        Optional<SuperAppObjectEntity> optionalEntity = objectRepository.findById(new ObjectId(superAppId, internal_obj_id));
        if (optionalEntity.isEmpty()) {
            throw new NullPointerException("internal id not exist");
        }
        SuperAppObjectEntity entity = optionalEntity.get();
        boolean dirtyFlag = false;
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
        if (update.getObjectDetails() != null) {
            entity.setObjectDetails(update.getObjectDetails());
            dirtyFlag=true;
        }
        if(update.getObjectId() != null){
            entity.setObjectId(update.getObjectId());
            dirtyFlag=true;
        }

        if(dirtyFlag){
            entity = objectRepository.save(entity);
        }

        return entityToBoundary(entity);
    }

    @Override
    public Optional<ObjectBoundary> getSpecificObject(String superAppId, String internal_obj_id) {
        Optional<SuperAppObjectEntity> optionalEntity = objectRepository.findById(new ObjectId(superAppId, internal_obj_id));
        return optionalEntity.map(this::entityToBoundary);
    }

    @Override
    public List<ObjectBoundary> getAllObjects() {
        List<SuperAppObjectEntity> entities = objectRepository.findAll();
        return entities.stream()
                .map(this::entityToBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllObjects() {
        objectRepository.deleteAll();
        relationshipRepository.deleteAll();
    }

    public void bindParentAndChild(String parentId, String childId) {
        SuperAppObjectEntity parent = mongoTemplate.findById(parentId, SuperAppObjectEntity.class);
        SuperAppObjectEntity child = mongoTemplate.findById(childId, SuperAppObjectEntity.class);

        parent.getChildObjects().add(child);

        child.getParentObjects().add(parent);

        mongoTemplate.save(parent);
        mongoTemplate.save(child);
    }

    public Set<SuperAppObjectEntity> getAllParents(SuperAppObjectEntity object) {
        Set<SuperAppObjectEntity> parents = new HashSet<>();

        for (SuperAppObjectEntity parent : object.getParentObjects()) {
            parents.add(parent);
            parents.addAll(getAllParents(parent));
        }
        return parents;
    }



    public ObjectBoundary entityToBoundary( SuperAppObjectEntity entity) {
        ObjectBoundary obj = new ObjectBoundary();

        // convert entity to boundary
        obj.setObjectId(entity.getObjectId());
        obj.setActive(entity.getActive());
        obj.setCreatedBy(entity.getCreatedBy());
        obj.setAlias(entity.getAlias());
        obj.setObjectDetails(entity.getObjectDetails());
        obj.setCreationTimestamp(entity.getCreationTimestamp());
        obj.setType(entity.getType());
        obj.setLocation(entity.getLocation());
        obj.setObjectId(new ObjectId(springAppName, UUID.randomUUID().toString()));
        obj.setCreationTimestamp(new Date());
        return obj;
    }




    public SuperAppObjectEntity boundaryToEntity (ObjectBoundary obj) {
        SuperAppObjectEntity rv = new SuperAppObjectEntity();

        rv.setObjectId(obj.getObjectId());
        rv.setActive(obj.getActive());
        rv.setLocation(obj.getLocation());
        rv.setType(obj.getType());
        rv.setObjectDetails(obj.getObjectDetails());
        rv.setAlias(obj.getAlias());
        rv.setCreatedBy(obj.getCreatedBy());
        rv.setCreationTimestamp(obj.getCreationTimestamp());

        return rv;
    }
}
