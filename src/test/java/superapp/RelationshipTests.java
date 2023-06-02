package superapp;

import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import superapp.Boundary.CreatedBy;
import superapp.Boundary.Location;
import superapp.Boundary.ObjectId;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.UserId;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Mongo.ObjectServiceRepo;

import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RelationshipTests {

    private RestTemplate restTemplate;
    private ObjectServiceRepo objectServiceRepo;
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
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port+"/superapp";
    }

//    @AfterEach
////	@BeforeEach
//    public void tearDown () {
//        this.restTemplate
//                .delete(this.baseUrl);
//    }
//
//    @Test
//    @DisplayName("test relationship many to many")
//    public void testRelationshipSettingBetweenMessages() throws Exception{
//        // GIVEN the database already contains 2 messages
//
//        SuperAppObjectBoundary boundaryParent = new SuperAppObjectBoundary("superapp", "123");
//        boundaryParent.setType("exampleType");
//        boundaryParent.setAlias("exampleAlias");
//        boundaryParent.setActive(true);
//        Date now = new Date();
//        boundaryParent.setCreationTimestamp(now);
//        boundaryParent.setLocation(new Location(1.0, 2.0));
//        boundaryParent.setCreatedBy(new CreatedBy((new UserId("2023b.gil.azani", "gil2@gmail.com"))));
//        boundaryParent.setObjectDetails(Map.of("exampleKey", "exampleValue"));
//
//        // WHEN a POST request is sent to create the object
//        SuperAppObjectBoundary parent = this.restTemplate.postForObject(
//                this.baseUrl+"/objects",
//                boundaryParent,
//                SuperAppObjectBoundary.class
//        );
//
//        SuperAppObjectBoundary boundaryChild = new SuperAppObjectBoundary("superapp", "1234");
//        boundaryChild.setType("exampleType");
//        boundaryChild.setAlias("exampleAlias");
//        boundaryChild.setActive(true);
//        now = new Date();
//        boundaryChild.setCreationTimestamp(now);
//        boundaryChild.setLocation(new Location(1.0, 2.0));
//        boundaryChild.setCreatedBy(new CreatedBy((new UserId("2023b.gil.azani", "gil2@gmail.com"))));
//        boundaryChild.setObjectDetails(Map.of("exampleKey", "exampleValue"));
//
//        // WHEN a POST request is sent to create the object
//        superapp.Boundary.SuperAppObjectBoundary child = this.restTemplate.postForObject(
//                this.baseUrl +"/objects" ,
//                boundaryChild,
//                superapp.Boundary.SuperAppObjectBoundary.class
//        );
//
//
//        // WHEN I PUT {superapp}/{internalObjectId}/children
//        assert child != null;
//        assert parent != null;
//        this.restTemplate
//                .put(this.baseUrl + "/objects/{superapp}/{internalObjectId}/children",
//                      boundaryChild.getObjectId(),springAppName, parent.getObjectId().getInternalObjectId());
//
//        // THEN a relationship will be created between objects
//        SuperAppObjectBoundary[] children =
//                this.restTemplate
//                        .getForObject(this.baseUrl + "/objects/{superapp}/{internalObjectId}/children", SuperAppObjectBoundary[].class,
//                                parent.getObjectId().getInternalObjectId());
//
//        Assertions.assertThat(children)
//                .isNotNull()
//                .isNotEmpty()
//                .usingRecursiveFieldByFieldElementComparator()
//                .containsExactly(child);
//    }


}
