package demo.Controller;

import java.util.HashMap;
import java.util.Map;

import demo.Model.MiniAppCommandBoundary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public class MiniAppCommandApiController
{

    // POST: Invoke MiniApp command
    @RequestMapping(
            path = {"/superapp/miniapp/{miniappname}"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object invokeMiniApp(@PathVariable("miniappname") String miniAppName, @RequestBody MiniAppCommandBoundary miniAppCommand) {
        // Here you can implement the logic to invoke the specified MiniApp with the provided command
        // For example, you could use a switch statement to select the correct MiniApp implementation based on the given name

        // For the sake of this example, let's assume that we have a hardcoded implementation for a MiniApp called "sample-miniapp"
        if (miniAppName.equals("sample-miniapp")) {
            // Execute the command and return the output
            Map<String, Object> output = new HashMap<>();
            output.put("result", "success");
            output.put("message", "Sample MiniApp command invoked successfully.");
            output.put("command", miniAppCommand);
            return output;
        }

        // If the MiniApp name is not recognized, return an error message
        Map<String, Object> errorOutput = new HashMap<>();
        errorOutput.put("result", "error");
        errorOutput.put("message", "MiniApp not found.");
        return errorOutput;
    }

}
