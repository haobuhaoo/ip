package lax.task;

/**
 * Represents a task with a <code>String</code> name and <code>boolean</code> completed.
 */
public abstract class Task {
    /**
     * The name of the task which cannot be changed later.
     */
    private final String name;

    /**
     * The completion status of the task.
     */
    private Boolean completed;

    /**
     * Constructs the task with a name and its completion status. By default, all new <code>Task</code>
     * are not completed.
     *
     * @param n The name of the task.
     * @param c The completion status of the task.
     *          <code>true</code> represents completed, <code>false</code> otherwise.
     */
    public Task(String n, Boolean c) {
        name = n;
        completed = c;
    }

    public String getName() {
        return name;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void markTask() {
        completed = true;
    }

    public void unmarkTask() {
        completed = false;
    }

    /**
     * Converts the task into a <code>String</code> suitable for saving into the database file.
     *
     * @return <li>"1 | name" if completed.</li><li>"0 | name" if not completed.</li>
     */
    public String toFile() {
        return (completed ? "1" : "0") + " | " + name;
    }

    /**
     * Converts the task into a <code>String</code> suitable for displaying.
     *
     * @return <li>"[X] name" if completed.</li><li>"[ ] name" if not completed.</li>
     */
    public String toString() {
        return "[" + (completed ? "X" : " ") + "] " + name;
    }
}
