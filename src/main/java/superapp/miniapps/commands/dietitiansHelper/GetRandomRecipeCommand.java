package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.SpoonacularService;
import superapp.miniapps.commands.Command;

@Component("GET_RANDOM_RECIPE")
public class GetRandomRecipeCommand implements Command {
	
	private SpoonacularService spoonacularService;
	
	@Autowired
	public GetRandomRecipeCommand(SpoonacularService spoonacularService) {
		this.spoonacularService = spoonacularService;
	}

	
	
	@Override
	public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
		
		Integer number = (Integer) miniAppCommandBoundary.getCommandAttributes().get("number");
		if(number == null) {
			number = 1;
		}
		
		return this.spoonacularService.getRandomRecipe(number);
	}

}
