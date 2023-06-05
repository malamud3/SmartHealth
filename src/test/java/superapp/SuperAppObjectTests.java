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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SuperAppObjectTests {

	private String baseUrl;
	private RestTemplate restTemplate;
	private int port;
	private UserId adminUserId;


	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}


	@PostConstruct
	public void setup() {
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
	@DisplayName("Test create Object")
	public void testCreateObject() {
		// GIVEN the server is up
		// AND the superappObject database is empty
		//AND there is a SUPERAPP_USER user

		UserBoundary superappUser = createExampleUser("SUPERAPP_USER"); 


		// WHEN I POST /superapp/objects with newSuperappObjectBoundary
		//AND I get /superapp/objects/{superapp}/{internalObjectId} the actualObjectBoundary
		SuperAppObjectBoundary expectedObjectBoundary = createExampleSuperappObject(true, superappUser.getUserId()); 

		SuperAppObjectBoundary actualObjectBoundary = this.restTemplate
				.getForObject(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}"
						+ "?userSuperapp={userSuperapp}&userEmail={userEmail}" ,
						SuperAppObjectBoundary.class, expectedObjectBoundary.getObjectId().getSuperapp(),
						expectedObjectBoundary.getObjectId().getInternalObjectId(), superappUser.getUserId().getSuperapp(),
						superappUser.getUserId().getEmail());


		// THEN the actualObjectBoundary should be the same as the expectedObjectBoundary
		assertThat(actualObjectBoundary)
		.isNotNull()
		.usingRecursiveComparison().isEqualTo(expectedObjectBoundary);
	}


	@Test
	@DisplayName("test create object with miniapp user")
	public void testMiniappUserCreateObject() {
		// GIVEN the server is up
		// AND the superappObject database is empty
		//AND there is a MINIAPP_USER user

		UserBoundary miniappUser =  createExampleUser("MINIAPP_USER");
		
		// WHEN I POST /superapp/objects with miniappUser
		
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			createExampleSuperappObject(false, miniappUser.getUserId()); 
		});

		//THEN an HTTP FORBIDDEN (403 error code) thrown
		assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	@DisplayName("test create object with admin user")
	public void testAdminUserCreateObject() {
		// GIVEN the server is up
		// AND the superappObject database is empty
		//AND there is an ADMIN user


		// WHEN I POST /superapp/objects with newMiniappObjectBoundary
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			createExampleSuperappObject(true, adminUserId);
		});

		//THEN an HTTP FORBIDDEN (403 error code) thrown
		assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	@DisplayName("Test Update Object")
	public void testUpdateObject() {
		// GIVEN the server is up
		// AND there is one SuperappObject on the server
		// AND there is a SUPERAPP user

		UserBoundary superappUser = createExampleUser("SUPERAPP_USER"); new NewUserBoundary();

		SuperAppObjectBoundary existsSuperAppObjectBoundary = 
				createExampleSuperappObject(true, superappUser.getUserId());

		// WHEN I PUT /superapp/objects/{superapp}/{internalObjectId} updated object

		existsSuperAppObjectBoundary.setType("updatedType");
		existsSuperAppObjectBoundary.setAlias("updatedAlias");
		existsSuperAppObjectBoundary.setActive(false);
		existsSuperAppObjectBoundary.setLocation(new Location(2.0, 1.0));
		existsSuperAppObjectBoundary.setObjectDetails(Map.of("updatedkey", "updatedVlaue"));

		this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}"
				+ "?userSuperapp={userSuperapp}&userEmail={userEmail}",
				existsSuperAppObjectBoundary, existsSuperAppObjectBoundary.getObjectId().getSuperapp(),
				existsSuperAppObjectBoundary.getObjectId().getInternalObjectId(),
				superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());

		SuperAppObjectBoundary actualUpdatedObjectBoundary = this.restTemplate
				.getForObject(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}"
						+ "?userSuperapp={userSuperapp}&userEmail={userEmail}", SuperAppObjectBoundary.class,
						existsSuperAppObjectBoundary.getObjectId().getSuperapp(), existsSuperAppObjectBoundary.getObjectId().getInternalObjectId(),
						superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());

		// THEN  the existsObjectBoundary should be the same as the actualUpdatedObjectBoundary 
		assertThat(actualUpdatedObjectBoundary)
		.isNotNull()
		.usingRecursiveComparison().isEqualTo(existsSuperAppObjectBoundary);

	}
	
	@Test
	@DisplayName("test get not active object")
	public void testGetNotActiveObject() {
		// GIVEN the server is up
		// AND there is a SuperappObject with active = flase
		//AND there is a SUPERAPP_USER and a MINIAPP_USER
		
		UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
		UserBoundary miniappUser = createExampleUser("MINIAPP_USER");
		SuperAppObjectBoundary object = createExampleSuperappObject(false, superappUser.getUserId());


		// WHEN I GET /superapp/objects using both users
		
		SuperAppObjectBoundary[] superappUserGetResponse = this.restTemplate
				.getForObject(this.baseUrl + "/superapp/objects?userSuperapp={userSuperapp}&userEmail={email}",
						SuperAppObjectBoundary[].class, superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());

		SuperAppObjectBoundary[] miniappUserGetResponse = this.restTemplate
				.getForObject(this.baseUrl + "/superapp/objects?userSuperapp={userSuperapp}&userEmail={email}",
						SuperAppObjectBoundary[].class, miniappUser.getUserId().getSuperapp(), miniappUser.getUserId().getEmail());
		
		// THEN superappUser get should return an array with exactly the sent object
		// AND miniappUser GET should return an empty array
		assertThat(superappUserGetResponse)
		.isNotNull()
		.hasSize(1)
		.containsExactly(object);
		
		assertThat(miniappUserGetResponse)
		.isNotNull()
		.hasSize(0);
		
	}

	
	private SuperAppObjectBoundary createExampleSuperappObject(boolean active, UserId creatorId) {
		SuperAppObjectBoundary newParentObjectBoundary = new SuperAppObjectBoundary();
		newParentObjectBoundary.setType("exampleType");
		newParentObjectBoundary.setAlias("exampleAlias");
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
