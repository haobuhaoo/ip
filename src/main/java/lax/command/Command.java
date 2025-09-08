package lax.command;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a command.
 */
public abstract class Command {
    /**
     * Indicates if the command is for the notesList.
     */
    private boolean isNoteCommand = false;

    public boolean getNoteCommand() {
        return isNoteCommand;
    }

    public void setNoteCommand(boolean noteCommand) {
        isNoteCommand = noteCommand;
    }

    /**
     * Executes the given command.
     */
    public abstract String execute(Catalogue t, Ui u, Storage s) throws InvalidCommandException;
}
