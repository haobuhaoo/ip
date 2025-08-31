package lax.command;

import lax.Storage;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents a help command.
 */
public class HelpCommand extends Command {
    /**
     * {@inheritDoc}
     * It displays the full list of commands with its uses to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.showHelp();
    }
}
