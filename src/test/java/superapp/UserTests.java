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
        newUserBoundary.setEmail("example545@example.com");
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
        // AND the database should have only one user with this email
        assertThat(this.restTemplate
                .getForObject(this.baseUrl + "/superapp/users/login/"+springAppName+"/"+userBoundary.getUserId().getEmail(), UserBoundary[].class))
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorOnFields("email")
                .containsExactly(userBoundary);
    }



}
