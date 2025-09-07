package lax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a Deadline task with a <code>String</code> name, <code>boolean</code> completed and
 * <code>LocalDateTime</code> dueDate.
 */
public class Deadline extends Task {
    /**
     * The format of the dateTime that the chatbot outputs.
     */
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma", Locale.ENGLISH);

    /**
     * The due date of the task.
     */
    private final LocalDateTime dueDate;

    /**
     * Constructs the task with a name and due date, with completed set as false.
     *
     * @param n The name of the task.
     * @param d The due date of the task.
     */
    public Deadline(String n, LocalDateTime d) {
        this(n, false, d);
    }

    /**
     * Constructs the task with a name, completion status and due date.
     *
     * @param n The name of the task.
     * @param c The completion status of the task.
     * @param d The due date of the task.
     */
    public Deadline(String n, Boolean c, LocalDateTime d) {
        super(n, c);
        dueDate = d;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Converts the dateTime into a <code>String</code> suitable for displaying.
     *
     * @return <li>The format is "MMM dd yyyy hh:mma".</li><li>Eg. "Aug 26 2025 12:32am".</li>
     */
    private String parseDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_DATETIME_FORMAT)
                .replace("AM", "am")
                .replace("PM", "pm");
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"deadline | 1 | name | 2025-08-26T13:24" if completed</li>
     *         <li>"deadline | 0 | name | 2025-08-26T13:24" if not completed.</li>
     */
    @Override
    public String toFile() {
        return "deadline | " + super.toFile() + " | " + dueDate;
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"[D][X] name (by: Aug 26 2025 01:24pm)" if completed.</li>
     *         <li>"[D][ ] name (by: Aug 26 2025 01:24pm)" if not completed.</li>
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + parseDateTime(dueDate) + ")";
    }
}
