package superapp.logic.mockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import superapp.Boundary.User.UserBoundary;
import superapp.dal.UserRepository;
import superapp.data.Enum.UserRole;
import superapp.logic.service.UsersService;
import superapp.data.mainEntity.UserEntity;
import jakarta.annotation.PostConstruct;



@Service

public class UsersServiceMockup implements UsersService {
	private UserRepository userRepository;

	private Map<String,UserEntity> dbMockup;
    private  String springAppName;

	@Autowired
	public UsersServiceMockup(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


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
			userEntity = this.userRepository.save(userEntity);

		return this.entityToBoundary(userEntity);
	}

	@Override
	public Optional<UserBoundary> login(String userSuperApp, String userEmail) {
		String userKey = userSuperApp + "_" + userEmail; //temp user key

		Optional<UserEntity> entity = userRepository.findByEmail(userEmail);
		if (entity.isPresent()){
			System.out.println(" "+entity);
		}
//		UserEntity entity = this.dbMockup.get(userKey);
		if (entity.get() == null) {
			return Optional.empty();
		}else {
		    UserBoundary boundary = this.entityToBoundary(entity.get());
			return Optional.of(boundary);
		}
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		String userKey = userSuperApp + "_" + userEmail; // temp user key


		UserEntity existing = this.dbMockup.get(userKey);

		if (existing == null) {
			throw new RuntimeException("Could not find user by id: " + userKey);
		}
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
			this.dbMockup.put(userKey, existing);
		}
		return this.entityToBoundary(existing);
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
	public UserEntity boundaryToEntity (UserBoundary user) {

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
	public UserBoundary entityToBoundary ( UserEntity user) {

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
