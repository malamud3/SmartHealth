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

@Component("MODIFY_RECIPE")
public class ModifyRecipeCommand  implements Command  {

	private SuperAppObjectUtility superAppObjectUtility;
	private SuperAppObjectCrud objectRepository;
	private SpoonacularService spoonacularService;

	@Autowired
	public ModifyRecipeCommand(SuperAppObjectCrud objectRepository, SpoonacularService spoonacularService) {
		this.objectRepository = objectRepository;
		this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
		this.spoonacularService = spoonacularService;
	}




	@Override
	public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
		// 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
        
        String recipeId = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeId");
        double amount = (double) miniAppCommandBoundary.getCommandAttributes().get("amount");
        String ingredientName = (String) miniAppCommandBoundary.getCommandAttributes().get("ingredientName");
        
        IngredientEntity ingredient = spoonacularService.getIngredientDataByNameAndAmount(ingredientName, amount);
        
        RecipeResponse updatedRecipe = dietitian.addIngredientToRecipe(recipeId, ingredient);
        objectRepository.save(dietitian);
        
        //entity to boundary
        return updatedRecipe;
	}

}
