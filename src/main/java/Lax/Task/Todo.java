package Lax.Task;

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
    public Todo(String n, Boolean c) {
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
}