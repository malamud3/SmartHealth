package superapp.miniapps.commands.usersConnections;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.ObjectId;
import superapp.dal.SuperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.logic.utilitys.SuperAppObjectUtility;
import superapp.logic.utilitys.UserUtility;
import superapp.miniapps.commands.Command;

@Component("UNFOLLOW_DIETITIAN")
public class UnFollowDietitianCommand implements Command{
	private SuperAppObjectUtility superAppObjectUtility;
	private SuperAppObjectCrud superAppObjectCrud;



	@Autowired
	public UnFollowDietitianCommand(SuperAppObjectCrud objectRepository) {
		this.superAppObjectCrud = objectRepository;  
		this.superAppObjectUtility = new SuperAppObjectUtility(objectRepository);

	}


	@Override
	public Object execute(MiniAppCommandBoundary miniAppCommandBoundary) {
        ObjectId idObject = miniAppCommandBoundary.getTargetObject().getObjectId();
		SuperAppObjectEntity dietitian = superAppObjectUtility.checkSuperAppObjectEntityExist(idObject);
		
		// in the future list of followers by username
		//String username = userUtility.checkUserExist(miniAppCommandBoundary.getInvokedBy().getUserId()).getUsername();
		
		dietitian.deleteFollowerFromObjectDetails(miniAppCommandBoundary.getInvokedBy().getUserId());
		
		superAppObjectCrud.save(dietitian);
		return new ArrayList<>();
		
	}

}
