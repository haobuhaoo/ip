import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskList;

    public enum TaskType {TODO, DEADLINE, EVENT}

    public TaskList(ArrayList<Task> t) {
        taskList = t;
    }

    public int size() { return taskList.size(); }

    public LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
    }

    public String showList(LocalDateTime dateTime) {
        String tempString = dateTime == null ? "" : " on " + dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
        if (taskList.isEmpty()) return "There is no task in your list" + tempString + ".";

        StringBuilder s = new StringBuilder("Here are the tasks in your list" + tempString + ":");
        int n = 1;
        for (Task i : taskList) {
            s.append("\n").append(n).append(". ").append(i.toString());
            n++;
        }
        return s.toString();
    }

    public Task labelTask(String number, boolean mark) throws InvalidCommandException {
        if (taskList.isEmpty())
            throw new InvalidCommandException("No task to be " + (mark ? "marked" : "unmarked"));

        try {
            Task t = taskList.get(Integer.parseInt(number) - 1);
            if (mark) {
                if (t.isCompleted()) {
                    throw new InvalidCommandException("Task \"" + t.getName() + "\" is already marked as done");
                }

                t.markTask();
            } else {
                if (!t.isCompleted()) {
                    throw new InvalidCommandException("Task \"" + t.getName() + "\" is already marked as not done");
                }

                t.unmarkTask();
            }
            return t;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. mark 1\neg. unmark 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    public Task addTask(String task, String type) throws InvalidCommandException {
        try {
            Task t;
            switch (TaskType.valueOf(type.toUpperCase())) {
            case TODO -> t = new Todo(task);
            case DEADLINE -> {
                try {
                    String[] data = task.split("/by");
                    t = new Deadline(data[0].trim(), parseDateTime(data[1].trim()));
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. deadline return book /by 23-08-2025 1800");
                }
            }
            case EVENT -> {
                try {
                    String[] data = task.split("/from");
                    String[] timing = data[1].trim().split("/to");
                    t = new Event(data[0].trim(), parseDateTime(timing[0].trim()), parseDateTime(timing[1].trim()));
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. event project meeting "
                            + "/from 23-08-2025 1400 /to 23-08-2025 1600");
                }
            }
            default -> throw new InvalidCommandException("No such task.");
            }

            taskList.add(t);
            return t;
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + task + "\"");
        }
    }

    public Task deleteTask(String number) throws InvalidCommandException {
        if (taskList.isEmpty()) throw new InvalidCommandException("No task to delete.");

        try {
            return taskList.remove(Integer.parseInt(number) - 1);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    public String filterTask(String dt) {
        LocalDateTime dateTime = parseDateTime(dt);
        ArrayList<Task> newTask = new ArrayList<>(100);
        for (Task t : taskList) {
            if (t instanceof Deadline temp) {
                if (temp.getDueDate().isEqual(dateTime) || temp.getDueDate().isAfter(dateTime)) {
                    newTask.add(t);
                }
            } else if (t instanceof Event temp) {
                if ((temp.getStartDate().isEqual(dateTime) || temp.getStartDate().isBefore(dateTime))
                        && (temp.getEndDate().isEqual(dateTime) || temp.getEndDate().isAfter(dateTime))) {
                    newTask.add(t);
                }
            }
        }
        return new TaskList(newTask).showList(dateTime);
    }

    public void save(FileWriter file) throws IOException {
        if (taskList.isEmpty()) return;

        for (Task t : taskList) {
            file.write(t.toFile() + "\n");
        }
    }
}
