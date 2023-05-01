package superapp.logic.mockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import superapp.Boundary.User.UserBoundary;
import superapp.data.Enum.UserRole;
import superapp.logic.service.UsersService;
import superapp.data.mainEntity.UserEntity;
import jakarta.annotation.PostConstruct;

@Service

public class UsersServiceMockup implements UsersService {
	//private UserRepository userRepository;

	private Map<String,UserEntity> dbMockup;
    private  String springAppName;

//	@Autowired
//	public UsersServiceMockup(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}


    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @PostConstruct
    public void init()
    {
        this.dbMockup = Collections.synchronizedMap(new HashMap<>());
        System.err.println("******"+this.springAppName); //test
    }
	
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserEntity userEntity = this.boundaryToEntity(user);
		
		this.dbMockup.put(userEntity.getUserId().getSuperapp() + "_" + userEntity.getUserId().getEmail()
		, userEntity);
		
		return this.entityToBoundary(userEntity);
	}

	@Override
	public Optional<UserBoundary> login(String userSuperApp, String userEmail) {
		String userKey = userSuperApp + "_" + userEmail; //temp user key
		
		UserEntity entity = this.dbMockup.get(userKey);
		if (entity == null) {			
			return Optional.empty();
		}else {
		    UserBoundary boundary = this.entityToBoundary(entity);
			return Optional.of(boundary);
		}
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail,@NotNull UserBoundary update) {
		String userKey = userSuperApp + "_" + userEmail; // temp user key
		UserEntity existing = this.dbMockup.get(userKey);
		UserBoundary userBoundary = new UserBoundary();

		if (existing == null) {
			throw new RuntimeException("Could not find user by id: " + userKey);
		} else {
			if(existing == null){
				userBoundary = this.createUser(update);
			}else if (update.getRole() != null && update.getUsername() != null && update.getAvatar() != null ) {
				userBoundary = entityToBoundary(existing);
				userBoundary.setRole(update.getRole());
				userBoundary.setUsername(update.getUsername());
				userBoundary.setAvatar(update.getAvatar());
			}
			return userBoundary;
		}
	}



	@Override
	public List<UserBoundary> getAllUsers() {
		return this.dbMockup.values()
				.stream() 
				.map(this::entityToBoundary)
				.toList();
	}

	@Override
	public void deleteAllUsers() {
		this.dbMockup.clear();
	}


	/**
	 * Converts a UserBoundary object to a UserEntity object.
	 *
	 * @param user the UserBoundary object to convert
	 * @return a UserEntity object representing the same data as the input UserBoundary object,
	 *         or null if any required fields are null
	 */
	public UserEntity boundaryToEntity (@NotNull UserBoundary user) {

		// should not be null
		if (	user.getUserId() == null ||
				user.getRole() == null ||
				user.getUsername() == null ||
				user.getAvatar() == null){
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
	 *         or null if any required fields are null
	 */
	public UserBoundary entityToBoundary (@NotNull UserEntity user) {

	// should not be null
	if (	user.getUserId() == null ||
			user.getRole() == null ||
			user.getUsername() == null ||
			user.getAvatar() == null){
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
