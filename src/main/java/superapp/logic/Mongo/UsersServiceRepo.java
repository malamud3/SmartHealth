package superapp.logic.Mongo;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import superapp.logic.Exceptions.DepreacatedOpterationException;
import superapp.logic.Exceptions.PermissionDeniedException;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.dal.UserRepository;
import superapp.data.UserRole;
import superapp.logic.Exceptions.UserNotFoundException;
import superapp.data.UserEntity;
import jakarta.annotation.PostConstruct;
import superapp.logic.service.UserServices.UsersServiceWithAdminPermission;
import superapp.logic.utilitys.GeneralUtility;
import superapp.logic.utilitys.UserUtility;


@Service

public class UsersServiceRepo implements UsersServiceWithAdminPermission {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final UserUtility userUtility;
    private  JmsTemplate jmsTemplate ;
    private String springAppName;


    @Autowired
    public UsersServiceRepo(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.userUtility = new UserUtility(userRepository);
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.jmsTemplate.setDeliveryDelay(3000L);
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



    @Async
    @Override
	public UserBoundary createUser(NewUserBoundary newUser) throws RuntimeException {
    	 try {
             userUtility.validateUser(newUser,springAppName);
         } catch (RuntimeException e) {
             throw new IllegalArgumentException(e.getMessage());
         }

         UserBoundary user = new UserBoundary(newUser, springAppName);
         UserEntity userEntity = boundaryToEntity(user);
         userEntity = userRepository.save(userEntity);

         // Sending JMS message
         String message = "New user created: " + user.getUserId();
         jmsTemplate.convertAndSend("userQueue", message);

         return entityToBoundary(userEntity);
	}

    @Deprecated
    @Override
    public UserBoundary createUser(UserBoundary newUser) throws RuntimeException {
    	throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");  
    }

    @Override
    public UserBoundary login(String userSuperApp, String userEmail) throws RuntimeException {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));

        UserBoundary boundary = entityToBoundary(userEntity);

        // Sending JMS message
        String message = "User logged in: " + boundary.getUserId();
        jmsTemplate.convertAndSend("userLoginQueue", message);

        return boundary;
    }



    @Override
    public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) throws RuntimeException {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));


        if (update.getRole() != null) {
            userEntity.setRole(UserRole.valueOf(update.getRole()));
        }

        if (update.getUsername() != null) {
        	userEntity.setUsername(update.getUsername());
        }

        if (update.getAvatar() != null) {
        	userEntity.setAvatar(update.getAvatar());
        }

        userEntity = userRepository.save(userEntity);

            // Sending JMS message
            String message = "User updated: " + userEntity.getUserId();
            jmsTemplate.convertAndSend("userUpdateQueue", message);

        return entityToBoundary(userEntity);
    }

    @Deprecated
    @Override
	public List<UserBoundary> getAllUsers() {
		throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
	}
    
    
    @Override
    public List<UserBoundary> exportAllUsers(String userSuperApp, String userEmail, int size, int page) throws RuntimeException {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));

        if (!userEntity.getRole().equals(UserRole.ADMIN) ) {
            throw new PermissionDeniedException("You do not have permission to get all users");
        }

        Page<UserEntity> userPage = userRepository.findAll(PageRequest.of(page,size, Sort.Direction.ASC , "id"));

        return userPage.getContent().stream()
                .map(this::entityToBoundary)
                .collect(Collectors.toList());
    }


    
    
    @Override
    @Deprecated
	public void deleteAllUsers() {
    	throw new DepreacatedOpterationException("do not use this operation any more, as it is deprecated");
	}
    
    /**
     * delete all users
     *
     * @param  userSuperApp and  userEmail ate user ID of the userId the ID of the user attempting to delete all users
     *
     * @throws UserNotFoundException if the user with the specified ID doesn't exist
     * @throws PermissionDeniedException if the user doesn't have permission to delete all users
     */

    @Override
    public void deleteAllUsers(String userSuperApp, String userEmail) throws RuntimeException {
        UserEntity userEntity = userUtility.checkUserExist(new UserId(userSuperApp,userEmail));

        if (!userEntity.getRole().equals(UserRole.ADMIN) ) {
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


}
