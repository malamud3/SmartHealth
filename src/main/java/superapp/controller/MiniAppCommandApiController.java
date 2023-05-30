package superapp.controller;

import superapp.Boundary.CommandId;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.MiniAppServices.MiniAppCommandServiceWithAsyncSupport;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController

public class MiniAppCommandApiController
{
	private String superappName;


	// this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.superappName = springApplicationName;
    }

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

    public MiniAppCommandBoundary invokeMiniApp(
    		@PathVariable("miniAppName") String miniAppName,
             @RequestParam(value = "async", defaultValue = "false") boolean asyncFlag,
             @RequestBody MiniAppCommandBoundary miniAppCommand) throws RuntimeException {
    	CommandId commandId = new CommandId();
    	commandId.setMiniapp(miniAppName);
    	commandId.setSuperapp(superappName);
    	commandId.setInternalCommandId(UUID.randomUUID().toString());
        miniAppCommand.setCommandId(commandId);
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
