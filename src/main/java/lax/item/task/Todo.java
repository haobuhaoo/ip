package lax.item.task;

import java.util.Objects;

/**
 * Represents a Todo task with a <code>String</code> name and <code>boolean</code> completed.
 */
public class Todo extends Task {
    /**
     * Constructs the task with a name and completed is set as false.
     *
     * @param n The name of the task.
     */
    public Todo(String n) {
        this(n, false);
    }

    /**
     * Constructs the task with a name and its completion status.
     *
     * @param n The name of the task.
     * @param c The completion status of the task.
     */
    public Todo(String n, boolean c) {
        super(n, c);
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"todo | 1 | name" if completed.</li><li>"todo | 0 | name" if not completed.</li>
     */
    @Override
    public String toFile() {
        return "todo | " + super.toFile();
    }

    /**
     * {@inheritDoc}
     *
     * @return <li>"[T][X] name" if completed.</li><li>"[T][ ] name" if not completed.</li>
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Two <code>Todo</code> objects are considered equal if they have the same name, ignoring case.
     *
     * @param obj The object to be compared to.
     * @return true if both <code>Todo</code> objects have the same name, ignoring case; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Todo other)) {
            return false;
        }

        return super.getName().equalsIgnoreCase(other.getName());
    }

    /**
     * The hash code is based on the name of the <code>Todo</code>, ignoring case.
     *
     * @return The hash code of the name in lowercase.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName().toLowerCase());
    }
}
