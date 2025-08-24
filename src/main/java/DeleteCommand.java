public class DeleteCommand extends Command {
    private final String taskNumber;

    public DeleteCommand(String c) {
        taskNumber = c;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.deleteTask(taskNumber);
        storage.saveTask(taskList);
        ui.showSuccessMessage(print(t, taskList));
    }

    public String print(Task t, TaskList taskList) {
        return "Noted. I've removed this task:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.";
    }
}
