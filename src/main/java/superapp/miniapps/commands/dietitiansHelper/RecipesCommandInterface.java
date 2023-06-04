package superapp.miniapps.commands.dietitiansHelper;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;
import superapp.miniapps.commands.Command;
import superapp.miniapps.commands.CommandsEnum;

public interface RecipesCommandInterface {
    void createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary);

    public MiniAppCommandBoundary createRecipe(MiniAppCommandBoundary commandBoundary);

    public MiniAppCommandBoundary modifyRecipe(MiniAppCommandBoundary commandBoundary);

    public void deleteRecipe(MiniAppCommandBoundary commandBoundary);

    public void deleteAllRecipes(MiniAppCommandBoundary commandBoundary);

    public MiniAppCommandBoundary getRecipe(MiniAppCommandBoundary commandBoundary);

    public MiniAppCommandBoundary getAllRecipe(MiniAppCommandBoundary commandBoundary);
}

