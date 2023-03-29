package SuperApp.Controller;

import SuperApp.Model.MiniAppCommandBoundary;
import SuperApp.Model.UserBoundary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController


public class AdminRelatedAPIController
{
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})
    public void deleteAllUsers() {
        // do nothing
    }

    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})
    public void deleteAllObjects() {
        // do nothing
    }


    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.DELETE})
    public void deleteAllCommands() {
        // do nothing
    }


    // GET: ALL USERS
    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserBoundary> getAllUsers() {
        return new ArrayList<UserBoundary>();
    }




    // GET: ALL MINI-APPS COMMANDS HISTORY

    @RequestMapping(
            path ={"/superapp/admin/miniapp"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MiniAppCommandBoundary[] ExportAllMiniAppsHistory()
    {
        return new MiniAppCommandBoundary[5];
    }

    // GET: Specific MINI-APPS COMMANDS HISTORY

    @RequestMapping(
            path ={"/superapp/admin/miniapp/{miniAppName}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE}
            ,consumes = {MediaType.APPLICATION_JSON_VALUE})

    public MiniAppCommandBoundary[] getSpecificMiniAppHistory(@PathVariable("miniAppName") String miniapp  )
    {
        return new MiniAppCommandBoundary[2];
    }
}
