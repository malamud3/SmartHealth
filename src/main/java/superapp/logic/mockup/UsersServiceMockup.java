package superapp.logic.mockup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.dal.UserRepository;
import superapp.data.Enum.UserRole;
import superapp.logic.service.SuperAppObjectRelationshipService;
import superapp.logic.service.UsersService;
import superapp.data.mainEntity.UserEntity;
import jakarta.annotation.PostConstruct;
import superapp.logic.utilitys.userUtility;

@Service

public class UsersServiceMockup implements UsersService {
	private UserRepository userRepository;
	private  MongoTemplate mongoTemplate;

	private String springAppName;

	@Autowired
	public UsersServiceMockup(UserRepository userRepository, MongoTemplate mongoTemplate) {
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
	public UserBoundary createUser(NewUserBoundary newUser) {

		try {
			validateUser(newUser);
		} catch (IllegalArgumentException e) {
			return  null;
		}

		UserBoundary user = new UserBoundary(newUser, springAppName);
		UserEntity userEntity = this.boundaryToEntity(user);
		userEntity = this.userRepository.save(userEntity);

		return this.entityToBoundary(userEntity);
	}


	@Override
	public Optional<UserBoundary> login(String userSuperApp, String userEmail) {
		UserId userId = new UserId(userSuperApp, userEmail);
		Optional<UserEntity> entity = userRepository.findByUserId(userId);
		if (entity.isPresent()) {
			UserBoundary boundary = entityToBoundary(entity.get());
			return Optional.of(boundary);
		} else {
			return Optional.empty();
		}
	}


	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		UserId userId = new UserId(userSuperApp, userEmail);
		Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
		UserEntity existing = optionalUser.orElseThrow(() -> new RuntimeException("could not find user with id: " + userSuperApp + "_" + userEmail));

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
	public List<UserBoundary> getAllUsers() {
		List<UserEntity> userEntities = userRepository.findAll();
		return userEntities.stream()
				.map(this::entityToBoundary)
				.collect(Collectors.toList());
	}


	@Override
	public void deleteAllUsers() {
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
	 Validates the given NewUserBoundary object.
	 @param newUser the NewUserBoundary object to validate
	 @throws IllegalArgumentException if the email address is invalid, role is invalid, username is null or empty, or avatar is null or empty
	 @throws RuntimeException if a user with the same email address already exists in the database
	 */
	private void validateUser(NewUserBoundary newUser) {
		userUtility UserUtility = new userUtility();

		// Check if email is valid
		if (!UserUtility.isValidEmail(newUser.getEmail())) {
			throw new IllegalArgumentException("Invalid email address: " + newUser.getEmail());
		}

		// Check if role is valid
		if(UserUtility.isUserRoleValid(newUser.getRole())){
			throw new IllegalArgumentException("Invalid role: " + newUser.getRole());
		}

		// Check if the user already exists
		UserId userId = new UserId(newUser.getEmail(), this.springAppName);
		if (this.userRepository.existsById(userId)) {
			throw new RuntimeException("User with email " + newUser.getEmail() + " already exists");
		}

		if (StringUtils.isEmpty(newUser.getUsername())) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}

		if (StringUtils.isEmpty(newUser.getAvatar())) {
			throw new IllegalArgumentException("Avatar cannot be null or empty");
		}

	}
}


