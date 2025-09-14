package lax.command;

import lax.catalogue.Catalogue;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a list command.
 */
public class ListCommand extends Command {
    /**
     * {@inheritDoc}
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.LIST;
    }

    /**
     * {@inheritDoc}
     * It displays the full <code>Catalogue</code> to the user.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) {
        return ui.showList(catalogue.showList());
    }
}
