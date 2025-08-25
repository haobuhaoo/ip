package Lax.Task;

import Lax.Exception.InvalidCommandException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents the list of tasks stored in the database file. It has methods to add, delete, label, show,
 * filter and save the list of tasks.
 */
public class TaskList {
    /**
     * The arraylist to store the list of task.
     */
    private final ArrayList<Task> taskList;

    /**
     * The types of <code>Task</code> available.
     */
    public enum TaskType {TODO, DEADLINE, EVENT}

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
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
    }

    /**
     * Converts the tasklist into a <code>String</code> for displaying.
     *
     * @param dateTime The dateTime used when filtering tasks.
     * @return A <code>String</code> representation of the tasklist with each task being listed out.
     */
    public String showList(LocalDateTime dateTime) {
        String tempString = dateTime == null ? "" : " on "
                + dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
        if (taskList.isEmpty()) return "There is no task in your list" + tempString + ".";

        StringBuilder s = new StringBuilder("Here are the tasks in your list" + tempString + ":");
        int n = 1;
        for (Task i : taskList) {
            s.append("\n").append(n).append(". ").append(i.toString());
            n++;
        }
        return s.toString();
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
        if (taskList.isEmpty()) throw new InvalidCommandException("No task to delete.");

        try {
            return taskList.remove(Integer.parseInt(number) - 1);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
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

    /**
     * Saves the currently tasklist into the database file.
     *
     * @throws IOException If there is an error writing to the file.
     */
    public void save(FileWriter file) throws IOException {
        if (taskList.isEmpty()) return;

        for (Task t : taskList) {
            file.write(t.toFile() + "\n");
        }
    }
}
