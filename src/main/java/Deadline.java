public class Deadline extends Task {
    private final String dueDate;

    public Deadline(String n, String d) {
        super(n);
        dueDate = d;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate + ")";
    }
}