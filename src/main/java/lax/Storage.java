package lax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lax.task.Deadline;
import lax.task.Event;
import lax.task.Task;
import lax.task.TaskList;
import lax.task.Todo;

/**
 * Represents the database of the chatbot specified at the <code>String</code> filePath.
 */
public class Storage {
    /**
     * The path of the database file.
     */
    private final String filePath;

    /**
     * Constructs the database at the specified <code>filePath</code>.
     *
     * @param f The file path.
     */
    public Storage(String f) {
        filePath = f;
    }

    /**
     * Creates the file to store the taskList.
     */
    private void createFile(File file) {
        try {
            if (!file.createNewFile()) {
                System.out.println("File could not be created.");
            }
        } catch (IOException e) {
            System.out.println("Error creating new file: " + e.getMessage());
        }
    }

    /**
     * Creates the parent directory for the file that stores the taskList.
     */
    private void createFileDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                System.out.println("Error creating parent directory.");
            }
        }
    }

    /**
     * Creates the specific <code>Task</code> based on what is written in the file.
     *
     * @param line The line that is read by the scanner in the file.
     * @return <code>Task</code> object.
     * @throws DateTimeParseException If the format for dateTime is written wrongly.
     */
    private Task createTask(String line) throws DateTimeParseException {
        String[] data = line.split("\\|");
        assert data.length >= 2 : "tasks in the file should at least have the type and label";

        boolean completed = data[1].trim().equals("1");

        switch (TaskList.TaskType.valueOf(data[0].trim().toUpperCase())) {
        case TODO:
            return new Todo(data[2].trim(), completed);
        case DEADLINE:
            return new Deadline(data[2].trim(), completed, LocalDateTime.parse(data[3].trim()));
        case EVENT:
            return new Event(data[2].trim(), completed,
                    LocalDateTime.parse(data[3].trim()), LocalDateTime.parse(data[4].trim()));
        default:
            throw new RuntimeException("Invalid task format");
        }
    }

    /**
     * Increments the total count of corrupted tasks and prints out the line that is corrupted.
     */
    private void handleCorruptedTask(int[] corrupted, String line) {
        corrupted[0]++;
        System.out.println("Skipping corrupted task: " + line);
    }

    /**
     * Prints the total number of corrupted tasks in the file.
     */
    private void printTotalCorruptedTasks(int[] corrupted) {
        System.out.println("Total Corrupted Tasks: " + corrupted[0] + "\n");
    }

    /**
     * Loads the file specified in <code>filePath</code> by reading every line of the file and converting
     * it into a <code>Task</code>, which then adds it into an <code>TaskList</code> and is returned.
     * <p>
     * If file does not exist, it will create a new file at the exact <code>filePath</code> specified and
     * returns an empty <code>TaskList</code> instead.
     * <p>
     * If the file has been corrupted with anomalous data, such lines will be ignored and reported back to
     * the user, without stopping the chatbot from continue running.
     *
     * @return The list of tasks stored previously or a new empty list.
     */
    public TaskList loadTask() {
        ArrayList<Task> taskList = new ArrayList<>(100);
        File file = new File(filePath);

        if (!file.exists()) {
            createFileDirectory(file);
            createFile(file);
            return new TaskList(taskList);
        }

        try (Stream<String> lines = Files.lines(file.toPath())) {
            int[] corrupted = { 0 };
            taskList = lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> {
                                try {
                                    return createTask(line);
                                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                                    throw new RuntimeException(e);
                                } catch (DateTimeParseException e) {
                                    handleCorruptedTask(corrupted, line);
                                    return null;
                                }
                            }
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (corrupted[0] > 0) {
                printTotalCorruptedTasks(corrupted);
            }
        } catch (IOException e) {
            System.out.println("Error reading file from hard disk: " + e.getMessage());
        }

        return new TaskList(taskList);
    }

    /**
     * Saves the existing version of <code>TaskList</code> into the file specified by writing directly over
     * the existing file.
     *
     * @param taskList The <code>TaskList</code> that is being read and write into the file.
     */
    public void saveTask(TaskList taskList) {
        try {
            Path path = new File(filePath).toPath();
            Files.write(path, taskList.serialize());
        } catch (IOException e) {
            throw new RuntimeException("Error saving task: " + e);
        }
    }
}
