package lax.command;

import lax.catalogue.Catalogue;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a find command with a <code>String</code> description.
 */
public class FindCommand extends Command {
    /**
     * The item description used to filter by.
     */
    private final String description;

    /**
     * Constructs a find command with a description keyword.
     *
     * @param t The task description to filter by.
     */
    public FindCommand(String t) {
        description = t;
    }

    /**
     * {@inheritDoc}
     * It filters the <code>Catalogue</code> for all <code>Item</code> by the keyword in the description
     * and displays it to the user.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) {
        return ui.showList(catalogue.findItems(description));
    }
}
