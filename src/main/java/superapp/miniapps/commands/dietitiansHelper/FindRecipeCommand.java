package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.service.SpoonacularService;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("FIND_RECIPE")
public class FindRecipeCommand implements Command {
    private SpoonacularService spoonacularService;
    private SuperAppObjectUtility superAppObjectUtility;
    private SuperAppObjectCrud objectRepository;

    @Autowired
    public FindRecipeCommand(SuperAppObjectCrud objectRepository, SpoonacularService spoonaculerService) {
        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
        this.spoonacularService = spoonaculerService;
    }

    @Override
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. Find the dietitian object using the provided object ID
        SuperAppObjectEntity dietitianObject = superAppObjectUtility.checkSuperAppObjectEntityExist(miniAppCommandBoundary.getTargetObject().getObjectId());

        // 2. Initialize an empty RecipeResponse object
        RecipeResponse recipe = new RecipeResponse();

        // 3. Check if the "diet" attribute is provided in the command attributes
        if (miniAppCommandBoundary.getCommandAttributes().get("diet") == null) {
            // If no "diet" attribute is provided, retrieve the recipe by name only
            recipe = spoonacularService.getRecipeByName((String) miniAppCommandBoundary.getCommandAttributes().get("recipeName"));
        } else {
            // If the "diet" attribute is provided, retrieve the recipe by name and diet
            recipe = spoonacularService.getRecipeByNameAndDiet((String) miniAppCommandBoundary.getCommandAttributes().get("recipeName"),
                    (String) miniAppCommandBoundary.getCommandAttributes().get("diet"));
        }

        // 4. Insert the new recipe into the dietitian's object details
        dietitianObject.insertNewRecipeToObjectDetails(recipe);

        // 5. Save the updated dietitian object
        objectRepository.save(dietitianObject);

        // Return the recipe response
        return recipe;
    }
}
