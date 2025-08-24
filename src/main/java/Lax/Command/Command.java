package Lax.Command;

import Lax.Exception.InvalidCommandException;
import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

public abstract class Command {
    private Boolean exit = false;

    public Boolean isExit() {
        return exit;
    }

    public void setExit(boolean e) {
        exit = e;
    }

    public abstract void execute(TaskList t, Ui u, Storage s) throws InvalidCommandException;
}
