package lax.command;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.item.Item;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a delete command with a <code>String</code> taskNumber.
 */
public class DeleteCommand extends Command {
    /**
     * The item number to be deleted.
     */
    private final String taskNumber;

    /**
     * Constructs the delete command with the item number.
     *
     * @param c The task number.
     */
    public DeleteCommand(String c) {
        taskNumber = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }

    /**
     * {@inheritDoc}
     * It deletes the <code>Item</code> from the <code>Catalogue</code> and saves the <code>Catalogue</code>
     * into the database. After successful execution, a success message is displayed to the user.
     *
     * @throws InvalidCommandException If the user inputs an invalid command.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) throws InvalidCommandException {
        Item item = catalogue.deleteItem(taskNumber);
        assert item != null : "item should not be null";

        storage.saveTask(catalogue);
        return ui.showSuccessMessage(print(item, catalogue));
    }

    /**
     * Prints the success message after a delete execution.
     */
    public String print(Item item, Catalogue catalogue) {
        return "Noted. I've removed this item:\n  " + item
                + "\nNow you have " + catalogue.size() + " items in the list.";
    }
}
