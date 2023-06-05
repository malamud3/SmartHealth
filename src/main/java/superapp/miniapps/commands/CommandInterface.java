package superapp.miniapps.commands;

import superapp.Boundary.MiniAppCommandBoundary;

public interface CommandInterface {
    public Object createCommand(CommandsEnum commandsEnum, MiniAppCommandBoundary commandBoundary);
}

