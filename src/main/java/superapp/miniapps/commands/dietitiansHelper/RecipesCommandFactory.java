package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.miniapps.commands.Command;
import superapp.miniapps.commands.dietitiansHelper.CommandsEnum;
import superapp.miniapps.commands.dietitiansHelper.RecipesCommandInterface;

@Service
public class RecipesCommandFactory implements RecipesCommandInterface {

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public Object createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary) {

    	Command command = null;

		try {
			command = this.applicationContext
					.getBean(commandsEnum.toString(), Command.class);
		}catch (Exception e) {
			//command = this.default;
			System.out.println("Unknown command");
		}

        assert command != null;
        return command
			.execute(commandBoundary);

    }
}
