package demo;


import demo.Controller.AdminRelatedAPIController;
import demo.Controller.SuperAppObjectsAPIController;
import demo.Controller.UsersRelatedAPIController;
import demo.Model.ObjectBoundary;
import demo.Model.UserBoundary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int actual = superAppObjectsAPIController.getAllObjects().length;
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

}
