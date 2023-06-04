package superapp.miniapps.commands;

import superapp.Boundary.SuperAppObjectBoundary;

public interface Command {
    public SuperAppObjectBoundary execute();
}

