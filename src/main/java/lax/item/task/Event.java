package lax.item.task;

import java.time.LocalDateTime;
import java.util.Objects;

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
    public Event(String n, boolean c, LocalDateTime s, LocalDateTime e) {
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
     * {@inheritDoc}
     *
     * @return <li>"event | 1 | name | 2025-08-26T13:24 | 2025-08-27T04:56" if completed</li>
     *         <li>"event | 0 | name | 2025-08-26T13:24 | 2025-08-27T04:56" if not completed.</li>
     */
    @Override
    public String toFile() {
        return "event | " + super.toFile() + " | " + startDate + " | " + endDate;
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"[E][X] name (from: Aug 26 2025 01:24pm to: Aug 27 2025 04:56am)" if completed.</li>
     *         <li>"[E][ ] name (from: Aug 26 2025 01:24pm to: Aug 27 2025 04:56am)" if not completed.</li>
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + super.parseDateTime(startDate) + " to: " + super.parseDateTime(endDate) + ")";
    }

    /**
     * Two <code>Event</code> objects are considered equal if they have the same name (ignoring case),
     * start date and end date.
     *
     * @param obj The object to be compared to.
     * @return true if both <code>Event</code> objects have the same name (ignoring case),
     *         start date and end date; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Event other)) {
            return false;
        }

        return super.getName().equalsIgnoreCase(other.getName())
                && this.getStartDate().equals(other.getStartDate())
                && this.getEndDate().equals(other.getEndDate());
    }

    /**
     * Generates a hash code for the <code>Event</code> object based on its name (in lowercase),
     * start date, and end date.
     *
     * @return The hash code of the <code>Event</code> object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName().toLowerCase(), startDate, endDate);
    }
}
