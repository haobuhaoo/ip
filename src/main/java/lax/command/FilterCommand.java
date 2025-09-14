package lax.command;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a filter command with a <code>String</code> dateTime.
 */
public class FilterCommand extends Command {
    /**
     * The dateTime used to filter by.
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
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.FILTER;
    }

    /**
     * {@inheritDoc}
     * It filters the <code>Catalogue</code> for all <code>Item</code> happening on the specified dateTime
     * and displays it to the user.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) throws InvalidCommandException {
        return ui.showList(catalogue.filterItems(dateTime.trim()));
    }
}
