package SuperApp;


import SuperApp.Controller.AdminRelatedAPIController;
import SuperApp.Controller.MiniAppCommandApiController;
import SuperApp.Controller.SuperAppObjectsAPIController;
import SuperApp.Controller.UsersRelatedAPIController;
import SuperApp.Model.MiniAppCommandBoundary;
import SuperApp.Model.ObjectBoundary;
import SuperApp.Model.UserBoundary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApplicationTests {


    @Autowired
    private SuperAppObjectsAPIController superAppObjectsAPIController;
    @Autowired
    private UsersRelatedAPIController usersRelatedAPIController;

    @Autowired
    private MiniAppCommandApiController miniAppCommandApiController;

    @Autowired
    private AdminRelatedAPIController adminRelatedAPIController;

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
        int actual = adminRelatedAPIController.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    public void TestCreateUser(){
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary(superApp, email);
        UserBoundary actual = usersRelatedAPIController.createUser(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void TestExportAllMiniAppsHistory() {

        int expected = 5;
        int actual = adminRelatedAPIController.ExportAllMiniAppsHistory().length;
        assertEquals(expected, actual);
    }
    @Test
    public void TestGetSpecificMiniAppHistory(){
        String minApp = "sample-miniapp";
        int expected = 2;
        int actual = adminRelatedAPIController.getSpecificMiniAppHistory(minApp).length;
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
    @Test
    public void TestUpdateObject() throws Exception{
        String superApp = "2023b.Gil.Azani";
        String internalObjectId = "object1";
        String expected = "object2";
        ObjectBoundary obj1 = new ObjectBoundary(superApp, internalObjectId);
        ObjectBoundary actual = superAppObjectsAPIController.updateObject(superApp, expected, obj1);
        assertEquals(expected, actual.getObjectId().getInternalObjectId());
    }
    @Test
    public void TestInvokeMiniApp() throws Exception{
        String miniAppName = "sample-miniapp";
        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary();
        Map<String, Object> actual = miniAppCommandApiController.invokeMiniApp(miniAppName, miniAppCommand);
        assertEquals("success", actual.get("result"));
    }


}
