package Lax;

import Lax.Task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

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
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
            }
            return new TaskList(taskList);
        }

        try (Scanner scanner = new Scanner(file)) {
            int corrupted = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split("\\|");
                boolean completed = data[1].trim().equals("1");

                try {
                    Task t = null;
                    switch (TaskList.TaskType.valueOf(data[0].trim().toUpperCase())) {
                    case TODO -> t = new Todo(data[2].trim(), completed);
                    case DEADLINE -> t = new Deadline(data[2].trim(), completed,
                            LocalDateTime.parse(data[3].trim()));
                    case EVENT -> t = new Event(data[2].trim(), completed,
                            LocalDateTime.parse(data[3].trim()), LocalDateTime.parse(data[4].trim()));
                    }
                    taskList.add(t);
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new RuntimeException(e);
                } catch (DateTimeParseException e) {
                    corrupted++;
                    System.out.println("Skipping corrupted task: " + line);
                }
            }

            if (corrupted > 0) {
                System.out.println("Total Corrupted Tasks: " + corrupted + "\n");
            }
        } catch (FileNotFoundException e) {
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
        try (FileWriter file = new FileWriter(filePath)) {
            taskList.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
