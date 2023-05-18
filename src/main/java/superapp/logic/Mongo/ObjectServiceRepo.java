package superapp.logic.Mongo;

import org.springframework.beans.factory.annotation.Autowired;
import superapp.Boundary.superAppObjectBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRepository;
import superapp.data.mainEntity.SuperAppObjectEntity;
import superapp.logic.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.service.ObjectsService;
import superapp.logic.service.SuperAppObjectRelationshipService;
import superapp.logic.utilitys.GeneralUtility;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectServiceRepo implements ObjectsService, SuperAppObjectRelationshipService {

    private final SuperAppObjectRepository objectRepository;
    private String springAppName;


    @Autowired
    public ObjectServiceRepo(SuperAppObjectRepository objectRepository) {

        this.objectRepository = objectRepository;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public superAppObjectBoundary createObject(superAppObjectBoundary obj) throws RuntimeException {
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

    private void validateObject(superAppObjectBoundary obj) {
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
    public superAppObjectBoundary updateObject(String superAppId, String internal_obj_id, superAppObjectBoundary update) {
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
    public Optional<superAppObjectBoundary> getSpecificObject(String superAppId, String internal_obj_id) {
        Optional<SuperAppObjectEntity> optionalEntity = objectRepository.findById(new ObjectId(superAppId, internal_obj_id));
        return optionalEntity.map(this::entityToBoundary);
    }

    @Override
    public List<superAppObjectBoundary> getAllObjects() {
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
    public Set<superAppObjectBoundary> getAllChildren(String objectId) {
        SuperAppObjectEntity parent = objectRepository.findById(new ObjectId(springAppName, objectId)).orElseThrow(()->new ObjectNotFoundException("object not found"));

        Set<superAppObjectBoundary> children = new HashSet<>();

        if (!parent.getChildObjects().isEmpty()){
            children.addAll(parent.getChildObjects().stream().map(this::entityToBoundary).toList());
        }else {
            throw new RuntimeException("object doesn't have children");
        }

        return children;
    }
    @Override
    public Set<superAppObjectBoundary> getAllParents(String objectId) {

        SuperAppObjectEntity child = objectRepository.findById(new ObjectId(springAppName, objectId)).orElseThrow(()->new ObjectNotFoundException("object not found"));

        Set<superAppObjectBoundary> parents = new HashSet<>();
        if (!child.getParentObjects().isEmpty()){
            parents.addAll(child.getParentObjects().stream().map(this::entityToBoundary).toList());
        }else {
            throw new RuntimeException("object doesn't have parents");
        }

        return parents;

    }


    public superAppObjectBoundary entityToBoundary(SuperAppObjectEntity entity) {
        superAppObjectBoundary obj = new superAppObjectBoundary();

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


    public SuperAppObjectEntity boundaryToEntity (superAppObjectBoundary obj) {
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
