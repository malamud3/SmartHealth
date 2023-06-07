package superapp.miniapps.commands.dietitiansHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.Exceptions.RecipeNotExistException;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.miniapps.commands.Command;

@Component("GET_RECIPE")
public class GetRecipeCommand implements Command {

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

		// 1. Find the dietitian object using the provided object ID
		ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
		SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
		
		String recipeId = (String) miniAppCommandBoundary.getCommandAttributes().get("recipeId");
		if(recipeId == null) {
			throw new RecipeNotExistException();
		}
		

		// 2. Retrieve the recipe from the dietitian's object details using provided recipeId
		return dietitian.getRecipeById(recipeId);
	}


}
