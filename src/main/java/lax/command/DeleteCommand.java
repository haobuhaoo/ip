package lax.command;

import lax.Storage;
import lax.exception.InvalidCommandException;
import lax.task.Task;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents a delete command with a <code>String</code> taskNumber.
 */
public class DeleteCommand extends Command {
    /**
     * The task number to be deleted.
     */
    private final String taskNumber;

    /**
     * Constructs the delete command with a task number.
     *
     * @param c The task number.
     */
    public DeleteCommand(String c) {
        taskNumber = c;
    }

    /**
     * {@inheritDoc}
     * It deletes the <code>Task</code> from the tasklist and saves the tasklist into the database. After
     * successful execution, a success message is displayed to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     * @throws InvalidCommandException If the user inputs an invalid command.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.deleteTask(taskNumber);
        storage.saveTask(taskList);
        ui.showSuccessMessage(print(t, taskList));
    }

    /**
     * Prints the success message after an execution.
     *
     * @param t        The <code>Task</code> deleted.
     * @param taskList The tasklist.
     * @return A <code>String</code> message.
     */
    public String print(Task t, TaskList taskList) {
        return "Noted. I've removed this task:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }
}
