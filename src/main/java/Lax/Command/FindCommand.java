package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

/**
 * Represents a find command with a <code>String</code> task.
 */
public class FindCommand extends Command {
    /**
     * The task description used to filter tasklist by.
     */
    private final String task;

    /**
     * Constructs a find command with a task.
     *
     * @param t The task description to filter by.
     */
    public FindCommand(String t) {
        task = t;
    }

    /**
     * {@inheritDoc}
     * It filters the tasklist for all <code>Task</code> by the keyword in the task name and displays
     * the filtered tasklist to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList.findTask(task));
    }
}
