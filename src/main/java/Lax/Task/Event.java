package Lax.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a <code>String</code> name, <code>boolean</code> completed,
 * <code>LocalDateTime</code> startDate and <code>LocalDateTime</code> endDate.
 */
public class Event extends Task {
    /**
     * The start date of the task.
     */
    private final LocalDateTime startDate;

    /**
     * The end date of the task.
     */
    private final LocalDateTime endDate;

    /**
     * Constructs the task with a name, start date and end date, with completed set as false.
     *
     * @param n The name of the task.
     * @param s The start date of the task.
     * @param e The end date of the task.
     */
    public Event(String n, LocalDateTime s, LocalDateTime e) {
        this(n, false, s, e);
    }

    /**
     * Constructs the task with a name, completion status, start date and end date.
     *
     * @param n The name of the task.
     * @param c The completion status of the task.
     * @param s The start date of the task.
     * @param e The end date of the task.
     */
    public Event(String n, Boolean c, LocalDateTime s, LocalDateTime e) {
        super(n, c);
        startDate = s;
        endDate = e;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Converts the dateTime into a <code>String</code> suitable for displaying.
     *
     * @return <li>The format is "MMM dd yyyy hh:mma".</li><li>Eg. "Aug 26 2025 12:32am".</li>
     */
    private String parseDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"event | 1 | name | 2025-08-26T13:24 | 2025-08-27T04:56" if completed</li>
     * <li>"event | 0 | name | 2025-08-26T13:24 | 2025-08-27T04:56" if not completed.</li>
     */
    @Override
    public String toFile() {
        return "event | " + super.toFile() + " | " + startDate + " | " + endDate;
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"[E][X] name (from: Aug 26 2025 01:24pm to: Aug 27 2025 04:56am)" if completed.</li>
     * <li>"[E][ ] name (from: Aug 26 2025 01:24pm to: Aug 27 2025 04:56am)" if not completed.</li>
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + parseDateTime(startDate) + " to: " + parseDateTime(endDate) + ")";
    }
}