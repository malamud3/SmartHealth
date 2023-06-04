package superapp.miniapps.commands;

<<<<<<< HEAD
import superapp.Boundary.SuperAppObjectBoundary;

public interface Command {
    public SuperAppObjectBoundary execute();
=======
import superapp.Boundary.MiniAppCommandBoundary;
import superapp.Boundary.SuperAppObjectBoundary;

public interface Command {
    public void execute(MiniAppCommandBoundary miniAppCommandBoundary);
>>>>>>> master3
}

