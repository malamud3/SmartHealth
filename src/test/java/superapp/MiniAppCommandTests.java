package superapp;


import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import superapp.Boundary.*;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.Boundary.User.UserId;
import superapp.data.RecipeResponse;

import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MiniAppCommandTests {

	
    private String baseUrl;
    private RestTemplate restTemplate;
    private UserId adminUserId;
    private int port;
    private String miniAppName = "foodApp";


    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void setup() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port;
    }
    
    @BeforeEach
   	private void createAdminUser() {
   		NewUserBoundary newAdminBoundary = new NewUserBoundary();
   		newAdminBoundary.setAvatar("admin_avatar");
   		newAdminBoundary.setRole("ADMIN");
   		newAdminBoundary.setEmail("admin@example.com");
   		newAdminBoundary.setUsername("admin_userName");

   		adminUserId = this.restTemplate.postForObject(
   				this.baseUrl + "/superapp/users",
   				newAdminBoundary,
   				UserBoundary.class
   				).getUserId();

   	}


   	@AfterEach
   	public void tearDown() {	
   		//clean the commands collection
   		this.restTemplate
   		.delete(this.baseUrl + "/superapp/admin/miniapp?userSuperapp={userSuperapp}&userEmail={userEmail}",
   				adminUserId.getSuperapp(), adminUserId.getEmail()); 
   		
   	    //clean the commands collection
   		this.restTemplate
   		.delete(this.baseUrl + "/superapp/admin/objects?userSuperapp={userSuperapp}&userEmail={userEmail}",
   				adminUserId.getSuperapp(), adminUserId.getEmail());

   		//clean the usersObjects DB
   		this.restTemplate
   		.delete(this.baseUrl + "/superapp/admin/users?userSuperapp={userSuperapp}&userEmail={userEmail}",
   				adminUserId.getSuperapp(), adminUserId.getEmail());
   	}

    @Test
    @DisplayName("Test invoke mini app command")
    public void testInvokeMiniAppCommand() {

        // GIVEN there is a SUPERAPP_User (who created the super app object)
    	// AND there is a SuperappObjectBoundary set to active
    	//AND there is a MINIAPP_USER
    	//AND there are no commands history in the server
    	
    	UserBoundary superappUser = createExampleUser("SUPERAPP_USER");
    	UserBoundary miniappUser = createExampleUser("MINIAPP_USER");
    	SuperAppObjectBoundary targetObject = createExampleSuperappObject("targetType", "targetAlias", true, superappUser.getUserId());
    	
    	// WHEN I POST /superapp/miniapp/{miniAppName} to invoke a new miniappCommand
        MiniAppCommandBoundary command = new MiniAppCommandBoundary();
        command.setCommand("CREATE_RECIPE");
        command.setTargetObject(new TargetObject( targetObject.getObjectId()));
        command.setInvocationTimestamp(new Date());
        command.setInvokedBy(new InvokedBy(miniappUser.getUserId()));
        command.setCommandAttributes(Map.of("recipeName", "test"));
  

        Object response = this.restTemplate.postForObject(
                this.baseUrl + "/superapp/miniapp/{miniAppName}",
                command,
                Object.class,
                miniAppName
        );
        
        //THEN response should be tpe recipe with an attribute name = test

        // Replace "Recipe" with the actual class name of the Recipe object
        Class<?> expectedClass = RecipeResponse.class;
        ObjectMapper objectMapper = new ObjectMapper();
        RecipeResponse recipe = (RecipeResponse) objectMapper.convertValue(response, expectedClass);

        // Assert that expectedCommand is an instance of Recipe
        Assertions.assertTrue(expectedClass.isInstance(recipe));

        // Assert that the name of the recipe is equal to the recipeName in the command attributes
        String expectedRecipeName = "test";
        Assertions.assertEquals(expectedRecipeName, recipe.getRecipeName());
        
        
    }
    
	private SuperAppObjectBoundary createExampleSuperappObject(String type, String alias, boolean active, UserId creatorId) {
		SuperAppObjectBoundary newParentObjectBoundary = new SuperAppObjectBoundary();
		newParentObjectBoundary.setType(type);
		newParentObjectBoundary.setAlias(alias);
		newParentObjectBoundary.setActive(active);
		newParentObjectBoundary.setCreationTimestamp(new Date());
		newParentObjectBoundary.setLocation(new Location(1.0, 2.0));
		newParentObjectBoundary.setCreatedBy(new CreatedBy((creatorId)));
		newParentObjectBoundary.setObjectDetails(Map.of("exampleKey", "exampleValue"));


		return this.restTemplate.postForObject(
				this.baseUrl + "/superapp/objects",
				newParentObjectBoundary,
				SuperAppObjectBoundary.class
				);
		
		
	}

	private UserBoundary createExampleUser(String role) {
		NewUserBoundary newSuperappBoundary = new NewUserBoundary();
		newSuperappBoundary.setAvatar("example_avatar");
		newSuperappBoundary.setRole(role);
		newSuperappBoundary.setEmail(role + "@example.com");
		newSuperappBoundary.setUsername("superapp_userName");

		return this.restTemplate.postForObject(
				this.baseUrl + "/superapp/users",
				newSuperappBoundary,
				UserBoundary.class);
	}

}



