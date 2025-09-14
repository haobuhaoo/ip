package lax.command;

import lax.catalogue.Catalogue;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a help command.
 */
public class HelpCommand extends Command {
    /**
     * {@inheritDoc}
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.HELP;
    }

    /**
     * {@inheritDoc}
     * It displays the full list of commands with its uses to the user.
     */
    @Override
    public String execute(Catalogue catalogue, Ui ui, Storage storage) {
        return ui.showHelp();
    }
}
