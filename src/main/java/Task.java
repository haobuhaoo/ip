abstract class Task {
    private final String name;
    private Boolean completed;

    public Task(String n) {
        name = n;
        completed = false;
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

    public String toString() {
        return "[" + (completed ? "X" : " ") + "] " + name;
    }
}