package Lax.Ui;

import java.util.Scanner;

import Lax.Task.TaskList;

public class Ui {
    public void showWelcome() {
        System.out.println("""
                Hello! I'm Lax.
                What can I do for you?
                Key "help" to find out the list of commands.""");
    }

    public String readInput(Scanner scanner) {
        return scanner.nextLine().trim().toLowerCase();
    }

    public void showList(TaskList taskList) {
        System.out.println(taskList.showList(null));
    }

    public void showList(String s) {
        System.out.println(s);
    }

    public void showSuccessMessage(String s) {
        System.out.println(s);
    }

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
                - find "task description"
                - filter "DateTime"
                - help
                - bye""");
    }

    public void showExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showError(String msg) {
        System.out.println(msg);
    }

    public void invalidDateTime() {
        System.out.println("Wrong DateTime format.\neg. 23-08-2025 1800");
    }

    public void emptyCmd() {
        System.out.println("Please key something in.");
    }
}
