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

@Component("createRecipe")
public class CreateRecipeCommand implements Command {
    //private final  SpoonaculerService spoonaculerService;
    private ObjectServicePaginationSupported servicePaginationSupported;
    private ObjectsServiceWithAdminPermission objectsServiceWithAdminPermission;
    private UserUtility userUtility;
    private SuperAppObjectUtility superAppObjectUtility;
    private SuperAppObjectCrud objectRepository;
    private UserCrud userCrud;

    @Autowired
    public CreateRecipeCommand(SuperAppObjectCrud objectRepository,UserCrud userCrud) {

        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.userCrud = userCrud;
        this.servicePaginationSupported = new ObjectServiceRepo(objectRepository,userCrud);
        this.objectsServiceWithAdminPermission = new ObjectServiceRepo(objectRepository,userCrud);
        this.userUtility = new UserUtility(userCrud);
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
    }



    public CreateRecipeCommand(UserCrud userCrud) {

    }

    @Override
    public void execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
        String ingredient = "new";
        dietitian.insertToObjectDetails(ingredient);
        objectRepository.save(dietitian);
    }
}
