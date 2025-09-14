package lax;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import lax.catalogue.NoteList;
import lax.catalogue.TaskList;
import lax.command.Command;
import lax.command.Parser;
import lax.exception.InvalidCommandException;
import lax.storage.NotesStorage;
import lax.storage.TaskStorage;
import lax.ui.Ui;

/**
 * Represents the chatbot with an <code>Ui</code>, <code>TaskStorage</code>, <code>NotesStorage</code>,
 * <code>TaskList</code>, <code>NotesList</code> and <code>CommandType</code>. It allows <code>Items</code>
 * to be stored and managed in a database files specified.
 */
public class Lax {
    /**
     * User Interface of the chatbot.
     */
    private static Ui ui;

    /**
     * Task database of the chatbot.
     */
    private static TaskStorage taskStorage;

    /**
     * Notes database of the chatbot.
     */
    private static NotesStorage notesStorage;

    /**
     * List of tasks of the user.
     */
    private static TaskList taskList;

    /**
     * List of notes of the user.
     */
    private static NoteList notesList;

    /**
     * Type of command keyed in by the user.
     */
    private String commandType;

    /**
     * Constructs the chatbot with strings <code>taskPath</code> and <code>notesPath</code> to store
     * the list of items.
     */
    public Lax(String taskPath, String notesPath) throws IOException {
        ui = new Ui();
        taskStorage = new TaskStorage(taskPath);
        notesStorage = new NotesStorage(notesPath);
        taskList = taskStorage.loadTask();
        notesList = notesStorage.loadNotes();

        assert taskList != null : "taskList should not be null";
        assert notesList != null : "notesList should not be null";
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String invalidCmd = Command.CommandType.INVALID.name();
        try {
            if (input == null || input.trim().isEmpty()) {
                throw new InvalidCommandException("Empty command");
            }

            Command command = Parser.parse(input);
            commandType = command.getCommandType().name();
            assert !commandType.isEmpty() : "command type should not be empty";

            if (command.getNoteCommand()) {
                return command.execute(notesList, ui, notesStorage);
            } else {
                return command.execute(taskList, ui, taskStorage);
            }
        } catch (InvalidCommandException e) {
            commandType = invalidCmd;
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            commandType = invalidCmd;
            return ui.invalidDateTime();
        } catch (IOException e) {
            commandType = invalidCmd;
            return ui.showError("Failed to save data: " + e.getMessage());
        }
    }
}
