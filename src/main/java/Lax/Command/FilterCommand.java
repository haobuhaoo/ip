package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

/**
 * Represents a filter command with a <code>String</code> dateTime.
 */
public class FilterCommand extends Command {
    /**
     * The dateTime used to filter tasklist by.
     */
    private final String dateTime;

    /**
     * Constructs a filter command with a dateTime.
     *
     * @param dt The dateTime to filter by.
     */
    public FilterCommand(String dt) {
        dateTime = dt;
    }

    /**
     * {@inheritDoc}
     * It filters the tasklist for all <code>Task</code> happening on the specified dateTime and displays
     * the filtered tasklist to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList.filterTask(dateTime));
    }
}
