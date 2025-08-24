package Lax.Command;

import Lax.Exception.InvalidCommandException;
import Lax.Storage;
import Lax.Task.Task;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

public class AddCommand extends Command {
    private final String command;
    private final String type;

    public AddCommand(String c, String t) {
        command = c;
        type = t;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.addTask(command, type);
        storage.saveTask(taskList);
        ui.showSuccessMessage(print(t, taskList));
    }

    public String print(Task t, TaskList taskList) {
        return "Got it. I've added this task to the list:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }
}
