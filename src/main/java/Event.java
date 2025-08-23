public class Event extends Task {
    private final String startDate;
    private final String endDate;

    public Event(String n, String s, String e) {
        this(n, false, s, e);
    }

    public Event(String n, Boolean c, String s, String e) {
        super(n, c);
        startDate = s;
        endDate = e;
    }

    @Override
    public String toFile() { return "event | " + super.toFile() + " | " + startDate + " | " + endDate; }

    @Override
    public String toString() { return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")"; }
}