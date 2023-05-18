package superapp.logic.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import superapp.Boundary.ObjectBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRepository;
import superapp.data.mainEntity.SuperAppObjectEntity;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.service.ObjectsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.service.SuperAppObjectRelationshipService;
import superapp.logic.utilitys.GeneralUtility;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectServiceMockup implements ObjectsService, SuperAppObjectRelationshipService {

    private final SuperAppObjectRepository objectRepository;
    private String springAppName;


    @Autowired
    public ObjectServiceMockup(SuperAppObjectRepository objectRepository) {

        this.objectRepository = objectRepository;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public ObjectBoundary createObject(ObjectBoundary obj) throws RuntimeException {
        try {
            validateObject(obj);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
        obj.setObjectId(new ObjectId(springAppName, UUID.randomUUID().toString()));
        obj.setCreationTimestamp(new Date());
        SuperAppObjectEntity entity = boundaryToEntity(obj);
        entity = objectRepository.save(entity);
        return entityToBoundary(entity);
    }

    private void validateObject(ObjectBoundary obj) {
        GeneralUtility generalUtility = new GeneralUtility();
        // Check if alias is valid
        if (generalUtility.isStringEmptyOrNull(obj.getAlias())){
            throw new RuntimeException("alias is empty");
        }
        // Check if type is valid
        if (generalUtility.isStringEmptyOrNull(obj.getType())){
            throw new RuntimeException("alias is empty");
        // Check if created by is valid
        }if (generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getEmail()) ||
                generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getSuperapp()) ){
            throw new RuntimeException("created by is empty");
        }
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
    public void bindParentAndChild(String parentId, String childId) throws RuntimeException{
        if (childId.equals(parentId)){
            throw new RuntimeException("can't bind the same object");
        }
        SuperAppObjectEntity parent  = objectRepository.findById(new ObjectId(springAppName, parentId)).orElseThrow(()
                -> new ObjectNotFoundException("could not find object with id: "+parentId ));
        SuperAppObjectEntity child = objectRepository.findById(new ObjectId(springAppName, childId)).orElseThrow(()
                -> new ObjectNotFoundException("could not find object with id: " +childId));

        boolean isChildAlreadyAssociated = parent.getChildObjects().stream()
                .anyMatch(existingChild -> existingChild.equals(child));

        if (!isChildAlreadyAssociated) {
            parent.getChildObjects().add(child);
            child.getParentObjects().add(parent);
            objectRepository.save(child);
            objectRepository.save(parent);
        }else {
            throw new RuntimeException("child and parent already bind");
        }
    }
    @Override
    public Set<ObjectBoundary> getAllChildren(String objectId) {
        SuperAppObjectEntity parent = objectRepository.findById(new ObjectId(springAppName, objectId)).orElseThrow(()->new ObjectNotFoundException("object not found"));

        Set<ObjectBoundary> children = new HashSet<>();

        if (!parent.getChildObjects().isEmpty()){
            children.addAll(parent.getChildObjects().stream().map(this::entityToBoundary).toList());
        }else {
            throw new RuntimeException("object doesn't have children");
        }

        return children;
    }
    @Override
    public Set<ObjectBoundary> getAllParents(String objectId) {

        SuperAppObjectEntity child = objectRepository.findById(new ObjectId(springAppName, objectId)).orElseThrow(()->new ObjectNotFoundException("object not found"));

        Set<ObjectBoundary> parents = new HashSet<>();
        if (!child.getParentObjects().isEmpty()){
            parents.addAll(child.getParentObjects().stream().map(this::entityToBoundary).toList());
        }else {
            throw new RuntimeException("object doesn't have parents");
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
