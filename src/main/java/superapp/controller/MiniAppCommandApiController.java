package superapp.controller;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.MiniAppServices.MiniAppCommandServiceWithAsyncSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController

public class MiniAppCommandApiController
{

    private final MiniAppCommandServiceWithAsyncSupport miniAppCommandService;


    @Autowired
    public MiniAppCommandApiController(MiniAppCommandServiceWithAsyncSupport miniAppCommandService) {
        this.miniAppCommandService = miniAppCommandService;

    }

    //POST: Invoke MiniApp Command
    @RequestMapping(
            path = {"/superapp/miniapp/{miniAppName}"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public MiniAppCommandBoundary invokeMiniApp(@PathVariable("miniAppName") String miniAppName,
                                                @RequestParam(value = "async", defaultValue = "false") boolean asyncFlag
            ,@RequestBody MiniAppCommandBoundary miniAppCommand) throws RuntimeException {
        miniAppCommand.getCommandId().setMiniapp(miniAppName);
        try {

           if (!asyncFlag)
               return miniAppCommandService.invokeCommand(miniAppCommand);
           else
               return miniAppCommandService.asyncHandle(miniAppCommandService.invokeCommand(miniAppCommand));
       }catch (RuntimeException e){
           throw new RuntimeException("can't invoke mini app: "+e.getMessage());
       }
    }
}
