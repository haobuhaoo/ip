package Lax;

import Lax.Command.Command;
import Lax.Exception.InvalidCommandException;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Lax {
    private static Ui ui;
    private static Storage storage;
    private static TaskList taskList;

    public Lax(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.loadTask();
    }

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
