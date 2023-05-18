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
import superapp.Boundary.ObjectBoundary;
import superapp.Boundary.User.UserId;
import superapp.controller.SuperAppObjectsAPIController;
import java.util.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectTests {

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
        ObjectBoundary objectBoundary = new ObjectBoundary("superapp", "123");
        objectBoundary.setType("exampleType");
        objectBoundary.setAlias("exampleAlias");
        objectBoundary.setActive(true);
        Date now = new Date();
        objectBoundary.setCreationTimestamp(now);
        objectBoundary.setLocation(new Location(1.0, 2.0));
        objectBoundary.setCreatedBy(new CreatedBy((new UserId("superapp", "example545@example.com"))));
        objectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));

        // WHEN a POST request is sent to create the object
        ObjectBoundary response = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/objects",
                objectBoundary,
                ObjectBoundary.class
        );

        // THEN verify the response is not null and has the same fields as the input
        assertNotNull(response);
        assertEquals(objectBoundary.getType(), response.getType());
        assertEquals(objectBoundary.getAlias(), response.getAlias());
        assertEquals(objectBoundary.getActive(), response.getActive());
        assertEquals(now.getTime(), response.getCreationTimestamp().getTime(),1000);
        assertEquals(objectBoundary.getLocation().getLat(), response.getLocation().getLat(),0.01);
        assertEquals(objectBoundary.getLocation().getLng(), response.getLocation().getLng(),0.01);
        assertEquals(objectBoundary.getCreatedBy().getUserId().getEmail(), response.getCreatedBy().getUserId().getEmail());
        assertEquals(objectBoundary.getObjectDetails(), response.getObjectDetails());
    }


    @Test
    @DisplayName("Test Update Object")
    public void testUpdateObject() {
        // create an object and save it to the service
        ObjectBoundary objectBoundary = new ObjectBoundary("superapp", "1");
        objectBoundary.setType("type1");
        objectBoundary.setAlias("alias1");
        objectBoundary.setActive(true);
        objectBoundary.setCreationTimestamp(new Date());
        objectBoundary.setLocation(new Location(1.1, 2.2));
        objectBoundary.setCreatedBy(new CreatedBy(new UserId("superapp", "example545@example.com")));
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("key1", "value1");
        objectBoundary.setObjectDetails(objectDetails);
        ObjectBoundary createdObject = this.restTemplate.postForObject(
                "http://localhost:" + this.port + "/superapp/objects",
                objectBoundary,
                ObjectBoundary.class);

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
        ObjectBoundary updatedObject = this.restTemplate.getForObject(
                this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}",
                ObjectBoundary.class,
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
        ObjectBoundary objectBoundary = new ObjectBoundary("superapp", "123");
        objectBoundary.setType("exampleType");
        objectBoundary.setAlias("exampleAlias");
        objectBoundary.setActive(true);
        objectBoundary.setCreationTimestamp(new Date());
        objectBoundary.setLocation(new Location(1.1, 2.2));
        objectBoundary.setCreatedBy(new CreatedBy(new UserId("superapp", "example545@example.com")));
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("exampleKey", "exampleValue");
        objectBoundary.setObjectDetails(objectDetails);
        ObjectBoundary createdObject = this.restTemplate.postForObject(
                "http://localhost:" + this.port + "/superapp/objects", objectBoundary, ObjectBoundary.class);

        // retrieve the created object
        ObjectBoundary retrievedObject = this.restTemplate.getForObject(
                this.baseUrl + "/superapp/objects/{superapp}/{internalObjectId}",
                ObjectBoundary.class, createdObject.getObjectId().getSuperapp(), createdObject.getObjectId().getInternalObjectId());
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
