package superapp.miniapps.commands.dietitiansHelper;

import superapp.Boundary.MiniAppCommandBoundary;
import superapp.miniapps.commands.Command;

public interface RecipesCommandFactory {
       Command createCommand(String commandName);

}
//    public MiniAppCommandBoundary createRecipe(MiniAppCommandBoundary commandBoundary);
//    public MiniAppCommandBoundary modifyRecipe(MiniAppCommandBoundary commandBoundary);
//    public void deleteRecipe(MiniAppCommandBoundary commandBoundary);
//    public void deleteAllRecipes(MiniAppCommandBoundary commandBoundary);
//    public MiniAppCommandBoundary getRecipe(MiniAppCommandBoundary commandBoundary);
//    public MiniAppCommandBoundary getAllRecipe(MiniAppCommandBoundary commandBoundary);
