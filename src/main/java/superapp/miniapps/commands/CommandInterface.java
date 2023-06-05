package superapp.miniapps.commands;

import superapp.Boundary.MiniAppCommandBoundary;

public interface CommandInterface {
    public Object createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary);


//    public MiniAppCommandBoundary createRecipe(MiniAppCommandBoundary commandBoundary);
//
//    public MiniAppCommandBoundary modifyRecipe(MiniAppCommandBoundary commandBoundary);
//
//    public void deleteRecipe(MiniAppCommandBoundary commandBoundary);
//
//    public void deleteAllRecipes(MiniAppCommandBoundary commandBoundary);
//
//    public MiniAppCommandBoundary getRecipe(MiniAppCommandBoundary commandBoundary);
//
//    public MiniAppCommandBoundary getAllRecipe(MiniAppCommandBoundary commandBoundary);
}

