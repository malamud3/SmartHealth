package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;
import superapp.logic.Mongo.ObjectServiceRepo;
import superapp.logic.service.SpoonaculerService;
import superapp.logic.utilitys.UserUtility;

@Component("createRecipe")
public class createRecipeCommand {

    private SpoonaculerService spoonaculerService;
    private SuperAppObjectCrud superAppObjectCrud;
    private UserCrud userCrud;
    private ObjectServiceRepo objectServiceRepo;


    @Autowired
    public void setCreateRecipeCommand(SpoonaculerService spoonaculerService, SuperAppObjectCrud superAppObjectCrud, UserCrud userCrud) {
        this.spoonaculerService = spoonaculerService;
        this.superAppObjectCrud = superAppObjectCrud;
        this.userCrud = userCrud;
        this.objectServiceRepo = new ObjectServiceRepo(superAppObjectCrud, userCrud);
    }

    public SuperAppObjectBoundary createRecipe(MiniAppCommandBoundary commandBoundary) {
        SuperAppObjectBoundary superAppObjectBoundary = new SuperAppObjectBoundary();
        userCrud.findByUserId(commandBoundary.getInvokedBy().getUserId());


    }

}
