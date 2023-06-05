package superapp.miniapps.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.logic.service.MiniAppServices.MiniAppCommandServiceWithAdminPermission;
import superapp.logic.service.SpoonaculerService;
import superapp.logic.service.SuperAppObjService.ObjectsServiceWithAdminPermission;
import superapp.logic.service.SuperAppObjService.SuperAppObjectRelationshipService;
import superapp.miniapps.commands.dietitiansHelper.RecipesCommandInterface;
import superapp.miniapps.commands.dietitiansHelper.CreateRecipeCommand;
@Service
public class RecipesCommandFactory implements RecipesCommandInterface {
    CreateRecipeCommand createRecipeCommand;

    public RecipesCommandFactory() {
        // Instantiate the createRecipeCommand using the concrete implementation
        createRecipeCommand = new CreateRecipeCommand();
    }

// @Autowired
//    public RecipesCommandFactory(SuperAppObjectRelationshipService relationshipService, SpoonaculerService spoonaculerService) {
//        this.createRecipeCommand = new CreateRecipeCommand(relationshipService,spoonaculerService);
//
//    }

    @Override
    public void createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary) {
        switch (commandsEnum) {
            case CREATE_RECIPE -> {
                createRecipe(commandBoundary);
                System.out.println("Processing createRecipe");
            }
            case MODIFY_RECIPE -> {
                modifyRecipe(commandBoundary);
                System.out.println("Processing modifyRecipe");
            }
            case DELETE_RECIPE -> {
                deleteRecipe(commandBoundary);
                System.out.println("Processing deleteRecipe");
            }
            case DELETE_ALL_RECIPES -> {
                deleteAllRecipes(commandBoundary);
                System.out.println("Processing deleteAllRecipes");
            }
            case GET_RECIPE -> {
                getRecipe(commandBoundary);
                System.out.println("Processing getRecipe");
            }
            case GET_ALL_RECIPES -> {
                getAllRecipe(commandBoundary);
                System.out.println("Processing getAllRecipes");
            }
            default -> System.out.println("Unknown command");
        }
    }


    @Override
    public MiniAppCommandBoundary createRecipe(MiniAppCommandBoundary commandBoundary) {
        createRecipeCommand.execute(commandBoundary);
        return commandBoundary;
    }

    @Override
    public MiniAppCommandBoundary modifyRecipe(MiniAppCommandBoundary commandBoundary) {
        return null;
    }

    @Override
    public void deleteRecipe(MiniAppCommandBoundary commandBoundary) {

    }

    @Override
    public void deleteAllRecipes(MiniAppCommandBoundary commandBoundary) {

    }

    @Override
    public MiniAppCommandBoundary getRecipe(MiniAppCommandBoundary commandBoundary) {
        return null;
    }

    @Override
    public MiniAppCommandBoundary getAllRecipe(MiniAppCommandBoundary commandBoundary) {
        return null;
    }


}
