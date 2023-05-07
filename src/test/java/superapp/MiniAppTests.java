package superapp;


import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.Boundary.User.UserId;
import superapp.controller.SuperAppObjectsAPIController;

import java.util.Date;
import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MiniAppTests {

    @Autowired
    private SuperAppObjectsAPIController superAppObjectsAPIController;
    private String baseUrl;
    private RestTemplate restTemplate;
    private int port;
    private String springAppName;



    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springAppName = springApplicationName;
    }

    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }

    @Test
    @DisplayName("Test invoke mini app command")
    public void testInvokeMiniAppCommand() {

        String miniAppName="foodApp";
        // GIVEN a mini app command
           MiniAppCommandBoundary command = new MiniAppCommandBoundary();
//        command.setCommand("exampleCommand");
//        command.setTargetObject(new ObjectId());
//        command.setInvocationTimestamp(new Date());
//        command.setInvokedBy(new UserId("superApp", "example545@example.com"));
//        command.setCommandAttributes(new HashMap<>());



        // WHEN a POST request is sent to invoke the mini app command
        MiniAppCommandBoundary response = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/miniapp/{miniAppName}",
                command ,
                MiniAppCommandBoundary.class,
                miniAppName
        );

        // THEN verify the response is not null and has a command ID set
        assertNotNull(response);
        assertNotNull(response.getCommandId());
    }












}
