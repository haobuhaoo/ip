package lax;

import java.time.format.DateTimeParseException;

import lax.command.Command;
import lax.exception.InvalidCommandException;
import lax.task.TaskList;
import lax.ui.Ui;

/**
 * Represents the chatbot with an <code>Ui</code>, <code>Storage</code> and <code>TaskList</code>.
 * <p>
 * It allows <code>Task</code> to be stored and managed in a database file specified.
 */
public class Lax {
    /**
     * User Interface of the chatbot.
     */
    private static Ui ui;

    /**
     * Database of the chatbot.
     */
    private static Storage storage;

    /**
     * List of Tasks of the user.
     */
    private static TaskList taskList;

    /**
     * Type of command keyed in by the user.
     */
    private String commandType;

    /**
     * Constructs the chatbot with a String <code>filePath</code> to store the list of tasks to or retrieve
     * existing tasks from. It then loads the file into <code>taskList</code>.
     */
    public Lax(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.loadTask();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            commandType = command.getClass().getSimpleName();
            return command.execute(taskList, ui, storage);
        } catch (InvalidCommandException e) {
            commandType = "InvalidCommand";
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            commandType = "InvalidCommand";
            return ui.invalidDateTime();
        }
    }

    public String getCommandType() {
        return commandType;
    }
}
