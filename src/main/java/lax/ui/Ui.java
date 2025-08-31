package lax.ui;

import lax.task.TaskList;

/**
 * Represents the User Interface of the chatbot. It displays out messages to the user.
 */
public class Ui {
    /**
     * Displays the starting welcome message line.
     */
    public String showWelcome() {
        return """
                Hello! I'm Lax.
                What can I do for you?
                Key "help" to find out the list of commands.""";
    }

    /**
     * Displays the list of tasks that the user has stored in the database.
     */
    public String showList(TaskList taskList) {
        return taskList.showList(null);
    }

    /**
     * Displays the list of filtered tasks on a specific date.
     * <p>
     * This is an overloaded method.
     */
    public String showList(String taskList) {
        return taskList;
    }

    /**
     * Displays the success message after a command is executed successfully.
     */
    public String showSuccessMessage(String msg) {
        return msg;
    }

    /**
     * Displays the full list of commands and how it is being used.
     */
    public String showHelp() {
        return """
                List of Commands:
                - list
                - mark "task number"
                - unmark "task number"
                - todo "task name"
                - deadline "task name" /by "due DateTime"
                - event "task name" /from "start DateTime" /to "end DateTime"
                - delete "task number"
                - find "task description"
                - filter "DateTime"
                - help
                - bye""";
    }

    /**
     * Displays the exit message line.
     */
    public String showExit() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays the error messages that occurred.
     */
    public String showError(String msg) {
        return msg;
    }

    /**
     * Displays the error message specific to wrong DateTime format.
     */
    public String invalidDateTime() {
        return "Wrong DateTime format.\neg. 23-08-2025 1800";
    }

    /**
     * Displays the reminder message if user did not key in anything.
     */
    public String emptyCmd() {
        return "Please key something in.";
    }
}
