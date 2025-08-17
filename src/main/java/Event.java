public class Event extends Task {
    private final String startDate;
    private final String endDate;

    public Event(String n, String s, String e) {
        super(n);
        startDate = s;
        endDate = e;
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }
}