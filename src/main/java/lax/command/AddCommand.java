package lax.command;

import lax.Storage;
import lax.exception.InvalidCommandException;
import lax.task.Task;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents an add command with a <code>String</code> task and <code>String</code> type.
 */
public class AddCommand extends Command {
    /**
     * The task description.
     */
    private final String task;

    /**
     * The type of task. It should only be of type <code>TODO</code>, <code>DEADLINE</code>,
     * <code>EVENT</code>.
     */
    private final String type;

    /**
     * Constructs the add command with a task description and task type.
     *
     * @param td The task description.
     * @param t  The type of task.
     */
    public AddCommand(String td, String t) {
        task = td;
        type = t;
    }

    /**
     * {@inheritDoc}
     * It adds the new <code>Task</code> into the tasklist and saves the tasklist into the database. After
     * successful execution, a success message is displayed to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     * @throws InvalidCommandException If the user inputs an invalid command.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.addTask(task, type);
        storage.saveTask(taskList);
        return ui.showSuccessMessage(print(t, taskList));
    }

    /**
     * Prints the success message after an execution.
     *
     * @param t        The new <code>Task</code> added.
     * @param taskList The tasklist.
     * @return A <code>String</code> message.
     */
    public String print(Task t, TaskList taskList) {
        return "Got it. I've added this task to the list:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }
}
