package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Mongo.ObjectServiceRepo;
import superapp.logic.service.SuperAppObjService.ObjectServicePaginationSupported;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.logic.utilitys.UserUtility;
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
        // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
        dietitian.insertToObjectDetails(miniAppCommandBoundary.getCommandAttributes().get("recipeName").toString(),
        		miniAppCommandBoundary.getCommandAttributes().get("recipeDetails"));
        objectRepository.save(dietitian);
        
        //entity to boundary
        return dietitian;
    }
}
