package superapp.miniapps.commands.dietitiansHelper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;

import superapp.data.SuperAppObjectEntity;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("DELETE_ALL_RECIPES")
public class DeleteAllRecipesCommand implements Command {
    //private final  SpoonaculerService spoonaculerService;
    private SuperAppObjectUtility superAppObjectUtility;
    private SuperAppObjectCrud objectRepository;

    @Autowired
    public DeleteAllRecipesCommand(SuperAppObjectCrud objectRepository) {

        //  this.spoonaculerService = spoonaculerService;
        this.objectRepository = objectRepository;
        this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);
    }



    @Override
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        // 1. find the dietitian object
        // 2. remove the recipe from the dietitian object
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
        SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
        dietitian.deleteAllRecipes();
        objectRepository.save(dietitian);

        return new ArrayList<>();
    }

}
