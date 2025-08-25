package Lax;

import Lax.Command.Command;
import Lax.Exception.InvalidCommandException;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

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
     * Constructs the chatbot with a String <code>filePath</code> to store the list of tasks to or retrieve
     * existing tasks from. It then loads the file into <code>taskList</code>.
     */
    public Lax(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.loadTask();
    }

    /**
     * Starts the chatbot for user interactions.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            try {
                String input = ui.readInput(scanner);
                if (input.isEmpty()) {
                    ui.emptyCmd();
                    continue;
                }

                Command command = Parser.parse(input);
                command.execute(taskList, ui, storage);
                exit = command.isExit();
            } catch (InvalidCommandException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.invalidDateTime();
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        new Lax("./data/data.txt").run();
    }
}
