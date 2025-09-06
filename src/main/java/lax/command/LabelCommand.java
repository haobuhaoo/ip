package lax.command;

import lax.Storage;
import lax.exception.InvalidCommandException;
import lax.task.Task;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents a label command with a <code>String</code> taskNumber and <code>boolean</code> mark.
 */
public class LabelCommand extends Command {
    /**
     * The task number to be labeled.
     */
    private final String taskNumber;

    /**
     * The type of label. <code>true</code> if label is mark. <code>false</code> if label is unmark.
     */
    private final boolean mark;

    /**
     * Constructs a label command with the task number and label type.
     *
     * @param c The task number.
     * @param t The label type.
     */
    public LabelCommand(String c, String t) {
        taskNumber = c;
        mark = t.equals("mark");
    }

    /**
     * {@inheritDoc}
     * It labels the <code>Task</code> specified by the task number and saves the tasklist into the
     * database. After a successful execution, a success message is displayed to the user.
     *
     * @param taskList The tasklist to modify.
     * @param ui       The ui for displaying messages to the user.
     * @param storage  The database for saving the tasklist.
     * @throws InvalidCommandException If the user inputs an invalid command.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.labelTask(taskNumber, mark);
        assert t != null : "task should not be null";

        storage.saveTask(taskList);
        return ui.showSuccessMessage(print(t));
    }

    /**
     * Prints the success message after an execution.
     *
     * @param t The <code>Task</code> labeled.
     * @return A <code>String</code> message.
     */
    public String print(Task t) {
        return (mark
                ? "Nice! I've marked this task as done:\n  "
                : "OK, I've marked this task as not done yet:\n  ") + t;
    }
}
