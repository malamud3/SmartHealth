package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("CREATE_RECIPE")
public class CreateRecipeCommand implements Command {
    //private final  SpoonaculerService spoonaculerService;
    private SuperAppObjectUtility superAppObjectUtility;

    private SuperAppObjectCrud objectRepository;

    @Autowired
    public CreateRecipeCommand(SuperAppObjectCrud objectRepository) {
        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
    }

    @Override
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. Find the dietitian object using the provided object ID
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);

        // 2. Create a new recipe using the recipe name from the command attributes
        RecipeResponse newRecipe = dietitian.createRecipe(miniAppCommandBoundary.getCommandAttributes().get("recipeName").toString());

        // Save the updated dietitian object with the new recipe
        objectRepository.save(dietitian);

        // Convert the recipe response to a boundary object and return it
        return newRecipe;
    }
}
