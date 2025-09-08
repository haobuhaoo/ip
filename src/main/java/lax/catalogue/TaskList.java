package lax.catalogue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import lax.exception.InvalidCommandException;
import lax.item.task.Deadline;
import lax.item.task.Event;
import lax.item.task.Task;
import lax.item.task.Todo;

/**
 * Represents the list of tasks stored in the database file.
 */
public class TaskList implements Catalogue {
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
     * Converts the taskList into a <code>String</code> for displaying.
     */
    @Override
    public String showList() {
        return Catalogue.super.showList(null, taskList);
    }

    /**
     * Updates the task label.
     *
     * @param number The index of the <code>Task</code> in the taskList.
     * @param mark   Mark or Unmark the <code>Task</code>.
     * @return The <code>Task</code> that is modified.
     * @throws InvalidCommandException If <code>Task</code> is already labelled as param <code>mark</code>.
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
     * Labels the task based on the command given.
     *
     * @param number The task number to be labelled.
     * @param mark   The command given.
     *               <code>true</code> if command is mark. <code>false</code> if command is unmark.
     * @return The task that is being labelled.
     * @throws InvalidCommandException If no task to be labelled or task has already been previously labelled
     *                                 or invalid task number.
     */
    @Override
    public Task labelItem(String number, boolean mark) throws InvalidCommandException {
        if (taskList.isEmpty()) {
            throw new InvalidCommandException("No task to be " + (mark ? "marked" : "unmarked"));
        }

        try {
            return updateTaskLabel(number, mark);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. task mark 1\neg. task unmark 1");
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
                throw new InvalidCommandException("eg. task deadline return book /by 23-08-2025 1800");
            }
        }
        case EVENT -> {
            try {
                String[] data = task.split("/from");
                String[] timing = data[1].trim().split("/to");
                return new Event(data[0].trim(), parseDateTime(timing[0].trim()), parseDateTime(timing[1].trim()));
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidCommandException("eg. task event project meeting "
                        + "/from 23-08-2025 1400 /to 23-08-2025 1600");
            }
        }
        default -> throw new InvalidCommandException("No such task.");
        }
    }

    /**
     * Adds the new <code>Task</code> into the taskList.
     * <p>
     * If dateTime is of wrong format, <code>DateTimeParseException</code> is thrown.
     *
     * @param task The task description.
     * @param type The type of task.
     * @return The new <code>Task</code> that is added.
     * @throws InvalidCommandException If there is missing information in the task description.
     */
    @Override
    public Task addItem(String task, String type) throws InvalidCommandException {
        try {
            Task t = createTask(task, type);
            taskList.add(t);
            return t;
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + task + "\"");
        }
    }

    /**
     * Deletes the <code>Task</code> from the taskList.
     *
     * @param number The task number.
     * @return The deleted task.
     * @throws InvalidCommandException If taskList is empty or invalid task number.
     */
    @Override
    public Task deleteItem(String number) throws InvalidCommandException {
        if (taskList.isEmpty()) {
            throw new InvalidCommandException("No task to delete.");
        }

        try {
            return taskList.remove(Integer.parseInt(number) - 1);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. task delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    /**
     * Finds all <code>Task</code> in the taskList by the keyword in the task name.
     *
     * @param desc The keyword to find by.
     * @return A <code>String</code> representation of the filtered taskList.
     */
    @Override
    public String findItems(String desc) {
        ArrayList<Task> newTask = taskList.stream()
                .filter(t -> t.getName().contains(desc))
                .collect(Collectors.toCollection(ArrayList::new));
        return Catalogue.super.showList(null, newTask);
    }

    /**
     * Checks if the <code>Task</code> is currently ongoing base on the dateTime given.
     *
     * @param t        The task of interest.
     * @param dateTime The dateTime of interest.
     * @return <code>true</code> if it is ongoing, <code>false</code> otherwise.
     */
    private boolean happeningNow(Task t, LocalDateTime dateTime) {
        if (t instanceof Deadline temp) {
            return dateTime.isBefore(temp.getDueDate());
        } else if (t instanceof Event temp) {
            boolean isAfterStartDate = dateTime.isAfter(temp.getStartDate());
            boolean isBeforeEndDate = dateTime.isBefore(temp.getEndDate());
            return isAfterStartDate && isBeforeEndDate;
        } else {
            return false;
        }
    }

    /**
     * Filters the taskList for tasks happening on the specific dateTime.
     * <p>
     * If the dateTime is of wrong format, it throws a <code>DateTimeParseException</code>.
     *
     * @param dt The dateTime to filter by.
     * @return A <code>String</code> representation of the filtered taskList.
     */
    @Override
    public String filterItems(String dt) {
        LocalDateTime dateTime = Catalogue.super.parseDateTime(dt);
        ArrayList<Task> newTask = taskList.stream()
                .filter(t -> happeningNow(t, dateTime))
                .collect(Collectors.toCollection(ArrayList::new));
        return Catalogue.super.showList(dateTime, newTask);
    }

    /**
     * Serialize the current taskList into the correct format.
     *
     * @return An <code>ArrayList</code> of string representation of tasks in the correct format.
     */
    public ArrayList<String> serialize() {
        return taskList.stream()
                .map(Task::toFile)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
