package superapp.controller;

import java.util.HashMap;
import java.util.Map;

import superapp.logic.mockup.MiniAppCommandServiceMockUp;
import superapp.model.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandService;
import superapp.logic.ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController

public class MiniAppCommandApiController
{


    private MiniAppCommandService miniAppCommandService;

    @Autowired
    public MiniAppCommandApiController(MiniAppCommandService miniAppCommandService) {
        this.miniAppCommandService = miniAppCommandService;

    }


    // POST: Invoke MiniApp command
    @RequestMapping(
            path = {"/superapp/miniapp/{miniappname}"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object invokeMiniApp(@PathVariable("miniappname") String miniAppName, @RequestBody MiniAppCommandBoundary miniAppCommand) {
        miniAppCommand.getCommandId().setMiniApp(miniAppName);
        return miniAppCommandService.InvokeCommand(miniAppCommand);
    }

}
