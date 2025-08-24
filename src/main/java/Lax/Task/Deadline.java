package Lax.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime dueDate;

    public Deadline(String n, LocalDateTime d) {
        this(n, false, d);
    }

    public Deadline(String n, Boolean c, LocalDateTime d) {
        super(n, c);
        dueDate = d;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    private String parseDateTime(LocalDateTime dueDate) {
        return dueDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
    }

    @Override
    public String toFile() {
        return "deadline | " + super.toFile() + " | " + dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + parseDateTime(dueDate) + ")";
    }
}