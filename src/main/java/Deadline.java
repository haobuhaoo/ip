public class Deadline extends Task {
    private final String dueDate;

    public Deadline(String n, String d) {
        this(n, false, d);
    }

    public Deadline(String n, Boolean c, String d) {
        super(n, c);
        dueDate = d;
    }

    @Override
    public String toFile() { return "deadline | " + super.toFile() + " | " + dueDate; }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate + ")";
    }
}