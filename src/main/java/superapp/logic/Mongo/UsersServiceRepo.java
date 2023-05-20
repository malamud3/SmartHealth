package superapp.logic.Mongo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import superapp.logic.Exceptions.DepreacatedOpterationException;
import superapp.logic.Exceptions.PermissionDeniedException;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.dal.UserRepository;
import superapp.data.Enum.UserRole;
import superapp.logic.Exceptions.UserNotFoundException;
import superapp.data.mainEntity.UserEntity;
import jakarta.annotation.PostConstruct;
import superapp.logic.service.UsersServiceWithAdminPermission;
import superapp.logic.utilitys.GeneralUtility;
import superapp.logic.utilitys.UserUtility;

@Service

public class UsersServiceRepo implements UsersServiceWithAdminPermission {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private String springAppName;

    @Autowired
    public UsersServiceRepo(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }


    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @PostConstruct
    public void init() {
        if (!mongoTemplate.collectionExists("USERS")) {
            mongoTemplate.createCollection("USERS");
        }
    }


    @Override
    public UserBoundary createUser(NewUserBoundary newUser) throws RuntimeException {

        try {
            validateUser(newUser);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        UserBoundary user = new UserBoundary(newUser, springAppName);
        UserEntity userEntity = this.boundaryToEntity(user);
        userEntity = this.userRepository.save(userEntity);

        return this.entityToBoundary(userEntity);
    }


    @Override
    public Optional<UserBoundary> login(String userSuperApp, String userEmail) throws RuntimeException {
        UserId userId = new UserId(userSuperApp, userEmail);
        UserEntity entity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("could not find user with id: " + userSuperApp + "_" + userEmail));
        UserBoundary boundary = entityToBoundary(entity);
        return Optional.of(boundary);
    }


    @Override
    public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) throws RuntimeException{
        UserId userId = new UserId(userSuperApp, userEmail);
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        UserEntity existing = optionalUser.orElseThrow(() -> new UserNotFoundException("could not find user with id: " + userSuperApp + "_" + userEmail));

        boolean dirtyFlag = false;

        if (update.getRole() != null) {
            existing.setRole(UserRole.valueOf(update.getRole()));
            dirtyFlag = true;
        }

        if (update.getUsername() != null) {
            existing.setUsername(update.getUsername());
            dirtyFlag = true;
        }

        if (update.getAvatar() != null) {
            existing.setAvatar(update.getAvatar());
            dirtyFlag = true;
        }

        if (dirtyFlag) {
            userRepository.save(existing);
        }

        return entityToBoundary(existing);
    }


    @Override
    public List<UserBoundary> getAllUsers() throws RuntimeException {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(this::entityToBoundary)
                .collect(Collectors.toList());
    }


    @Override
    @Deprecated
    public void deleteAllUsers() throws RuntimeException {
    	throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
    }
    
    
    /**
     * delete all users
     *
     * @param userId the user ID of the userId the ID of the user attempting to delete all users
     * 
     * @throws UserNotFoundException if the user with the specified ID doesn't exist
     * @throws PermissionDeniedException if the user doesn't have permission to delete all users
     */
    @Override
    public void deleteAllUsers(UserId userId) throws RuntimeException {
    	UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(()->new UserNotFoundException("inserted id: " 
    	+ userId.toString() + " does not exist"));
    		
    	if (userEntity.getRole() != UserRole.ADMIN) {
    		throw new PermissionDeniedException("You do not have permission to delete all users");
    	}
    	this.userRepository.deleteAll();
    }


    /**
     * Converts a UserBoundary object to a UserEntity object.
     *
     * @param user the UserBoundary object to convert
     * @return a UserEntity object representing the same data as the input UserBoundary object,
     * or null if any required fields are null
     */
    public UserEntity boundaryToEntity(UserBoundary user) {

        // should not be null
        if (user.getUserId() == null ||
                user.getRole() == null ||
                user.getUsername() == null ||
                user.getAvatar() == null) {
            return null;
        }

        UserEntity rv = new UserEntity();

        rv.setUserId(user.getUserId());
        rv.setRole(UserRole.valueOf(user.getRole()));

        rv.setUsername(user.getUsername());

        rv.setAvatar(user.getAvatar());

        return rv;

    }

    /**
     * Converts a UserEntity object to a UserBoundary object.
     *
     * @param user the UserEntity object to convert
     * @return a UserBoundary object representing the same data as the input UserEntity object,
     * or null if any required fields are null
     */
    public UserBoundary entityToBoundary(UserEntity user) {

        // should not be null
        if (user.getUserId() == null ||
                user.getRole() == null ||
                user.getUsername() == null ||
                user.getAvatar() == null) {
            return null;
        }

        UserBoundary rv = new UserBoundary();

        rv.setUserId(user.getUserId());
        rv.setRole(user.getRole().name());
        rv.setUsername(user.getUsername());
        rv.setAvatar(user.getAvatar());

        return rv;

    }

    /**
     * Validates the given NewUserBoundary object.
     *
     * @param newUser the NewUserBoundary object to validate
     * @throws IllegalArgumentException if the email address is invalid, role is invalid, username is null or empty, or avatar is null or empty
     * @throws RuntimeException         if a user with the same email address already exists in the database
     */
    private void validateUser(NewUserBoundary newUser) throws RuntimeException {
        UserUtility userUtility = new UserUtility();
        GeneralUtility generalUtility = new GeneralUtility();

        // Check if email is valid
        if (!superapp.logic.utilitys.UserUtility.isValidEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("Invalid email address: " + newUser.getEmail());
        }

        // Check if role is valid
        if (!userUtility.isUserRoleValid(newUser.getRole())) {
            throw new IllegalArgumentException("Invalid role: " + newUser.getRole());
        }

        // Check if the user already exists
        // Check if the user already exists
        UserId userId = new UserId(newUser.getEmail(), this.springAppName);
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User with email " + newUser.getEmail() + " already exists");
        }

        //check if userName is not empty or null
        if (generalUtility.isStringEmptyOrNull(newUser.getUsername())) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        //check if avatar is not empty or null
        if (generalUtility.isStringEmptyOrNull(newUser.getAvatar())) {
            throw new IllegalArgumentException("Avatar cannot be null or empty");
        }

    }
}


