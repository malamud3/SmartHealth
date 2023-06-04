package superapp.miniapps.commands.dietitiansHelper;

import superapp.Boundary.SuperAppObjectBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;

public  abstract class DietitianCommandsInterface {
    private UserEntity userEntity;


    public abstract SuperAppObjectBoundary createRecipe(SuperAppObjectBoundary objectBoundary);

}
