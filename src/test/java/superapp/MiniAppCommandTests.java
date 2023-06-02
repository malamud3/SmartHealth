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
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;
import superapp.controller.MiniAppCommandApiController;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MiniAppCommandTests {

    @Autowired
    private MiniAppCommandApiController miniAppCommandApiController;
    private String baseUrl;
    private RestTemplate restTemplate;
    private int port;
    private String springAppName;

    private String miniAppName = "foodApp";


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

//    @Test
//    @DisplayName("Test invoke mini app command")
//    public void testInvokeMiniAppCommand() {
//
//        // GIVEN a mini app command
//        MiniAppCommandBoundary command = new MiniAppCommandBoundary();
//        command.setCommand("example-command");
//        command.setTargetObject(new TargetObject( new ObjectId("2023b.gil.azani", "bdde6835-67e9-4ef5-8d39-705f71c8e8c5")));
//        command.setInvocationTimestamp(Date.from(Instant.parse("2023-05-09T14:32:07.905Z")));
//        command.setInvokedBy(new InvokedBy(new UserId("2023b.gil.azani", "45555@example.com")));
//        command.setCommandAttributes(new HashMap<>());
//        CommandId commandId = new CommandId();
//        command.setCommandId(commandId);
//
//        // WHEN a POST request is sent to invoke the mini app command
//        MiniAppCommandBoundary response = this.restTemplate.postForObject(
//                this.baseUrl + "/superapp/miniapp/{miniAppName}",
//                command,
//                MiniAppCommandBoundary.class,
//                miniAppName
//        );
//
//        // THEN verify the response is not null and has a command ID set
//        assertNotNull(response);
//        assertNotNull(response.getCommandId());
//    }

}
