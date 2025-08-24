public abstract class Task {
    private final String name;
    private Boolean completed;

    public Task(String n, Boolean c) {
        name = n;
        completed = c;
    }

    public String getName() { return name; }

    public Boolean isCompleted() { return completed; }

    public void markTask() { completed = true; }

    public void unmarkTask() { completed = false; }

    public String toFile() { return (completed ? "1" : "0") + " | " + name; }

    public String toString() {
        return "[" + (completed ? "X" : " ") + "] " + name;
    }
}