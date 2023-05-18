package superapp;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.CreatedBy;
import superapp.Boundary.Location;
import superapp.Boundary.superAppObjectBoundary;
import superapp.Boundary.User.UserId;
import superapp.controller.SuperAppObjectsAPIController;
import java.util.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class superAppObjectTests {

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
    @DisplayName("Test create Object")
    public void testCreateObject() {
        // GIVEN an object boundary
        superAppObjectBoundary superAppObjectBoundary = new superAppObjectBoundary("superapp", "123");
        superAppObjectBoundary.setType("exampleType");
        superAppObjectBoundary.setAlias("exampleAlias");
        superAppObjectBoundary.setActive(true);
        Date now = new Date();
        superAppObjectBoundary.setCreationTimestamp(now);
        superAppObjectBoundary.setLocation(new Location(1.0, 2.0));
        superAppObjectBoundary.setCreatedBy(new CreatedBy((new UserId("superapp", "example545@example.com"))));
        superAppObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));

        // WHEN a POST request is sent to create the object
        superAppObjectBoundary response = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/objects",
                superAppObjectBoundary,
                superAppObjectBoundary.class
        );

        // THEN verify the response is not null and has the same fields as the input
        assertNotNull(response);
        assertEquals(superAppObjectBoundary.getType(), response.getType());
        assertEquals(superAppObjectBoundary.getAlias(), response.getAlias());
        assertEquals(superAppObjectBoundary.getActive(), response.getActive());
        assertEquals(now.getTime(), response.getCreationTimestamp().getTime(),1000);
        assertEquals(superAppObjectBoundary.getLocation().getLat(), response.getLocation().getLat(),0.01);
        assertEquals(superAppObjectBoundary.getLocation().getLng(), response.getLocation().getLng(),0.01);
        assertEquals(superAppObjectBoundary.getCreatedBy().getUserId().getEmail(), response.getCreatedBy().getUserId().getEmail());
        assertEquals(superAppObjectBoundary.getObjectDetails(), response.getObjectDetails());
    }


    @Test
    @DisplayName("Test Update Object")
    public void testUpdateObject() {
        // create an object and save it to the service
        superAppObjectBoundary superAppObjectBoundary = new superAppObjectBoundary("superapp", "1");
        superAppObjectBoundary.setType("type1");
        superAppObjectBoundary.setAlias("alias1");
        superAppObjectBoundary.setActive(true);
        superAppObjectBoundary.setCreationTimestamp(new Date());
        superAppObjectBoundary.setLocation(new Location(1.1, 2.2));
        superAppObjectBoundary.setCreatedBy(new CreatedBy(new UserId("superapp", "example545@example.com")));
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("key1", "value1");
        superAppObjectBoundary.setObjectDetails(objectDetails);
        superAppObjectBoundary createdObject = this.restTemplate.postForObject(
                "http://localhost:" + this.port + "/superapp/objects",
                superAppObjectBoundary,
                superAppObjectBoundary.class);

        // update the created object
        createdObject.setAlias("alias2");
        createdObject.setActive(false);
        createdObject.setObjectDetails(Collections.singletonMap("key2", "value2"));
        this.restTemplate.put(
                this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}",
                createdObject,
                createdObject.getObjectId().getSuperapp(),
                createdObject.getObjectId().getInternalObjectId());

        // verify that the object was updated
        superAppObjectBoundary updatedObject = this.restTemplate.getForObject(
                this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}",
                superAppObjectBoundary.class,
                createdObject.getObjectId().getSuperapp(),
                createdObject.getObjectId().getInternalObjectId());
        assertNotNull(updatedObject);
        assertEquals(createdObject.getAlias(), updatedObject.getAlias());
        assertEquals(createdObject.getActive(), updatedObject.getActive());
        assertEquals(createdObject.getObjectDetails(), updatedObject.getObjectDetails());
    }


    @Test
    @DisplayName("Test GET Object")
    public void testRetrieveObject() {
        // create an object and save it to the service
        superAppObjectBoundary superAppObjectBoundary = new superAppObjectBoundary("superapp", "123");
        superAppObjectBoundary.setType("exampleType");
        superAppObjectBoundary.setAlias("exampleAlias");
        superAppObjectBoundary.setActive(true);
        superAppObjectBoundary.setCreationTimestamp(new Date());
        superAppObjectBoundary.setLocation(new Location(1.1, 2.2));
        superAppObjectBoundary.setCreatedBy(new CreatedBy(new UserId("superapp", "example545@example.com")));
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("exampleKey", "exampleValue");
        superAppObjectBoundary.setObjectDetails(objectDetails);
        superAppObjectBoundary createdObject = this.restTemplate.postForObject(
                "http://localhost:" + this.port + "/superapp/objects", superAppObjectBoundary, superAppObjectBoundary.class);

        // retrieve the created object
        superAppObjectBoundary retrievedObject = this.restTemplate.getForObject(
                this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}",
                superAppObjectBoundary.class, createdObject.getObjectId().getSuperapp(), createdObject.getObjectId().getInternalObjectId());
        assertNotNull(retrievedObject);
        assertEquals(createdObject.getType(), retrievedObject.getType());
        assertEquals(createdObject.getAlias(), retrievedObject.getAlias());
        assertEquals(createdObject.getActive(), retrievedObject.getActive());
        assertEquals(createdObject.getCreationTimestamp(), retrievedObject.getCreationTimestamp());
        assertEquals(createdObject.getLocation(), retrievedObject.getLocation());
        assertEquals(createdObject.getCreatedBy().getUserId(), retrievedObject.getCreatedBy().getUserId());
        assertEquals(createdObject.getObjectDetails(), retrievedObject.getObjectDetails());
    }

}
