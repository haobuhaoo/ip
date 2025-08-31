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
     * Executes the given command.
     */
    public abstract String execute(TaskList t, Ui u, Storage s) throws InvalidCommandException;
}
