package lax.command;

import java.io.IOException;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.item.Item;
import lax.storage.Storage;
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
    private final boolean isMark;

    /**
     * Constructs a label command with the task number and label type.
     *
     * @param c The task number.
     * @param t The label type.
     */
    public LabelCommand(String c, String t) {
        taskNumber = c;
        isMark = t.equals("mark");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.LABEL;
    }

    /**
     * {@inheritDoc}
     * It labels the <code>Task</code> specified by the task number and saves the taskList into the database.
     * After a successful execution, a success message is displayed to the user.
     * <p>
     * Only <code>Task</code> can be labelled.
     *
     * @throws InvalidCommandException If the user inputs an invalid command or if a note is being labelled.
     * @throws IOException             If there is an error in writing to the database file.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) throws InvalidCommandException, IOException {
        Item item = catalogue.labelItem(taskNumber, isMark);
        assert item != null : "item should not be null";

        storage.saveTask(catalogue);
        return ui.showSuccessMessage(print(item));
    }

    /**
     * Prints the success message after a label execution.
     */
    public String print(Item item) {
        return (isMark
                ? "Nice! I've marked this item as done:\n  "
                : "OK, I've marked this item as not done yet:\n  ") + item;
    }
}
