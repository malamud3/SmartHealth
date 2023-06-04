package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;
import superapp.logic.Mongo.ObjectServiceRepo;
import superapp.logic.service.SpoonaculerService;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;
import superapp.logic.utilitys.UserUtility;

@Component("createRecipe")
public class createRecipeCommand {

    private SpoonaculerService spoonaculerService;
    private SuperAppObjectCrud superAppObjectCrud;
    private UserCrud userCrud;
    private SuperAppObjectRelationshipService objectRelationshipService;

    @Autowired
    public void setCreateRecipeCommand(SpoonaculerService spoonaculerService, SuperAppObjectCrud superAppObjectCrud, UserCrud userCrud, SuperAppObjectRelationshipService objectRelationshipService) {
        this.spoonaculerService = spoonaculerService;
        this.superAppObjectCrud = superAppObjectCrud;
        this.userCrud = userCrud;
        this.objectRelationshipService = objectRelationshipService;
    }

    public SuperAppObjectBoundary createRecipe(MiniAppCommandBoundary commandBoundary) {
        // 1. find the dietitian object
        // 2. add new recipe to the dietitian object
        SuperAppObjectBoundary superAppObjectBoundary = new SuperAppObjectBoundary();
        userCrud.findByUserId(commandBoundary.getInvokedBy().getUserId());
        objectRelationshipService.getSpecificObject(commandBoundary.getCommandId().getSuperapp(),
                commandBoundary.getCommandId().getInternalCommandId(),commandBoundary.getCommandId().getSuperapp(),);


    }

}
