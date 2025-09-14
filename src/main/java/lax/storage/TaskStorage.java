package lax.storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import lax.catalogue.TaskList;
import lax.item.task.Deadline;
import lax.item.task.Event;
import lax.item.task.Task;
import lax.item.task.Todo;

/**
 * Represents the database storage for tasks.
 */
public class TaskStorage extends Storage {
    public TaskStorage(String filePath) {
        super(filePath);
    }

    /**
     * Creates the specific <code>Task</code> based on what is written in the file.
     *
     * @param line The line that is read by the scanner.
     * @return The <code>Task</code> object created.
     * @throws DateTimeParseException If the format for dateTime is wrong.
     */
    private Task createTask(String line) throws DateTimeParseException {
        String[] data = line.split("\\|");
        assert data.length >= 3 : "tasks in the file should at least have the type, label and description";

        if (data[0].trim().isEmpty() || data[1].trim().isEmpty() || data[2].trim().isEmpty()) {
            super.handleCorruptedItem(line);
            return null;
        }

        boolean completed = data[1].trim().equals("1");

        switch (TaskList.TaskType.valueOf(data[0].trim().toUpperCase())) {
        case TODO -> {
            return new Todo(data[2].trim(), completed);
        }
        case DEADLINE -> {
            if (data.length < 4 || data[3].trim().isEmpty()) {
                super.handleCorruptedItem(line);
                return null;
            }

            return new Deadline(data[2].trim(), completed, LocalDateTime.parse(data[3].trim()));
        }
        case EVENT -> {
            if (data.length < 5 || data[3].trim().isEmpty() || data[4].trim().isEmpty()) {
                super.handleCorruptedItem(line);
                return null;
            }

            return new Event(data[2].trim(), completed,
                    LocalDateTime.parse(data[3].trim()), LocalDateTime.parse(data[4].trim()));
        }
        default -> {
            super.handleCorruptedItem(line);
            return null;
        }
        }
    }

    /**
     * Parses the line into a <code>Task</code> object.
     *
     * @param line The line to parse.
     * @return The created <code>Task</code>.
     */
    public Task parseLine(String line) {
        try {
            return createTask(line);
        } catch (IllegalArgumentException | IndexOutOfBoundsException | DateTimeParseException e) {
            super.handleCorruptedItem(line);
            return null;
        }
    }

    /**
     * Loads the file specified in filePath by reading every line of the file and converting it into a
     * <code>Task</code>, which then adds it into a taskList and is returned.
     *
     * @return The list of tasks stored previously or a new empty list.
     * @throws IOException If there is an error reading the file.
     */
    public TaskList loadTask() throws IOException {
        ArrayList<Task> taskList = super.load(new ArrayList<>(100), this::parseLine);
        return new TaskList(taskList);
    }
}
