package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.logic.Mongo.ObjectServiceRepo;
import superapp.logic.service.SpoonaculerService;
import superapp.miniapps.commands.Command;

@Component("createRecipe")
public class CreateRecipeCommand implements Command {
    //private final  SpoonaculerService spoonaculerService;
    private ObjectServiceRepo objectServiceRepo;

    @Autowired
    public CreateRecipeCommand(ObjectServiceRepo objectServiceRepo, SpoonaculerService spoonaculerService) {
      //  this.spoonaculerService = spoonaculerService;
        this.objectServiceRepo = objectServiceRepo;
    }

    public CreateRecipeCommand() {
    }
//    public SuperAppObjectBoundary createRecipe(MiniAppCommandBoundary commandBoundary) {
//        // 1. find the dietitian object
//        // 2. add new recipe to the dietitian object
//        String superapp = commandBoundary.getCommandId().getSuperapp();
//        String internalCommandId = commandBoundary.getCommandId().getInternalCommandId();
//        String email = commandBoundary.getInvokedBy().getUserId().getEmail();
//
//        SuperAppObjectBoundary dietitian = objectRelationshipService.getSpecificObject(superapp, internalCommandId,superapp,email);
//
//        IngredientEntity ingredient = spoonaculerService.getIngredientDataByName("pasta",20);
//
//        dietitian.insertToObjectDetails(ingredient);
//
//        objectRelationshipService.updateObject(superapp,dietitian.getObjectId().getInternalObjectId(),dietitian,superapp,email);
//
//        return dietitian;
//    }

    @Override
    public void execute(MiniAppCommandBoundary miniAppCommandBoundary) {
         // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        String superapp = miniAppCommandBoundary.getCommandId().getSuperapp();
        String email = miniAppCommandBoundary.getInvokedBy().getUserId().getEmail();
        String id = miniAppCommandBoundary.getCommandId().getInternalCommandId();

        SuperAppObjectBoundary dietitian = (SuperAppObjectBoundary)miniAppCommandBoundary.getCommandAttributes().get("userObj");
        //IngredientEntity ingredient = spoonaculerService.getIngredientDataByName("pasta",20);
        String ingredient = "asdfas";
        dietitian.insertToObjectDetails(ingredient);

        objectServiceRepo.updateObject(superapp,dietitian.getObjectId().getInternalObjectId(),dietitian,superapp,email);
    }
}
