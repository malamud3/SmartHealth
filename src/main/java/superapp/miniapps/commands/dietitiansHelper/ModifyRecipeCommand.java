package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.IngredientEntity;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Exceptions.CommandBadRequest;
import superapp.logic.service.SpoonacularService;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("MODIFY_RECIPE")
public class ModifyRecipeCommand implements Command {
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
        // 1. Find the dietitian object using the provided object ID
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);

        // 2. Retrieve the necessary attributes from the command attributes
        String recipeId = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeId");
        String updatedRecipeName = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeName");
        String updatedRecipeImage = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeImage");
        String updatedRecipeTitle = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeTitle");

        // 3. Validate that the recipeId is provided
        if (recipeId == null) {
            throw new CommandBadRequest("You need to enter recipeId");
        }

        // 4. Modify the recipe in the dietitian's object details
        RecipeResponse updatedRecipe = dietitian.modifyRecipe(recipeId, updatedRecipeName, updatedRecipeImage, updatedRecipeTitle);

        // 5. Save the updated dietitian object
        objectRepository.save(dietitian);

        // Return the updated recipe response
        return updatedRecipe;
    }
}
