package lax.command;

import java.io.IOException;

import lax.catalogue.Catalogue;
import lax.exception.InvalidCommandException;
import lax.storage.Storage;
import lax.ui.Ui;

/**
 * Represents a command.
 */
public abstract class Command {
    /**
     * Types of commands that can be executed.
     */
    public enum CommandType { ADD, DELETE, LABEL, LIST, FIND, FILTER, HELP, BYE, INVALID, START, EMPTY }

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
     * Returns the type of command.
     */
    public abstract CommandType getCommandType();

    /**
     * Executes the given command.
     */
    public abstract String execute(Catalogue t, Ui u, Storage s) throws InvalidCommandException, IOException;
}
