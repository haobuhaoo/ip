package lax.command;

import lax.catalogue.Catalogue;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents an exit command.
 */
public class ExitCommand extends Command {
    /**
     * {@inheritDoc}
     * It displays the exit message to the user.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) {
        return ui.showExit();
    }
}
