package lax.item.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lax.item.Item;

/**
 * Represents a task with a <code>String</code> name and <code>boolean</code> completed.
 */
public abstract class Task implements Item {
    /**
     * The format of the dateTime that the chatbot outputs.
     */
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma", Locale.ENGLISH);

    /**
     * The name of the task which cannot be changed later.
     */
    private final String name;

    /**
     * The completion status of the task.
     */
    private boolean isCompleted;

    /**
     * Constructs the task with a name and its completion status. By default, all new <code>Task</code>
     * are not completed.
     *
     * @param n The name of the task.
     * @param c The completion status of the task.
     *          <code>true</code> represents completed, <code>false</code> otherwise.
     */
    public Task(String n, boolean c) {
        name = n;
        isCompleted = c;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markTask() {
        isCompleted = true;
    }

    public void unmarkTask() {
        isCompleted = false;
    }

    /**
     * Converts the dateTime into a <code>String</code> suitable for displaying.
     *
     * @return <li>The format is "MMM dd yyyy hh:mma".</li><li>Eg. "Aug 26 2025 12:32am".</li>
     */
    protected String parseDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_DATETIME_FORMAT)
                .replace("AM", "am")
                .replace("PM", "pm");
    }

    /**
     * Converts the task into a <code>String</code> suitable for saving into the database file.
     *
     * @return <li>"1 | name" if completed.</li><li>"0 | name" if not completed.</li>
     */
    @Override
    public String toFile() {
        return (isCompleted ? "1" : "0") + " | " + name;
    }

    /**
     * Converts the task into a <code>String</code> suitable for displaying.
     *
     * @return <li>"[X] name" if completed.</li><li>"[ ] name" if not completed.</li>
     */
    @Override
    public String toString() {
        return "[" + (isCompleted ? "X" : " ") + "] " + name;
    }
}
