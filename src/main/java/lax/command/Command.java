package lax.command;

import lax.Storage;
import lax.exception.InvalidCommandException;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents a command with a <code>boolean</code> exit that is primarily false unless the command is an
 * exit command.
 */
public abstract class Command {
    /**
     * The exit status of the command. By default, it is set as false.
     */
    private Boolean exit = false;

    public Boolean isExit() {
        return exit;
    }

    public void setExit(boolean e) {
        exit = e;
    }

    /**
     * Executes the given command.
     *
     * @param t The tasklist to modify.
     * @param u The ui for displaying messages to the user.
     * @param s The database for saving the tasklist.
     * @throws InvalidCommandException If the user inputs an invalid command.
     */
    public abstract void execute(TaskList t, Ui u, Storage s) throws InvalidCommandException;
}
