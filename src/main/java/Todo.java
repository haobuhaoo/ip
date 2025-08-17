public class Todo extends Task {
    public Todo(String n) {
        super(n);
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}