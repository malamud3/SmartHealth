package superapp.logic.Mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.UserId;
import superapp.Boundary.Location;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.UserRole;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import superapp.logic.Exceptions.DepreacatedOpterationException;
import superapp.logic.Exceptions.ObjectBadRequest;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.Exceptions.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;
import superapp.logic.utilitys.GeneralUtility;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.logic.utilitys.UserUtility;

import java.util.*;


@Service
public class ObjectServiceRepo implements SuperAppObjectRelationshipService {

    private final SuperAppObjectCrud objectRepository;
    private String springAppName;
    private final UserUtility userUtility;
    private final SuperAppObjectUtility superAppObjectUtility;

    @Autowired
    public ObjectServiceRepo(SuperAppObjectCrud objectRepository, UserCrud userCrud) {

        this.objectRepository = objectRepository;
        this.userUtility = new UserUtility(userCrud);
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary obj) throws RuntimeException {
        UserId userId = new UserId(obj.getCreatedBy().getUserId().getSuperapp(), obj.getCreatedBy().getUserId().getEmail());
        System.err.println(userId.toString());
        UserEntity userEntity = userUtility.checkUserExist(userId);
        try {
        	superAppObjectUtility.validateObject(obj);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }

        if (!userEntity.getRole().equals(UserRole.SUPERAPP_USER))
            throw new PermissionDeniedException("User do not have permission to createObject");

        obj.setObjectId(new ObjectId(springAppName, UUID.randomUUID().toString()));
        obj.setCreationTimestamp(new Date());
        SuperAppObjectEntity entity = boundaryToEntity(obj);
        entity = objectRepository.save(entity);
        return entityToBoundary(entity);
    }


    @Override
    @Deprecated
    public SuperAppObjectBoundary updateObject(String superAppId, String internal_obj_id, SuperAppObjectBoundary update) {
        throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
    }

