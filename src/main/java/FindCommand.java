public class FindCommand extends Command {
    private final String dateTime;

    public FindCommand(String dt) {
        dateTime = dt;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showList(taskList.filterTask(dateTime));
    }
}
