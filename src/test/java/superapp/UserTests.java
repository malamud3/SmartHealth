package superapp;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.controller.UsersRelatedAPIController;
import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @Autowired
    private UsersRelatedAPIController usersRelatedAPIController;
    private RestTemplate restTemplate;
    private String baseUrl;
    private int port;
    private String springAppName;

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }


    @Test
    @DisplayName("test successful post user")
    public void testPostUser() {
        // GIVEN the database is up
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("SUPERAPP_USER");
        newUserBoundary.setEmail("example55545@example.com");
        newUserBoundary.setUsername("example_userName");

        // WHEN a POST request is sent to create a new user
        UserBoundary userBoundary = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/users",
                newUserBoundary,
                UserBoundary.class
        );

        // THEN the user should be created successfully
        assertThat(userBoundary).isNotNull();
        assertThat(userBoundary.getUserId().getEmail()).isNotNull().isEqualTo((newUserBoundary.getEmail()));
        assertThat(userBoundary.getAvatar()).isEqualTo(newUserBoundary.getAvatar());
        assertThat(userBoundary.getRole()).isEqualTo(newUserBoundary.getRole());
        assertThat(userBoundary.getUsername()).isEqualTo(newUserBoundary.getUsername());
    }

    @Test
    @DisplayName("test successful get user by email")
    public void testGetUserByEmail() {
        // GIVEN the database has a user with email "example545@example.com"
        String email = "example545@example.com";
        String superApp = springAppName;
        UserBoundary expectedUser = new UserBoundary();
        UserId userId = new UserId();
        userId.setEmail(email);
        userId.setSuperapp(springAppName);
        expectedUser.setUserId(userId);
        expectedUser.setAvatar("example_avatar");
        expectedUser.setRole("MINIAPP_USER");
        expectedUser.setUsername("example_userName");

        // WHEN a GET request is sent to retrieve the user by email
        UserBoundary actualUser =  this.restTemplate.getForObject(
                this.baseUrl + "/superapp/users/login/"+superApp+"/"+email,
                UserBoundary.class
        );

        // THEN the user should be retrieved successfully
        assertThat(actualUser).isNotNull();
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);

    }


    @Test
    @DisplayName("test successful update user")
    public void testUpdateUser() {
        // GIVEN the database has a user with email "example545@example.com"
        String email = "example545@example.com";
        String superApp = springAppName;
        UserBoundary existingUser = new UserBoundary();
        existingUser.setAvatar("example_avatar");
        existingUser.setRole("MINIAPP_USER");
        existingUser.setUsername("example_userName");
        existingUser.setUserId(new UserId(superApp, email));

        // GIVEN an updated user object
        UserBoundary updatedUser = new UserBoundary();
        updatedUser.setAvatar("new_avatar");
        updatedUser.setRole("ADMIN");
        updatedUser.setUsername("new_username");
        // WHEN a PUT request is sent to update the user
        this.restTemplate.put(this.baseUrl + "/superapp/users/{superapp}/{userEmail}", updatedUser, superApp, email );
    }

}
