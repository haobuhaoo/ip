public class Todo extends Task {
    public Todo(String n) {
        this(n, false);
    }

    public Todo(String n, Boolean c) { super(n, c); }

    @Override
    public String toFile() { return "todo | " + super.toFile(); }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}