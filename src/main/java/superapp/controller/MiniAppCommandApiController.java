package superapp.controller;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.MiniAppCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController

public class MiniAppCommandApiController
{

    private final MiniAppCommandService miniAppCommandService;

    @Autowired
    public MiniAppCommandApiController(MiniAppCommandService miniAppCommandService) {
        this.miniAppCommandService = miniAppCommandService;

    }

//    //POST: Invoke MiniApp Command
//    @RequestMapping(
//            path = {"/superapp/miniapp/{miniAppName}"},
//            method = {RequestMethod.POST},
//            consumes = {MediaType.APPLICATION_JSON_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE})
//
//    public MiniAppCommandBoundary invokeMiniApp(@PathVariable("miniAppName") String miniAppName, @RequestBody MiniAppCommandBoundary miniAppCommand) {
//        miniAppCommand.getCommandId().setMiniapp(miniAppName);
//        return miniAppCommandService.invokeCommand(miniAppCommand);
//    }

}
