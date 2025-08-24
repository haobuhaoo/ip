public class LabelCommand extends Command {
    private final String taskNumber;
    private final boolean mark;

    public LabelCommand(String c, String t) {
        taskNumber = c;
        mark = t.equals("mark");
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidCommandException {
        Task t = taskList.labelTask(taskNumber, mark);
        storage.saveTask(taskList);
        ui.showSuccessMessage(print(t));
    }

    public String print(Task t) {
        return (mark
                ? "Nice! I've marked this task as done:\n  "
                : "OK, I've marked this task as not done yet:\n  ") + t;
    }
}
