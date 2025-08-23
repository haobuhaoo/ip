import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Event(String n, LocalDateTime s, LocalDateTime e) {
        this(n, false, s, e);
    }

    public Event(String n, Boolean c, LocalDateTime s, LocalDateTime e) {
        super(n, c);
        startDate = s;
        endDate = e;
    }

    public LocalDateTime getStartDate() { return startDate; }

    public LocalDateTime getEndDate() { return endDate; }

    private String parseDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
    }

    @Override
    public String toFile() {
        return "event | " + super.toFile() + " | " + startDate + " | " + endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + parseDateTime(startDate) + " to: " + parseDateTime(endDate) + ")";
    }
}