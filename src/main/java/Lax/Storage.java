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

public class Storage {
    private final String filePath;

    public Storage(String f) {
        filePath = f;
    }

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

    public void saveTask(TaskList taskList) {
        try (FileWriter file = new FileWriter(filePath)) {
            taskList.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
