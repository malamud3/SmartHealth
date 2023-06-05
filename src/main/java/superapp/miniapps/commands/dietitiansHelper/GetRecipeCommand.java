package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.RecipeResponse;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.utilitys.RecipeApiClient;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("GET_RECIPE")
public class GetRecipeCommand implements Command {
    //private final  SpoonaculerService spoonaculerService;
    private SuperAppObjectUtility superAppObjectUtility;

    private SuperAppObjectCrud objectRepository;

    @Autowired
    public GetRecipeCommand(SuperAppObjectCrud objectRepository) {
        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
    }

    @Override
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();

        List<RecipeResponse> recipes = RecipeApiClient.fetchRecipesWithParams(1);
        Map<String, Object> map = new HashMap<>();
        map.put("recipes", recipes.toArray());
        miniAppCommandBoundary.setCommandAttributes(map);
        //entity to boundary
        return recipes;
    }
}
