package superapp.miniapps.commands.dietitiansHelper;

import superapp.Boundary.MiniAppCommandBoundary;

public interface RecipesCommandInterface {

    public MiniAppCommandBoundary createRecipe(MiniAppCommandBoundary commandBoundary);
    public MiniAppCommandBoundary modifyRecipe(MiniAppCommandBoundary commandBoundary);
    public void deleteRecipe(MiniAppCommandBoundary commandBoundary);
    public void deleteAllRecipes(MiniAppCommandBoundary commandBoundary);
    public MiniAppCommandBoundary getRecipe(MiniAppCommandBoundary commandBoundary);
    public MiniAppCommandBoundary getAllRecipe(MiniAppCommandBoundary commandBoundary);


}
