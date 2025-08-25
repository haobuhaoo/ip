package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

/**
 * Represents a list command.
 */
public class ListCommand extends Command {
    /**
     * {@inheritDoc}
     * It displays the full tasklist to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList);
    }
}
