package Lax.Ui;

import java.util.Scanner;

import Lax.Task.TaskList;

/**
 * Represents the User Interface of the chatbot. It prints out messages to the user.
 */
public class Ui {
    /**
     * Prints the starting welcome message line.
     */
    public void showWelcome() {
        System.out.println("""
                Hello! I'm Lax.
                What can I do for you?
                Key "help" to find out the list of commands.""");
    }

    /**
     * Reads user inputs via the <code>Scanner</code> while there is a next line. It applies simple
     * modifications to the input and returns it back to the chatbot for parsing.
     *
     * @return The <code>String</code> version of user input.
     */
    public String readInput(Scanner scanner) {
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Prints the list of tasks that the user has stored in the database.
     */
    public void showList(TaskList taskList) {
        System.out.println(taskList.showList(null));
    }

    /**
     * Prints the list of filtered tasks on a specific date.
     * <p>
     * This is an overloaded method.
     */
    public void showList(String taskList) {
        System.out.println(taskList);
    }

    /**
     * Prints the success message after a command is executed successfully.
     */
    public void showSuccessMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints the full list of commands and how it is being used.
     */
    public void showHelp() {
        System.out.println("""
                List of Commands:
                - list
                - mark "task number"
                - unmark "task number"
                - todo "task name"
                - deadline "task name" /by "due DateTime"
                - event "task name" /from "start DateTime" /to "end DateTime"
                - delete "task number"
                - find "DateTime"
                - help
                - bye""");
    }

    /**
     * Prints the exit message line.
     */
    public void showExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints the error messages that occurred.
     */
    public void showError(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints the error message specific to wrong DateTime format.
     */
    public void invalidDateTime() {
        System.out.println("Wrong DateTime format.\neg. 23-08-2025 1800");
    }

    /**
     * Prints the reminder message if user did not key in anything.
     */
    public void emptyCmd() {
        System.out.println("Please key something in.");
    }
}
