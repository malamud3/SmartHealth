package demo;


import demo.Controller.AdminRelatedAPIController;
import demo.Controller.MiniAppCommandApiController;
import demo.Controller.SuperAppObjectsAPIController;
import demo.Controller.UsersRelatedAPIController;
import demo.Model.MiniAppCommandBoundary;
import demo.Model.ObjectBoundary;
import demo.Model.UserBoundary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApplicationTests {


    @Autowired
    private SuperAppObjectsAPIController superAppObjectsAPIController;
    @Autowired
    private UsersRelatedAPIController usersRelatedAPIController;


    @Test
    public void TestRetrieveObject() throws Exception{
        String superapp = "2023b.Gil.Azani";
        String internalObjectId = "INTERNAL_ID";
        ObjectBoundary expected = new ObjectBoundary(superapp, internalObjectId);
        ObjectBoundary actual = superAppObjectsAPIController.retrieveObject(superapp, internalObjectId);
        assertEquals(expected, actual);
    }
    @Test
    public void TestGetAllObjects() throws Exception{
        int expected = 0;
        int actual = superAppObjectsAPIController.getAllObjects().size();
        assertEquals(expected, actual);
    }
    @Test
    public void TestValidUser() throws Exception {
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary(superApp, email);
        UserBoundary actual = usersRelatedAPIController.validuser(superApp, email);
        assertEquals(expected, actual);
    }

    @Test
    public void TestRetrieveUser() throws Exception{
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary(superApp, email);
        UserBoundary actual = usersRelatedAPIController.retrieveUser(superApp, email);

        assertEquals(expected, actual);
    }

    @Test
    public void TestGetAllUsers() throws Exception{
        int expected = 0;
        int actual = AdminRelatedAPIController.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    public void TestCreateUser(){
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary("superapp", email);
        UserBoundary actual = usersRelatedAPIController.createUser(expected);
        assertEquals(expected, actual);
    }


    @Test
    public void TestDeleteAllUsers() {
        UsersRelatedAPIController controller = new UsersRelatedAPIController();
        AdminRelatedAPIController controller1 = new AdminRelatedAPIController();
        UserBoundary user1 = new UserBoundary("superapp", "user1@example.com");
        UserBoundary user2 = new UserBoundary("superapp", "user2@example.com");
        controller. createUser(user1);
        controller.createUser(user2);

        controller1.deleteAllUsers();
        List<UserBoundary> actualUsers = controller1.getAllUsers();

        assertEquals(0, actualUsers.size());
    }

    @Test
    public void TestDeleteAllObjects() {
        // Arrange
        AdminRelatedAPIController controller = new AdminRelatedAPIController();
        SuperAppObjectsAPIController controller1 = new SuperAppObjectsAPIController();
        ObjectBoundary obj1 = new ObjectBoundary("superapp", "object1");
        ObjectBoundary obj2 = new ObjectBoundary("superapp", "object2");
        controller1.createObject(obj1);
        controller1.createObject(obj2);
        List<ObjectBoundary> actualObjects = controller1.getAllObjects();
        controller.deleteAllObjects();
        assertEquals(0, actualObjects.size());
    }
}
