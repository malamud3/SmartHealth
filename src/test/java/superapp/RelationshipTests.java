package superapp;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.CreatedBy;
import superapp.Boundary.Location;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RelationshipTests {

    private RestTemplate restTemplate;
    private String baseUrl;
    private int port;
    private UserId adminUserId;


    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }


    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }
    
    @BeforeEach
	private void createAdminUser() {
		NewUserBoundary newAdminBoundary = new NewUserBoundary();
		newAdminBoundary.setAvatar("admin_avatar");
		newAdminBoundary.setRole("ADMIN");
		newAdminBoundary.setEmail("admin@example.com");
		newAdminBoundary.setUsername("admin_userName");

		adminUserId = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newAdminBoundary,
				UserBoundary.class
				).getUserId();

	}


	@AfterEach
	public void tearDown() {	
		//clean the superappObjects collection
		this.restTemplate
		.delete(this.baseUrl + "/superapp/admin/objects?userSuperapp={userSuperapp}&userEmail={userEmail}",
				adminUserId.getSuperapp(), adminUserId.getEmail()); 

		//clean the usersObjects DB
		this.restTemplate
		.delete(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
				adminUserId.getSuperapp(), adminUserId.getEmail());
	}
	
	@Test
	@DisplayName("test create relationship")
	public void testCreateRelationship() {
		//GIVEN the database is up
		//AND there are 2 SuperappObjects in  the server
		//AND a SUPERAPP_USER
		
		UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
		
		UserId userId = superappUser.getUserId();
		
		SuperAppObjectBoundary parentObjectBoundary = createExampleSuperappObject("parentType", "parentAlias", true, userId);
		
		SuperAppObjectBoundary childObjectBoundary = createExampleSuperappObject("childType", "childAlias", true, userId);
		
		//WHEN I PUT /superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email} to bind parent and child
		
		this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email}",
				childObjectBoundary.getObjectId(), parentObjectBoundary.getObjectId().getSuperapp(), parentObjectBoundary.getObjectId().getInternalObjectId(),
				userId.getSuperapp(), userId.getEmail());
		
		//THEN GET All children of parentObject should contain only the child object
		//AND GET ALL parents of childObject should contain only the parent object
		
		SuperAppObjectBoundary[] children = this.restTemplate.getForObject(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email}",
				SuperAppObjectBoundary[].class, parentObjectBoundary.getObjectId().getSuperapp(), parentObjectBoundary.getObjectId().getInternalObjectId(),
				userId.getSuperapp(), userId.getEmail());
		
		SuperAppObjectBoundary[] parents = this.restTemplate.getForObject(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/parents?userSuperapp={userSuperapp}&userEmail={email}",
				SuperAppObjectBoundary[].class, childObjectBoundary.getObjectId().getSuperapp(), childObjectBoundary.getObjectId().getInternalObjectId(),
				userId.getSuperapp(), userId.getEmail());
		
		assertThat(children)
		.hasSize(1)
		.contains(childObjectBoundary);
		
		assertThat(parents)
		.hasSize(1)
		.contains(parentObjectBoundary);
	}
	
	@Test
	@DisplayName("test miniappUser create relationship")
	public void testMiniappUserCreateRelationship() {
		//GIVEN the database is up
		//AND there are 2 SuperappObjects in  the server
		//AND a MINIAPP_USER and a SUPERAPP_USER (to create the objects)
		
		UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
		
		UserBoundary miniappUser = createExampleUser("MINIAPP_USER");
		
		UserId userId = miniappUser.getUserId();
		
		SuperAppObjectBoundary parentObjectBoundary = createExampleSuperappObject("parentType", "parentAlias",
				true, superappUser.getUserId());
		
		SuperAppObjectBoundary childObjectBoundary = createExampleSuperappObject("childType", "childAlias",
				true, superappUser.getUserId());
		
		//WHEN I PUT /superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email} to bind parent and child
		
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
		    this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email}",
		        childObjectBoundary.getObjectId(), parentObjectBoundary.getObjectId().getSuperapp(), parentObjectBoundary.getObjectId().getInternalObjectId(),
		        userId.getSuperapp(), userId.getEmail());
		});

		 //THEN an HTTP FORBIDDEN (403 error code) thrown
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("test Admin create relationship")
	public void testAdminUserCreateRelationship() {
		//GIVEN the database is up
		//AND there are 2 SuperappObjects in  the server
		//AND a ADMIN and a SUPERAPP_USER (to create the objects)
		
		UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
		
		UserBoundary adminUser = createExampleUser("ADMIN");
		
		UserId userId = adminUser.getUserId();
		
		SuperAppObjectBoundary parentObjectBoundary = createExampleSuperappObject("parentType", "parentAlias",
				true, superappUser.getUserId());
		
		SuperAppObjectBoundary childObjectBoundary = createExampleSuperappObject("childType", "childAlias",
				true, superappUser.getUserId());
		
		//WHEN I PUT /superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email} to bind parent and child
		
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
		    this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/children?userSuperapp={userSuperapp}&userEmail={email}",
		        childObjectBoundary.getObjectId(), parentObjectBoundary.getObjectId().getSuperapp(), parentObjectBoundary.getObjectId().getInternalObjectId(),
		        userId.getSuperapp(), userId.getEmail());
		});

		 //THEN an HTTP FORBIDDEN (403 error code) thrown
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}
	
	
	@Test
	@DisplayName("test create object to itslef relationship")
	public void testCreateSameRelationship() {
		// GIVEN there is 1 superappObject
		// AND there is 1 superappUser
		
		UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
		SuperAppObjectBoundary relationshipObject =
				createExampleSuperappObject("exampleType", "exampleAlias", false, superappUser.getUserId());
		
		
		// WHEN I PUT /superapp/objects/{superapp}/{internalObjectId}/children to bind object to itself
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}/children"
					+ "?userSuperapp={userSuperapp}&userEmail={email}",
					relationshipObject.getObjectId(), relationshipObject.getObjectId().getSuperapp(), relationshipObject.getObjectId().getInternalObjectId(),
					superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());
		});
		
		//THEN an HTTP BAD REQUEST (400 error code) thrown
		assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
	}
	
	private SuperAppObjectBoundary createExampleSuperappObject(String type, String alias, boolean active, UserId creatorId) {
		SuperAppObjectBoundary newParentObjectBoundary = new SuperAppObjectBoundary();
		newParentObjectBoundary.setType(type);
		newParentObjectBoundary.setAlias(alias);
		newParentObjectBoundary.setActive(active);
		newParentObjectBoundary.setCreationTimestamp(new Date());
		newParentObjectBoundary.setLocation(new Location(1.0, 2.0));
		newParentObjectBoundary.setCreatedBy(new CreatedBy((creatorId)));
		newParentObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		return this.restTemplate.postForObject(
				this.baseUrl + "/superapp/objects",
				newParentObjectBoundary,
				SuperAppObjectBoundary.class
				);
		
		
	}

	private UserBoundary createExampleUser(String role) {
		NewUserBoundary newSuperappBoundary = new NewUserBoundary();
		newSuperappBoundary.setAvatar("example_avatar");
		newSuperappBoundary.setRole(role);
		newSuperappBoundary.setEmail(role + "@example.com");
		newSuperappBoundary.setUsername("superapp_userName");

		return this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newSuperappBoundary,
				UserBoundary.class);
	}

}