    @Override
    public SuperAppObjectBoundary updateObject(String superapp, String internal_obj_id, SuperAppObjectBoundary update, String userSuperApp, String userEmail) throws RuntimeException {
        SuperAppObjectEntity objectEntity = superAppObjectUtility.checkSuperAppObjectEntityExist(new ObjectId(superapp, internal_obj_id));
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));
        if (!userEntity.getRole().equals(UserRole.SUPERAPP_USER))
            throw new PermissionDeniedException("User do not have permission to updateObject");

        if (update.getType() != null) {
            objectEntity.setType(update.getType());
        }
        if (update.getAlias() != null) {
            objectEntity.setAlias(update.getAlias());
        }
        if (update.getActive() != null) {
            objectEntity.setActive(update.getActive());
        }
        if (update.getLocation() != null) {
            objectEntity.setLocation(new Point(update.getLocation().getLng(), update.getLocation().getLat()));//Point object LNG is first (x)
        }
        if (update.getObjectDetails() != null) {
            objectEntity.setObjectDetails(update.getObjectDetails());
        }

        objectEntity = objectRepository.save(objectEntity);

        return entityToBoundary(objectEntity);
    }

    @Override
    @Deprecated
    public Optional<SuperAppObjectBoundary> getSpecificObject(String superAppId, String internal_obj_id) {
        throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
    }

    @Override
    public SuperAppObjectBoundary getSpecificObject(String superAppId, String internal_obj_id, String userSuperApp, String userEmail) throws RuntimeException {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));

        superAppObjectUtility.checkSuperAppObjectEntityExist(new ObjectId(springAppName, internal_obj_id));

        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            SuperAppObjectEntity entity = objectRepository
                    .findByObjectId(new ObjectId(superAppId, internal_obj_id))
                    .orElseThrow(() -> new ObjectNotFoundException("Could not find object with id: " + superAppId + "_" + internal_obj_id));

            return entityToBoundary(entity);
        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            SuperAppObjectEntity entity = objectRepository
                    .findByObjectIdAndActiveIsTrue(new ObjectId(superAppId, internal_obj_id))
                    .orElseThrow(() -> new ObjectNotFoundException("Could not find object with id: " + superAppId + "_" + internal_obj_id));

            return entityToBoundary(entity);
        }
        throw new PermissionDeniedException("User do not have permission to getSpecificObject");
    }


    @Override
    @Deprecated
    public List<SuperAppObjectBoundary> getAllObjects() {
        throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
    }

    //Pagination Support

    @Override
    public List<SuperAppObjectBoundary> getAllObjects(String userSuperApp, String userEmail, int size, int page) {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));
        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            return this.objectRepository
                    .findAll(PageRequest.of(page, size, Sort
                            .Direction.DESC, "creationTimestamp", "_id"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();
        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            return this.objectRepository
                    .findByActiveIsTrue(PageRequest.of(page, size, Sort
                            .Direction.DESC, "creationTimestamp", "_id"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();
        } else {
            throw new PermissionDeniedException("User do not have permission to get all objects");
        }
    }


    @Override
    @Deprecated
    public void deleteAllObjects() {
        objectRepository.deleteAll();
    }

    @Override
    public void deleteAllObjects(UserId userId) {
        UserEntity userEntity = userUtility.checkUserExist(userId);

        if (!userEntity.getRole().equals(UserRole.ADMIN)) {
            throw new PermissionDeniedException("You do not have permission to delete all objects");
        }

        this.objectRepository.deleteAll();
    }


    @Override
    public void bindParentAndChild(String parentId, String childId, String userSuperApp, String userEmail) throws RuntimeException {
    	UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));
    	if (childId.equals(parentId)) {
    		throw new ObjectBadRequest("can't bind the same object");
    	}
    	if (!userEntity.getRole().equals(UserRole.SUPERAPP_USER))
    		throw new PermissionDeniedException("User do not have permission to bindParentAndChild Objects");

    	SuperAppObjectEntity parent = superAppObjectUtility.checkSuperAppObjectEntityExist(new ObjectId(springAppName, parentId));
    	SuperAppObjectEntity child = superAppObjectUtility.checkSuperAppObjectEntityExist(new ObjectId(springAppName, childId));

    	parent.getChildObjects().add(child);
    	child.getParentObjects().add(parent);
    	
    	objectRepository.save(child);
    	objectRepository.save(parent);
        
    }

    @Deprecated
    public Set<SuperAppObjectBoundary> getAllChildren(String objectId) {
        throw new DepreacatedOpterationException("dont use this func ");
    }

    @Override
    public List<SuperAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page) {

        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));
        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {

            SuperAppObjectEntity parent = superAppObjectUtility.checkSuperAppObjectEntityExist(new ObjectId(springAppName, internalObjectId));

            return this.objectRepository
                    .findByParentObjectsContaining(parent, PageRequest.of(page, size, Sort
                            .Direction.ASC, "creationTimestamp"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();


        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            SuperAppObjectEntity parent = objectRepository.findByObjectIdAndActiveIsTrue(new ObjectId(springAppName, internalObjectId))
                    .orElseThrow(() -> new ObjectNotFoundException("Object not found"));

            return this.objectRepository
                    .findByParentObjectsContainingAndActiveIsTrue(parent, PageRequest.of(page, size, Sort
                            .Direction.ASC, "creationTimestamp"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();
        }
        throw new PermissionDeniedException("User do not have permission to get all children");
    }

    @Override
    public List<SuperAppObjectBoundary> getAllParents(String internalObjectId, String userSuperapp, String userEmail, int size, int page) {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperapp, userEmail));

        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            SuperAppObjectEntity child = objectRepository.findById(new ObjectId(springAppName, internalObjectId))
                    .orElseThrow(() -> new ObjectNotFoundException("Object not found"));

            return this.objectRepository
                    .findByChildObjectsContaining(child, PageRequest.of(page, size, Sort
                            .Direction.ASC, "creationTimestamp"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();

        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            SuperAppObjectEntity child = objectRepository.findByObjectIdAndActiveIsTrue(new ObjectId(springAppName, internalObjectId))
                    .orElseThrow(() -> new ObjectNotFoundException("Object not found"));

            return this.objectRepository
                    .findByChildObjectsContainingAndActiveIsTrue(child, PageRequest.of(page, size, Sort
                            .Direction.ASC, "creationTimestamp"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();
        }
        throw new PermissionDeniedException("User do not have permission to get all parents");
    }


    public List<SuperAppObjectBoundary> searchByAlias(String alias, String userSuperApp, String userEmail, int size, int page) {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "_id");

        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            return this.objectRepository
                    .findByAlias(alias, pageRequest)
                    .stream()
                    .map(this::entityToBoundary) // Convert SuperAppObjectEntity to superAppObjectBoundary
                    .toList();
        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            return this.objectRepository
                    .findByAliasAndActiveIsTrue(alias, pageRequest)
                    .stream()
                    .map(this::entityToBoundary) // Convert SuperAppObjectEntity to superAppObjectBoundary
                    .toList();
        } else {
            throw new PermissionDeniedException("User do not have permission to search by alias");
        }
    }

    public List<SuperAppObjectBoundary> searchByType(String type, String userSuperApp, String userEmail, int size, int page) {

        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "_id");

        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            return this.objectRepository
                    .findByType(type, pageRequest)
                    .stream()
                    .map(this::entityToBoundary) // Convert SuperAppObjectEntity to superAppObjectBoundary
                    .toList();
        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            return this.objectRepository
                    .findByTypeAndActiveIsTrue(type, pageRequest)
                    .stream()
                    .map(this::entityToBoundary) // Convert SuperAppObjectEntity to superAppObjectBoundary
                    .toList();
        } else {
            throw new PermissionDeniedException("User do not have permission to search by alias");
        }
    }

    public List<SuperAppObjectBoundary> searchByLocation(double latitude, double longitude,
    		double distance, String distanceUnits, String userSuperApp, String userEmail, int size, int page) {

    	UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp, userEmail));

    	GeneralUtility generalUtility = new GeneralUtility();
    	double distanceInRadians = generalUtility.calculateRadiusInRadiansUsingDistanceUnit(distanceUnits, distance);
    	if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
    		return this.objectRepository
    				.findWithinCircle(latitude,
    						longitude, distanceInRadians,
    						PageRequest.of(page, size, Sort.Direction.ASC, "_id"))
    				.stream()
    				.map(this::entityToBoundary)
    				.toList();

    	} else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
    		return this.objectRepository
    				.findWithinCircleAndActiveIsTrue(latitude,
    						longitude, distanceInRadians,
    						PageRequest.of(page, size, Sort.Direction.ASC, "_id"))
    				.stream()
    				.map(this::entityToBoundary)
    				.toList();
    	}
    	throw new PermissionDeniedException("User do not have permission to search by location");


    }


    public SuperAppObjectBoundary entityToBoundary(SuperAppObjectEntity entity) {
        SuperAppObjectBoundary obj = new SuperAppObjectBoundary();

        // convert entity to boundary
        obj.setObjectId(entity.getObjectId());
        obj.setActive(entity.getActive());
        obj.setCreatedBy(entity.getCreatedBy());
        obj.setAlias(entity.getAlias());
        obj.setObjectDetails(entity.getObjectDetails());
        obj.setCreationTimestamp(entity.getCreationTimestamp());
        obj.setType(entity.getType());
        obj.setLocation(new Location(entity.getLocation().getY(), entity.getLocation().getX()));
        return obj;
    }


    public SuperAppObjectEntity boundaryToEntity(SuperAppObjectBoundary obj) {
        SuperAppObjectEntity rv = new SuperAppObjectEntity();

        rv.setObjectId(obj.getObjectId());
        rv.setActive(obj.getActive());
        rv.setLocation(new Point(obj.getLocation().getLng(), obj.getLocation().getLat()));
        rv.setType(obj.getType());
        rv.setObjectDetails(obj.getObjectDetails());
        rv.setAlias(obj.getAlias());
        rv.setCreatedBy(obj.getCreatedBy());
        rv.setCreationTimestamp(obj.getCreationTimestamp());

        return rv;
    }
}
