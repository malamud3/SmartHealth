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
        this.baseUrl = "http://localhost:" + this.port + "/message";
    }

    @AfterEach
//	@BeforeEach
    public void tearDown () {
        this.restTemplate
                .delete(this.baseUrl);
    }

    @Test
    @DisplayName("test create user")
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void testCreateUser(String springApplicationName){
        // GIVEN the database is up
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setAvatar("example_avatar");
        newUserBoundary.setRole("MINIAPP_USER");
        newUserBoundary.setEmail("example@example.com");
        newUserBoundary.setUsername("example_userName");
        newUserBoundary.setEmail(springApplicationName);
        UserBoundary userBoundary = this.restTemplate.postForObject(this.baseUrl,
                usersRelatedAPIController.createUser(newUserBoundary), UserBoundary.class);

        assert userBoundary != null;

    }


}
