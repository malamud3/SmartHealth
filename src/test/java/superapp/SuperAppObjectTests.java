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

		NewUserBoundary newSuperappBoundary = new NewUserBoundary();
		newSuperappBoundary.setAvatar("superapp_avatar");
		newSuperappBoundary.setRole("SUPERAPP_USER");
		newSuperappBoundary.setEmail("superapp@example.com");
		newSuperappBoundary.setUsername("superapp_userName");

		UserBoundary superappUser = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newSuperappBoundary,
				UserBoundary.class);


		// WHEN I POST /superapp/objects with newSuperappObjectBoundary
		//AND I get /superapp/objects/{superapp}/{internalObjectId} the actualObjectBoundary
		SuperAppObjectBoundary newSuperAppObjectBoundary = new SuperAppObjectBoundary();
		newSuperAppObjectBoundary.setType("exampleType");
		newSuperAppObjectBoundary.setAlias("exampleAlias");
		newSuperAppObjectBoundary.setActive(true);
		Date now = new Date();
		newSuperAppObjectBoundary.setCreationTimestamp(now);
		newSuperAppObjectBoundary.setLocation(new Location(1.0, 2.0));
		newSuperAppObjectBoundary.setCreatedBy(new CreatedBy((superappUser.getUserId())));
		newSuperAppObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		SuperAppObjectBoundary expectedObjectBoundary = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/objects",
				newSuperAppObjectBoundary,
				SuperAppObjectBoundary.class
				);

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

		NewUserBoundary newMiniappBoundary = new NewUserBoundary();
		newMiniappBoundary.setAvatar("miniapp_avatar");
		newMiniappBoundary.setRole("MINIAPP_USER");
		newMiniappBoundary.setEmail("miniapp@example.com");
		newMiniappBoundary.setUsername("miniapp_userName");

		UserBoundary miniappUser = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newMiniappBoundary,
				UserBoundary.class);

		// WHEN I POST /superapp/objects with newMiniappObjectBoundary
		SuperAppObjectBoundary newMiniappObjectBoundary = new SuperAppObjectBoundary();
		newMiniappObjectBoundary.setType("exampleType");
		newMiniappObjectBoundary.setAlias("exampleAlias");
		newMiniappObjectBoundary.setActive(true);
		Date now = new Date();
		newMiniappObjectBoundary.setCreationTimestamp(now);
		newMiniappObjectBoundary.setLocation(new Location(1.0, 2.0));
		newMiniappObjectBoundary.setCreatedBy(new CreatedBy((miniappUser.getUserId())));
		newMiniappObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.postForObject(this.baseUrl + "/superapp/objects",
					newMiniappObjectBoundary, SuperAppObjectBoundary.class);
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
		SuperAppObjectBoundary newAdminObjectBoundary = new SuperAppObjectBoundary();
		newAdminObjectBoundary.setType("exampleType");
		newAdminObjectBoundary.setAlias("exampleAlias");
		newAdminObjectBoundary.setActive(true);
		Date now = new Date();
		newAdminObjectBoundary.setCreationTimestamp(now);
		newAdminObjectBoundary.setLocation(new Location(1.0, 2.0));
		newAdminObjectBoundary.setCreatedBy(new CreatedBy((adminUserId)));
		newAdminObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.postForObject(this.baseUrl + "/superapp/objects",
					newAdminObjectBoundary, SuperAppObjectBoundary.class);
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

		NewUserBoundary newSuperappBoundary = new NewUserBoundary();
		newSuperappBoundary.setAvatar("superapp_avatar");
		newSuperappBoundary.setRole("SUPERAPP_USER");
		newSuperappBoundary.setEmail("superapp@example.com");
		newSuperappBoundary.setUsername("superapp_userName");

		UserBoundary superappUser = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newSuperappBoundary,
				UserBoundary.class);

		SuperAppObjectBoundary newSuperAppObjectBoundary = new SuperAppObjectBoundary();
		newSuperAppObjectBoundary.setType("exampleType");
		newSuperAppObjectBoundary.setAlias("exampleAlias");
		newSuperAppObjectBoundary.setActive(true);
		Date now = new Date();
		newSuperAppObjectBoundary.setCreationTimestamp(now);
		newSuperAppObjectBoundary.setLocation(new Location(1.0, 2.0));
		newSuperAppObjectBoundary.setCreatedBy(new CreatedBy((superappUser.getUserId())));
		newSuperAppObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		SuperAppObjectBoundary existsObjectBoundary = this.restTemplate.postForObject(
				this.baseUrl + "/superapp/objects",
				newSuperAppObjectBoundary,
				SuperAppObjectBoundary.class
				);

		// WHEN I PUT /superapp/objects/{superapp}/{internalObjectId} updated object

		existsObjectBoundary.setType("updatedType");
		existsObjectBoundary.setAlias("updatedAlias");
		existsObjectBoundary.setActive(false);
		existsObjectBoundary.setLocation(new Location(2.0, 1.0));
		existsObjectBoundary.setObjectDetails(Map.of("updatedkey", "updatedVlaue"));

		this.restTemplate.put(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}"
				+ "?userSuperapp={userSuperapp}&userEmail={userEmail}",
				existsObjectBoundary, existsObjectBoundary.getObjectId().getSuperapp(),
				existsObjectBoundary.getObjectId().getInternalObjectId(),
				superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());

		SuperAppObjectBoundary actualUpdatedObjectBoundary = this.restTemplate
				.getForObject(this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}"
						+ "?userSuperapp={userSuperapp}&userEmail={userEmail}", SuperAppObjectBoundary.class,
						existsObjectBoundary.getObjectId().getSuperapp(), existsObjectBoundary.getObjectId().getInternalObjectId(),
						superappUser.getUserId().getSuperapp(), superappUser.getUserId().getEmail());

		// THEN  the existsObjectBoundary should be the same as the actualUpdatedObjectBoundary 
		assertThat(actualUpdatedObjectBoundary)
		.isNotNull()
		.usingRecursiveComparison().isEqualTo(existsObjectBoundary);

	}


}
