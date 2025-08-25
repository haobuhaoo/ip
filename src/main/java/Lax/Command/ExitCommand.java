package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

/**
 * Represents an exit command.
 */
public class ExitCommand extends Command {
    /**
     * {@inheritDoc}
     * It sets the exit status of the command to <code>true</code> and closes the chatbot.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showExit();
        super.setExit(true);
    }
}
