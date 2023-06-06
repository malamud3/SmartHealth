package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.service.SpoonacularService;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("REMOVE_INGREDIENT")
public class RemoveIngredientFromRecipeCommand implements Command {
	private SuperAppObjectUtility superAppObjectUtility;
	private SuperAppObjectCrud objectRepository;
	private SpoonacularService spoonacularService;

	@Autowired
	public RemoveIngredientFromRecipeCommand(SuperAppObjectCrud objectRepository, SpoonacularService spoonacularService) {
		this.objectRepository = objectRepository;
		this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
		this.spoonacularService = spoonacularService;
	}

	@Override
	public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
		// 1. Find the dietitian object using the provided object ID
		ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
		SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);

		// 2. Retrieve the necessary attributes from the command attributes
		String recipeId = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeId");
		int ingredientId = (int) miniAppCommandBoundary.getCommandAttributes().get("ingredientId");

		// 3. Remove the ingredient from the recipe in the dietitian's object details
		RecipeResponse updatedRecipe = dietitian.removeIngredientFromRecipe(recipeId, ingredientId);

		// 4. Save the updated dietitian object
		objectRepository.save(dietitian);

		// Return the updated recipe response
		return updatedRecipe;
	}
}
