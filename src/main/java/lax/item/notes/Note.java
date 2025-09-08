package lax.item.notes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lax.item.Item;

/**
 * Represents a <code>Note</code> with a <code>String</code> description and a <code>LocalDate</code>
 * date that it was recorded.
 */
public class Note implements Item {
    /**
     * The format of the date that the chatbot outputs.
     */
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * The description of the note.
     */
    private final String description;

    /**
     * The date of the note.
     */
    private final LocalDate date;

    /**
     * Constructs the note with the description that the user wants to store, with the date being set
     * as the current date of execution.
     *
     * @param d The note description.
     */
    public Note(String d) {
        this(d, LocalDate.now());
    }

    /**
     * Constructs the note with the description and date.
     *
     * @param d The note description.
     * @param t The date the note was recorded.
     */
    public Note(String d, LocalDate t) {
        description = d;
        date = t;
    }

    /**
     * Converts the date into a <code>String</code> suitable for displaying.
     *
     * @return <li>The format is "MMM dd yyyy".</li><li>Eg. "Aug 26 2025".</li>
     */
    private String parseDate(LocalDate date) {
        return date.format(OUTPUT_DATE_FORMAT);
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Converts the note into a <code>String</code> for storing in file.
     */
    @Override
    public String toFile() {
        return date + " | " + description;
    }

    /**
     * Converts the note into a <code>String</code> for displaying.
     *
     * @return "[MMM dd yyyy] description".
     */
    @Override
    public String toString() {
        return "[" + parseDate(date) + "] " + description;
    }
}
