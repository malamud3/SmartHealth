package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.IngredientEntity;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.service.SpoonacularService;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("ADD_INGREDIENT")
public class AddIngredientToRecipeCommand implements Command {

	private SuperAppObjectUtility superAppObjectUtility;
	private SuperAppObjectCrud objectRepository;
	private SpoonacularService spoonacularService;

	@Autowired
	public AddIngredientToRecipeCommand(SuperAppObjectCrud objectRepository, SpoonacularService spoonacularService) {
		this.objectRepository = objectRepository;
		this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
		this.spoonacularService = spoonacularService;
	}

	@Override
	public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
		// 1. Find the dietitian object using the provided object ID
		ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
		SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);

		// 2. Extract the required attributes from the command boundary
		String recipeId = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeId");
		double amount = (double) miniAppCommandBoundary.getCommandAttributes().get("amount");
		String ingredientName = (String) miniAppCommandBoundary.getCommandAttributes().get("ingredientName");

		// 3. Retrieve ingredient data from the Spoonacular service
		IngredientEntity ingredient = spoonacularService.getIngredientDataByNameAndAmount(ingredientName, amount);

		// 4. Add the ingredient to the recipe of the dietitian
		RecipeResponse updatedRecipe = dietitian.addIngredientToRecipe(recipeId, ingredient);

		// 5. Save the updated dietitian object
		objectRepository.save(dietitian);

		// 6. Convert the updated recipe response to a boundary object and return it
		return updatedRecipe;
	}
}

