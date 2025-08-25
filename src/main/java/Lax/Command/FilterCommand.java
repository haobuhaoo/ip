package Lax.Command;

import Lax.Storage;
import Lax.Task.TaskList;
import Lax.Ui.Ui;

public class FilterCommand extends Command {
    private final String dateTime;

    public FilterCommand(String dt) {
        dateTime = dt;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList.filterTask(dateTime));
    }
}
