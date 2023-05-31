package superapp.logic.Mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.UserId;
import superapp.Boundary.Location;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectRepository;
import superapp.dal.UserRepository;
import superapp.data.UserRole;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import superapp.logic.Exceptions.DepreacatedOpterationException;
import superapp.logic.Exceptions.ObjectNotFoundException;
import superapp.logic.Exceptions.PermissionDeniedException;
import superapp.logic.Exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import superapp.logic.service.SuperAppObjService.ObjectServicePaginationSupported;
import superapp.logic.service.SuperAppObjService.ObjectsService;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;
import superapp.logic.utilitys.GeneralUtility;

import java.util.*;


@Service
public class ObjectServiceRepo implements ObjectsServiceWithAdminPermission, SuperAppObjectRelationshipService, ObjectServicePaginationSupported, ObjectsService {

    private final SuperAppObjectRepository objectRepository;
    private final UserRepository userRepository;//for permission checks
    private String springAppName;


    @Autowired
    public ObjectServiceRepo(SuperAppObjectRepository objectRepository, UserRepository userRepository) {

        this.objectRepository = objectRepository;
        this.userRepository = userRepository;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary obj) throws RuntimeException {
        UserId userId = new UserId(obj.getCreatedBy().getUserId().getSuperapp(), obj.getCreatedBy().getUserId().getEmail());
        System.err.println(userId.toString());
        UserEntity userEntity = checkUserExist(userId);
        try {
            validateObject(obj);
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

    private void validateObject(SuperAppObjectBoundary obj) {
        GeneralUtility generalUtility = new GeneralUtility();
        // Check if alias is valid
        if (generalUtility.isStringEmptyOrNull(obj.getAlias())) {
            throw new RuntimeException("alias is empty");
        }
        // Check if type is valid
        if (generalUtility.isStringEmptyOrNull(obj.getType())) {
            throw new RuntimeException("alias is empty");
            // Check if created by is valid
        }
        if (generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getEmail()) ||
                generalUtility.isStringEmptyOrNull(obj.getCreatedBy().getUserId().getSuperapp())) {
            throw new RuntimeException("created by is empty");
        }
    }

    @Override
    @Deprecated
    public SuperAppObjectBoundary updateObject(String superAppId, String internal_obj_id, SuperAppObjectBoundary update) {
        throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
    }

    @Override
    public SuperAppObjectBoundary updateObject(String superapp, String internal_obj_id, SuperAppObjectBoundary update, String userSuperApp, String userEmail) throws RuntimeException {
        SuperAppObjectEntity objectEntity = checkSuperAppObjectEntityExist(new ObjectId(superapp, internal_obj_id));
        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));

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
            objectEntity.setLocation(new Point(update.getLocation().getLat(), update.getLocation().getLng()));
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
        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));

        SuperAppObjectEntity objectEntity = checkSuperAppObjectEntityExist(new ObjectId(springAppName, internal_obj_id));

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

    //pagination Support

    @Override
    public List<SuperAppObjectBoundary> getAllObjects(String userSuperApp, String userEmail, int size, int page) {
        UserId userId = new UserId(userSuperApp, userEmail);
        System.err.println(userId.toString());
        UserEntity userEntity = checkUserExist(userId);
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
        UserEntity userEntity = checkUserExist(userId);

        if (!userEntity.getRole().equals(UserRole.ADMIN)) {
            throw new PermissionDeniedException("You do not have permission to delete all objects");
        }

        this.objectRepository.deleteAll();
    }


    @Override
    public void bindParentAndChild(String parentId, String childId, String userSuperApp, String userEmail) throws RuntimeException {
        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));
        if (childId.equals(parentId)) {
            throw new RuntimeException("can't bind the same object");
        }
        if (!userEntity.getRole().equals(UserRole.SUPERAPP_USER))
            throw new PermissionDeniedException("User do not have permission to bindParentAndChild Objects");

        SuperAppObjectEntity parent = checkSuperAppObjectEntityExist(new ObjectId(springAppName, parentId));
        SuperAppObjectEntity child = checkSuperAppObjectEntityExist(new ObjectId(springAppName, parentId));

        boolean isChildAlreadyAssociated = parent.getChildObjects().stream()
                .anyMatch(existingChild -> existingChild.equals(child));

        if (!isChildAlreadyAssociated) {
            parent.getChildObjects().add(child);
            child.getParentObjects().add(parent);
            objectRepository.save(child);
            objectRepository.save(parent);
        } else {
            throw new RuntimeException("child and parent already bind");
        }
    }

    private UserEntity checkUserExist(UserId userId) {
        return this.userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("inserted id: "
                        + userId.getEmail() + userId.getSuperapp() + " does not exist"));
    }
    private SuperAppObjectEntity checkSuperAppObjectEntityExist(ObjectId objectId) {
        return objectRepository.findById(objectId)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    @Deprecated
    public Set<SuperAppObjectBoundary> getAllChildren(String objectId) {
        throw new DepreacatedOpterationException("dont use this func ");
    }

    @Override
    public List<SuperAppObjectBoundary> getAllChildren(String internalObjectId, String userSuperApp, String userEmail, int size, int page) {

        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));
        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {
            SuperAppObjectEntity objectEntity = checkSuperAppObjectEntityExist(new ObjectId(springAppName, internalObjectId));

            SuperAppObjectEntity parent = checkSuperAppObjectEntityExist(new ObjectId(springAppName, internalObjectId));

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
        UserEntity userEntity = checkUserExist(new UserId(userSuperapp, userEmail));

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
        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));

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

        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));

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

        UserEntity userEntity = checkUserExist(new UserId(userSuperApp, userEmail));

        GeneralUtility generalUtility = new GeneralUtility();
        if (userEntity.getRole().equals(UserRole.SUPERAPP_USER)) {

            return this.objectRepository
                    .findByLocationWithinRadius(latitude, longitude,
                            new Distance(distance, generalUtility.parseDistanceUnit(distanceUnits)),
                            PageRequest.of(page, size, Sort
                                    .Direction.ASC, "creationTimestamp", "objectId"))
                    .stream()
                    .map(this::entityToBoundary)
                    .toList();

        } else if (userEntity.getRole().equals(UserRole.MINIAPP_USER)) {
            return this.objectRepository
                    .findByLocationNearAndActiveIsTrue(new Point(latitude, longitude),
                            new Distance(distance, generalUtility.parseDistanceUnit(distanceUnits)),
                            PageRequest.of(page, size, Sort
                                    .Direction.ASC, "creationTimestamp", "_id"))
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
        obj.setLocation(new Location(entity.getLocation().getX(), entity.getLocation().getY()));
        return obj;
    }


    public SuperAppObjectEntity boundaryToEntity(SuperAppObjectBoundary obj) {
        SuperAppObjectEntity rv = new SuperAppObjectEntity();

        rv.setObjectId(obj.getObjectId());
        rv.setActive(obj.getActive());
        rv.setLocation(new Point(obj.getLocation().getLat(), obj.getLocation().getLng()));
        rv.setType(obj.getType());
        rv.setObjectDetails(obj.getObjectDetails());
        rv.setAlias(obj.getAlias());
        rv.setCreatedBy(obj.getCreatedBy());
        rv.setCreationTimestamp(obj.getCreationTimestamp());

        return rv;
    }
}
