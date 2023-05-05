package superapp.logic.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import superapp.Boundary.ObjectBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRepository;
import superapp.data.mainEntity.SuperAppObjectEntity;
import superapp.logic.service.ObjectsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.service.SuperAppObjectRelationshipService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectServiceMockup implements ObjectsService, SuperAppObjectRelationshipService {

    private final SuperAppObjectRepository objectRepository;
    private String springAppName;


    @Autowired
    public ObjectServiceMockup(SuperAppObjectRepository objectRepository,
                               MongoTemplate mongoTemplate) {

        this.objectRepository = objectRepository;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public ObjectBoundary createObject(ObjectBoundary obj) {

        obj.setObjectId(new ObjectId(springAppName, UUID.randomUUID().toString()));
        SuperAppObjectEntity entity = boundaryToEntity(obj);
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
        if (update.getObjectDetails() != null) {
            entity.setObjectDetails(update.getObjectDetails());
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
    }
    @Override
    public void bindParentAndChild(String parentId, String childId) {
        Optional<SuperAppObjectEntity> optionalParent = objectRepository.findById(new ObjectId(springAppName, parentId));
        Optional<SuperAppObjectEntity> optionalChild = objectRepository.findById(new ObjectId(springAppName, childId));

        if (optionalParent.isEmpty() || optionalChild.isEmpty()) {
            throw new NullPointerException("internal id not exist");
        }

        SuperAppObjectEntity parent = optionalParent.get();
        SuperAppObjectEntity child = optionalChild.get();
        parent.getChildObjects().add(child);
        child.getParentObjects().add(parent);
        objectRepository.save(child);
        objectRepository.save(parent);

    }
    @Override
    public Set<ObjectBoundary> getAllChildren(String objectId) {
        Optional<SuperAppObjectEntity> optionalParent = objectRepository.findById(new ObjectId(springAppName, objectId));

        if (optionalParent.isEmpty()) {
            throw new NullPointerException("internal id not exist");
        }
        Set<ObjectBoundary> children = new HashSet<>();
        SuperAppObjectEntity parent = optionalParent.get();

        if (!parent.getChildObjects().isEmpty()){
            children.addAll(parent.getChildObjects().stream().map(this::entityToBoundary).toList());
        }

        return children;
    }
    @Override
    public Set<ObjectBoundary> getAllParents(String objectId) {

        Optional<SuperAppObjectEntity> optionalChild = objectRepository.findById(new ObjectId(springAppName, objectId));

        if (optionalChild.isEmpty()) {
            throw new NullPointerException("internal id not exist");
        }
        Set<ObjectBoundary> parents = new HashSet<>();
        SuperAppObjectEntity child = optionalChild.get();

        if (!child.getParentObjects().isEmpty()){
            parents.addAll(child.getParentObjects().stream().map(this::entityToBoundary).toList());
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
