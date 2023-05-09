package superapp;


import superapp.Boundary.User.UserId;
import superapp.controller.AdminRelatedAPIController;
import superapp.controller.MiniAppCommandApiController;
import superapp.controller.SuperAppObjectsAPIController;
import superapp.controller.UsersRelatedAPIController;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.data.mainEntity.MiniAppCommandEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testGetAllObjects() throws Exception{
        int expected = 0;
        int actual = superAppObjectsAPIController.getAllObjects().size();
        assertEquals(expected, actual);
    }
    @Test
    public void testValidUser() throws Exception {
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary(new UserId(superApp,email));
        UserBoundary actual = usersRelatedAPIController.validUser(superApp, email);
        assertEquals(expected, actual);
    }

    @Test
    public void testRetrieveUser() throws Exception{
        String superApp = "2023b.Gil.Azani";
        String email = "kuku@gmail.com";
        UserBoundary expected = new UserBoundary(new UserId(superApp,email));
        UserBoundary actual = usersRelatedAPIController.validUser(superApp, email);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllUsers() throws Exception{
        int expected = 0;
        int actual = adminRelatedAPIController.getAllUsers().size();
        assertEquals(expected, actual);
    }

//    @Test
//    public void testCreateUser(){
//        String superApp = "2023b.Gil.Azani";
//        String email = "kuku@gmail.com";
//        NewUserBoundary expected = new NewUserBoundary();
//        expected.setEmail(email);
//        UserBoundary actual = usersRelatedAPIController.createUser(expected);
//        assertEquals(expected.newUserBoundaryToUserBoundary(superApp), actual);
//    }

    @Test
    public void testExportAllMiniAppsHistory() {

        int expected = 5;
        int actual = adminRelatedAPIController.exportAllMiniAppsHistory().size();
        assertEquals(expected, actual);
    }
    @Test
    public void testGetSpecificMiniAppHistory(){
        String minApp = "sample-miniapp";
        int expected = 2;
        int actual = adminRelatedAPIController.getSpecificMiniAppHistory(minApp).size();
        assertEquals(expected, actual);
    }


//    @Test
//    public void testInvokeMiniApp() throws Exception{
//        String miniAppName = "sample-miniapp";
//        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary();
//        Object actual = miniAppCommandApiController.invokeMiniApp(miniAppName, miniAppCommand);
//        assertEquals(miniAppName, ((MiniAppCommandEntity) actual).getCommandId().getMiniapp());
//    }

}
