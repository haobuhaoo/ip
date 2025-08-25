package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

public class FindCommand extends Command {
    private final String task;

    public FindCommand(String t) {
        task = t;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList.findTask(task));
    }
}
