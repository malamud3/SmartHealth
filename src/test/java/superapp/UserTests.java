package superapp;

import jakarta.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    private RestTemplate restTemplate;
    private String baseUrl;
    private int port;
    //private String springAppName;

//    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
//    public void setSpringApplicationName(String springApplicationName) {
//        this.springAppName = springApplicationName;
//    }

    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }
    
	@AfterEach
	public void tearDown () {
		//create ADMIN USER to delete all users
		    NewUserBoundary newAdminBoundary = new NewUserBoundary();
	        newAdminBoundary.setAvatar("admin_avatar");
	        newAdminBoundary.setRole("ADMIN");
	        newAdminBoundary.setEmail("admin@example.com");
	        newAdminBoundary.setUsername("admin_userName");
	        
	        UserBoundary adminBoundary = this.restTemplate.postForObject(
	                this.baseUrl + "/superapp/users",
	                newAdminBoundary,
	                UserBoundary.class
	        );
		
		this.restTemplate
			.delete(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
					adminBoundary.getUserId().getSuperapp(), adminBoundary.getUserId().getEmail());
	}


    @Test
    @DisplayName("test post user")
    public void testPostUser() {
    	// GIVEN the server is up
		// AND the database is empty
		
		// WHEN I POST /superapp/useres with new example user
    	 NewUserBoundary newUserBoundary = new NewUserBoundary();
         newUserBoundary.setAvatar("example_avatar");
         newUserBoundary.setRole("ADMIN");//set to admin to get all users
         newUserBoundary.setEmail("example55545@example.com");
         newUserBoundary.setUsername("example_userName");
		
		
         UserBoundary expectedUserBoundary = this.restTemplate.postForObject(
                 this.baseUrl + "/superapp/users",
                 newUserBoundary,
                 UserBoundary.class
         );
		
		// THEN the database contains a single userBoundary with the same content
		assertThat(this.restTemplate
				.getForObject(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
						UserBoundary[].class, expectedUserBoundary.getUserId().getSuperapp(), expectedUserBoundary.getUserId().getEmail()))
			.hasSize(1)
			.usingRecursiveFieldByFieldElementComparatorOnFields(
					"avatar", "role", "userId","username" )
			.containsExactly(expectedUserBoundary);
    }

    @Test
    @DisplayName("test get user by userId")
    public void testGetUserByUserId() {
    	// GIVEN the server is up
    	// AND there is one user in the DB
    	
    	NewUserBoundary newUserBoundary = new NewUserBoundary();
    	newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("SUPERAPP_USER");
        newUserBoundary.setEmail("example55545@example.com");
        newUserBoundary.setUsername("example_userName");
        
        UserBoundary expectedUserBoundary = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/users",
                newUserBoundary,
                UserBoundary.class
        );
    			
    	// WHEN I GET /superapp/users/login/{superapp}/{email} of the user by userId
        UserBoundary actualUserBoundary = this.restTemplate
		.getForObject(this.baseUrl + "/superapp/users/login/{superapp}/{email}",
				UserBoundary.class, expectedUserBoundary.getUserId().getSuperapp(), expectedUserBoundary.getUserId().getEmail());


        // THEN  the expectedUserBoundary should be the same as the actualUserBoundary 
        assertThat(actualUserBoundary)
        .isNotNull()
        .usingRecursiveComparison().isEqualTo(expectedUserBoundary);

    }


    @Test
    @DisplayName("test update user")
    public void testUpdateUser() {
    	// GIVEN the server is up
    	// AND there is one user in the DB
    	
    	NewUserBoundary newUserBoundary = new NewUserBoundary();
    	newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("SUPERAPP_USER");
        newUserBoundary.setEmail("example55545@example.com");
        newUserBoundary.setUsername("example_userName");
        
        UserBoundary userBoundaryToUpdate = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/users",
                newUserBoundary,
                UserBoundary.class
        );

        // WHEN I PUT /superapp/users/{superapp}/{userEmail} an updated userBoundaryToUpdate 
        //AND I GET /superapp/users/login/{superapp}/{email} the actualUpdatedUserBoundary
        userBoundaryToUpdate.setAvatar("new_avatar");
        userBoundaryToUpdate.setRole("ADMIN");
        userBoundaryToUpdate.setUsername("new_username");
        
        this.restTemplate.put(this.baseUrl + "/superapp/users/{superapp}/{userEmail}",userBoundaryToUpdate,
        		 userBoundaryToUpdate.getUserId().getSuperapp(), userBoundaryToUpdate.getUserId().getEmail() );
        
        UserBoundary actualUpdatedUserBoundary = this.restTemplate
        		.getForObject(this.baseUrl + "/superapp/users/login/{superapp}/{email}",
        				UserBoundary.class, userBoundaryToUpdate.getUserId().getSuperapp(), userBoundaryToUpdate.getUserId().getEmail());
        
        
        // THEN the actualUpdatedUserBoundary should be the same as the userBoundaryToUpdate
        assertThat(actualUpdatedUserBoundary)
        .isNotNull()
        .usingRecursiveComparison().isEqualTo(userBoundaryToUpdate);
    }
    
    @Test
    @DisplayName("test get all users by miniapp user")
    public void testGetAllUsersByMiniappUser() {
    	// GIVEN the server is up
    	// AND there is one user in the DB with MINIAPP_USER role
    	
    	NewUserBoundary newUserBoundary = new NewUserBoundary();
    	newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("MINIAPP_USER");
        newUserBoundary.setEmail("example55545@example.com");
        newUserBoundary.setUsername("example_userName");
        
        UserBoundary miniappUserBoundary = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/users",
                newUserBoundary,
                UserBoundary.class
        );

        // WHEN I GET /superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail} by miniappUserBoundary's UserId 
        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
            		UserBoundary[].class, miniappUserBoundary.getUserId().getSuperapp(),
            		miniappUserBoundary.getUserId().getEmail());
        });

        //THEN an HTTP FORBIDDEN (403 error code) thrown
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
    
    @Test
    @DisplayName("test get all users by superapp user")
    public void testGetAllUsersBySuperappUser() {
    	// GIVEN the server is up
    	// AND there is one user in the DB with SUPERAPP_USER role
    	
    	NewUserBoundary newUserBoundary = new NewUserBoundary();
    	newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("SUPERAPP_USER");
        newUserBoundary.setEmail("example55545@example.com");
        newUserBoundary.setUsername("example_userName");
        
        UserBoundary superappUserBoundary = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/users",
                newUserBoundary,
                UserBoundary.class
        );

        // WHEN I GET /superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail} by superappUserBoundary's UserId 
        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
            		UserBoundary[].class, superappUserBoundary.getUserId().getSuperapp(),
            		superappUserBoundary.getUserId().getEmail());
        });

        //THEN an HTTP FORBIDDEN (403 error code) thrown
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
    
    

}
