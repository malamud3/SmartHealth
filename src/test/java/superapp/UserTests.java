package superapp;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.controller.UsersRelatedAPIController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @Autowired
    private UsersRelatedAPIController usersRelatedAPIController;
    private RestTemplate restTemplate;
    private String baseUrl;
    private int port;


    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }

 //   @AfterEach
//	@BeforeEach
//    public void tearDown () {
//        this.restTemplate
//                .delete(this.baseUrl);
//    }
    @Test
    @DisplayName("test create user")
    public void testCreateUser() {
        // GIVEN the database is up
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("MINIAPP_USER");
        newUserBoundary.setEmail("example@example.com");
        newUserBoundary.setUsername("example_userName");

        // WHEN a POST request is sent to create a new user
        UserBoundary userBoundary = this.restTemplate.postForObject(this.baseUrl+"/superapp/users",
                usersRelatedAPIController.createUser(newUserBoundary), UserBoundary.class);

        // THEN the user should be created successfully
        assertThat(userBoundary).isNotNull();
        assertThat(userBoundary.getUserId()).isNotNull();
        assertThat(userBoundary.getAvatar()).isEqualTo(newUserBoundary.getAvatar());
        assertThat(userBoundary.getRole()).isEqualTo(newUserBoundary.getRole());
        assertThat(userBoundary.getUserId().getEmail()).isEqualTo(newUserBoundary.getEmail());
        assertThat(userBoundary.getUsername()).isEqualTo(newUserBoundary.getUsername());
    }



}
