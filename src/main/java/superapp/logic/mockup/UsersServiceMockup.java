package superapp.logic.mockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import superapp.model.UserBoundary;
import superapp.model.UserId;
import superapp.logic.UsersService;
import superapp.data.UserEntity;
import jakarta.annotation.PostConstruct;

@Service
public class UsersServiceMockup implements UsersService {
	private Map<String,UserEntity> dbMockup;
    private  String springAppName;

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
		
		this.dbMockup.put(userEntity.getSuperapp() + "_" + userEntity.getEmail()
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
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		String userKey = userSuperApp + "_" + userEmail; //temp user key
		
		UserEntity existing = this.dbMockup.get(userKey);
		if (existing == null) {
			throw new RuntimeException("Could not find user by id: " + userKey);
		}
		boolean dirtyFlag = false;
		
		if (update.getRole() != null) {
			existing.setRole(update.getRole());
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
	
	public UserEntity boundaryToEntity (UserBoundary user) {
		
		UserEntity rv = new UserEntity();
		
		if(user.getUserId() == null) {
			rv.setSuperapp("");
			rv.setEmail("");
		}else {
			rv.setSuperapp(user.getUserId().getSuperapp());
			rv.setEmail(user.getUserId().getEmail());
		}
		if(user.getRole() == null) {
			rv.setRole("");
		}else {
			rv.setRole(user.getRole());
		}
		if(user.getUsername() == null) {
			rv.setUsername("");
		}else {
			rv.setUsername(user.getUsername());
		}
		if(user.getAvatar() == null) {
			rv.setAvatar("");
		}else {
			rv.setAvatar(user.getAvatar());
		}
		return rv;
		
	}
	
public UserBoundary entityToBoundary (UserEntity user) {
		
		UserBoundary rv = new UserBoundary();
		
		rv.setUserId(new UserId(user.getSuperapp(), user.getEmail()));
		rv.setRole(user.getRole());
		rv.setUsername(user.getUsername());
		rv.setAvatar(user.getAvatar());
		
		return rv;
		
	}
	
	

}
