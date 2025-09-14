package lax.command;

import java.io.IOException;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.item.Item;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents an add command with a <code>String</code> task and <code>String</code> type.
 */
public class AddCommand extends Command {
    /**
     * The description of the item.
     */
    private final String description;

    /**
     * The type of item. It should only be of type <code>TODO</code>, <code>DEADLINE</code>,
     * <code>EVENT</code> for <code>Task</code> objects and <code>NOTE</code> for <code>Note</code>
     * objects.
     */
    private final String type;

    /**
     * Constructs the add command with an item description and type.
     *
     * @param d The item description.
     * @param t The type of item.
     */
    public AddCommand(String d, String t) {
        description = d;
        type = t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.ADD;
    }

    /**
     * {@inheritDoc}
     * It adds the new <code>Item</code> into the <code>Catalogue</code> and saves the <code>Catalogue</code>
     * into the database. After successful execution, a success message is displayed to the user.
     *
     * @throws InvalidCommandException If the user inputs an invalid command.
     * @throws IOException             If there is an error in writing to the database file.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) throws InvalidCommandException, IOException {
        Item item = catalogue.addItem(description, type);
        assert item != null : "item should not be null";

        storage.saveTask(catalogue);
        return ui.showSuccessMessage(print(item, catalogue));
    }

    /**
     * Prints the success message after an add execution.
     */
    public String print(Item item, Catalogue catalogue) {
        return "Got it. I've added this item to the list:\n  " + item
                + "\nNow you have " + catalogue.size() + " items in the list.";
    }
}
