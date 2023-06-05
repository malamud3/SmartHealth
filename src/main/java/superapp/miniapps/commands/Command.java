package superapp.miniapps.commands;


import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;

public interface Command {
    public Object execute(MiniAppCommandBoundary miniAppCommandBoundary);
}

