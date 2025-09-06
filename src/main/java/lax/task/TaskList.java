package lax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lax.exception.InvalidCommandException;

/**
 * Represents the list of tasks stored in the database file. It has methods to add, delete, label, show,
 * filter and save the list of tasks.
 */
public class TaskList {
    /**
     * The format of the dateTime that user inputs.
     */
    private static final DateTimeFormatter INPUT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    /**
     * The format of the dateTime that the chatbot outputs.
     */
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

    /**
     * The arraylist to store the list of task.
     */
    private final ArrayList<Task> taskList;

    /**
     * The types of <code>Task</code> available.
     */
    public enum TaskType { TODO, DEADLINE, EVENT }

    /**
     * Constructs the list of task with an arraylist.
     *
     * @param t The arraylist of task.
     */
    public TaskList(ArrayList<Task> t) {
        taskList = t;
    }

    public int size() {
        return taskList.size();
    }

    /**
     * Parses the dateTime of the pattern of "dd-MM-yyyy HHmm" into a <code>LocalDateTime</code> object.
     *
     * @return <li>The format is "yyyy-MM-ddTHH:mm".</li><li>Eg. "2025-08-26T13:24".</li>
     * @throws DateTimeParseException If the dateTime cannot be parsed.
     */
    public LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, INPUT_DATETIME_FORMAT);
    }

    /**
     * Parses the dateTime into a string.
     *
     * @return An empty string if dateTime is null. Else, a string dateTime in the format of
     *         "MMM dd yyyy hh:mma"
     */
    private String getDateString(LocalDateTime dateTime) {
        return dateTime == null
                ? ""
                : " on " + dateTime.format(OUTPUT_DATETIME_FORMAT);
    }

    /**
     * Parses the taskList into a string, with the timestamp if dateTime is not null.
     */
    private String createStringList(String dateString) {
        if (taskList.isEmpty()) {
            return "There is no task in your list" + dateString + ".";
        }

        String taskString = IntStream.range(1, taskList.size() + 1)
                .mapToObj(i -> i + ". " + taskList.get(i - 1).toString())
                .collect(Collectors.joining("\n"));
        return "Here are the tasks in your list" + dateString + ":\n" + taskString;
    }

    /**
     * Converts the tasklist into a <code>String</code> for displaying.
     *
     * @param dateTime The dateTime used when filtering tasks.
     * @return A <code>String</code> representation of the tasklist with each task being listed out.
     */
    public String showList(LocalDateTime dateTime) {
        String dateString = getDateString(dateTime);
        return createStringList(dateString);
    }

    /**
     * Updates the task label.
     *
     * @param number The index of the <code>Task</code> in the taskList.
     * @param mark   Mark or Unmark the <code>Task</code>.
     * @return The <code>Task</code> that is modified.
     * @throws InvalidCommandException When <code>Task</code> is already labelled as <code>mark</code>.
     */
    private Task updateTaskLabel(String number, boolean mark) throws InvalidCommandException {
        Task t = taskList.get(Integer.parseInt(number) - 1);
        assert t != null : "task should not be null";

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
    }

    /**
     * Labels the task based on the command given. It gets the task from the tasklist and labels it, and
     * returns the task back.
     * <p>
     * If tasklist is empty, or if task number is invalid, or if task has been previously labeled, it throws
     * an <code>InvalidCommandException</code>.
     *
     * @param number The task number to be labeled.
     * @param mark   The command given.
     *               <code>true</code> if command is mark. <code>false</code> if command is unmark.
     * @return The task that is being labeled.
     * @throws InvalidCommandException If no task to be labeled or task has already been previously labeled
     *                                 or task number is invalid.
     */
    public Task labelTask(String number, boolean mark) throws InvalidCommandException {
        if (taskList.isEmpty()) {
            throw new InvalidCommandException("No task to be " + (mark ? "marked" : "unmarked"));
        }

        try {
            return updateTaskLabel(number, mark);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. mark 1\neg. unmark 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    /**
     * Creates the corresponding <code>Task</code> based on the type specified.
     *
     * @param task The task description.
     * @param type The task type.
     * @return The <code>Task</code> created.
     * @throws InvalidCommandException If task description is in wrong format.
     */
    private Task createTask(String task, String type) throws InvalidCommandException {
        switch (TaskType.valueOf(type.toUpperCase())) {
        case TODO -> {
            return new Todo(task);
        }
        case DEADLINE -> {
            try {
                String[] data = task.split("/by");
                return new Deadline(data[0].trim(), parseDateTime(data[1].trim()));
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidCommandException("eg. deadline return book /by 23-08-2025 1800");
            }
        }
        case EVENT -> {
            try {
                String[] data = task.split("/from");
                String[] timing = data[1].trim().split("/to");
                return new Event(data[0].trim(), parseDateTime(timing[0].trim()), parseDateTime(timing[1].trim()));
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidCommandException("eg. event project meeting "
                        + "/from 23-08-2025 1400 /to 23-08-2025 1600");
            }
        }
        default -> throw new InvalidCommandException("No such task.");
        }
    }

    /**
     * Adds the new <code>Task</code> into the tasklist. It splits the task description into sections and
     * creates a <code>Task</code> object.
     * <p>
     * If there are missing information or invalid dateTime format, <code>InvalidCommandException</code> and
     * <code>DateTimeParseException</code> is thrown respectively.
     *
     * @param task The task description.
     * @param type The type of task.
     * @return The new <code>Task</code> that is added.
     * @throws InvalidCommandException If there is missing information in the task description.
     */
    public Task addTask(String task, String type) throws InvalidCommandException {
        try {
            Task t = createTask(task, type);
            taskList.add(t);
            return t;
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + task + "\"");
        }
    }

    /**
     * Deletes a <code>Task</code> from the tasklist and returns the deleted <code>Task</code>.
     * <p>
     * If tasklist is empty or invalid task number, it throws an <code>InvalidCommandException</code>  .
     *
     * @param number The task number.
     * @return The deleted <code>Task</code>.
     * @throws InvalidCommandException If tasklist is empty or invalid task number.
     */
    public Task deleteTask(String number) throws InvalidCommandException {
        if (taskList.isEmpty()) {
            throw new InvalidCommandException("No task to delete.");
        }

        try {
            return taskList.remove(Integer.parseInt(number) - 1);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    /**
     * Finds and filters all <code>Task</code> in the tasklist by the keyword in the task name.
     *
     * @param desc The keyword to find by.
     * @return A <code>String</code> representation of the filtered tasklist.
     */
    public String findTask(String desc) {
        ArrayList<Task> newTask = taskList.stream()
                .filter(t -> t.getName().contains(desc))
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(newTask).showList(null);
    }

    /**
     * Filters the whole tasklist for tasks happening on the specific dateTime.
     * <p>
     * If the dateTime is of wrong format, it throws a <code>DateTimeParseException</code>.
     *
     * @param dt The dateTime to filter by.
     * @return A <code>String</code> representation of the filtered tasklist.
     */
    public String filterTask(String dt) {
        LocalDateTime dateTime = parseDateTime(dt);
        ArrayList<Task> newTask = taskList.stream()
                .filter(t -> {
                    if (t instanceof Deadline temp) {
                        return dateTime.isBefore(temp.getDueDate());
                    } else if (t instanceof Event temp) {
                        boolean isAfterStartDate = dateTime.isAfter(temp.getStartDate());
                        boolean isBeforeEndDate = dateTime.isBefore(temp.getEndDate());
                        return isAfterStartDate && isBeforeEndDate;
                    } else {
                        return false;
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(newTask).showList(dateTime);
    }

    /**
     * Serialize the current tasklist into the correct format.
     *
     * @return An <code>ArrayList</code> of <code>Task</code> in the correct format.
     */
    public ArrayList<String> serialize() {
        return taskList.stream()
                .map(Task::toFile)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
