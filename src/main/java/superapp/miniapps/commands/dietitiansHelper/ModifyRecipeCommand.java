package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("MODIFY_RECIPE")
public class ModifyRecipeCommand  implements Command  {

	private SuperAppObjectUtility superAppObjectUtility;
	private SuperAppObjectCrud objectRepository;

	@Autowired
	public ModifyRecipeCommand(SuperAppObjectCrud objectRepository) {
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
