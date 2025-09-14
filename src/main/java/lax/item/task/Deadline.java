package lax.item.task;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Deadline task with a <code>String</code> name, <code>boolean</code> completed and
 * <code>LocalDateTime</code> dueDate.
 */
public class Deadline extends Task {
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
    public Deadline(String n, boolean c, LocalDateTime d) {
        super(n, c);
        dueDate = d;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
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
        return "[D]" + super.toString() + " (by: " + super.parseDateTime(dueDate) + ")";
    }

    /**
     * Two <code>Deadline</code> objects are considered equal if they have the same name (ignoring case)
     * and the same due date.
     *
     * @param obj The object to be compared to.
     * @return true if both <code>Deadline</code> objects have the same name (ignoring case)
     *         and the same due date; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Deadline other)) {
            return false;
        }

        return super.getName().equalsIgnoreCase(other.getName()) && this.getDueDate().equals(other.getDueDate());
    }

    /**
     * Generates a hash code based on the name (in lowercase) and due date of the task.
     *
     * @return The hash code of the task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName().toLowerCase(), dueDate);
    }
}
