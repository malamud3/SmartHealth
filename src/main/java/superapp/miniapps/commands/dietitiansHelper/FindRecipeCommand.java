package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.service.SpoonaculerService;
import superapp.logic.utilitys.RecipeApiClient;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("FIND_RECIPE")
public class FindRecipeCommand implements Command {
    private SpoonaculerService spoonaculerService;
    private SuperAppObjectUtility superAppObjectUtility;

    private SuperAppObjectCrud objectRepository;

    @Autowired
    public FindRecipeCommand(SuperAppObjectCrud objectRepository, SpoonaculerService spoonaculerService) {
        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
        this.spoonaculerService = spoonaculerService;
    }

    @Override
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        SuperAppObjectEntity dietitianObject =  superAppObjectUtility
        		.checkSuperAppObjectEntityExist(miniAppCommandBoundary.getTargetObject().getObjectId());
        
        

        RecipeResponse recipe = spoonaculerService
        		.getRecipeByName((String)miniAppCommandBoundary.getCommandAttributes().get("recipeName"));
        
        //List<RecipeResponse> recipe = RecipeApiClient.fetchRecipesWithParams(1);
        
        dietitianObject.insertNewRecipeToObjectDetails(recipe);
        objectRepository.save(dietitianObject);
        
//        Map<String, Object> map = new HashMap<>();
//        map.put("recipes", recipes.toArray());
//        miniAppCommandBoundary.setCommandAttributes(map);
        //entity to boundary
        return recipe;
    }
}
