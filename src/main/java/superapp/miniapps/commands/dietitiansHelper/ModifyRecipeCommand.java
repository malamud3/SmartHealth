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
        String updatedRecipeName = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeName");
        String updatedRecipeImage = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeImage");
        String updatedRecipeTitle = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeTitle");
        
        if (recipeId == null) {
            throw new IllegalArgumentException("you need to enter recipeId");
        }
                
        RecipeResponse updatedRecipe = dietitian.modifyRecipe(recipeId, updatedRecipeName, updatedRecipeImage, updatedRecipeTitle);
        objectRepository.save(dietitian);
        
        //entity to boundary
        return updatedRecipe;
	}

}
