package lax.catalogue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import lax.exception.InvalidCommandException;
import lax.item.notes.Note;

/**
 * Represents the list of notes stored in the database file.
 */
public class NoteList implements Catalogue {
    /**
     * The format of the date that user inputs.
     */
    private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

    /**
     * The format of the date that the chatbot outputs.
     */
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * The only type of note.
     */
    private static final String NOTE_TYPE = "NOTE";

    /**
     * The arraylist to store the list of notes.
     */
    private final ArrayList<Note> notesList;

    /**
     * Constructs the list of notes with an arraylist.
     *
     * @param n The arraylist of notes.
     */
    public NoteList(ArrayList<Note> n) {
        notesList = n;
    }

    public int size() {
        return notesList.size();
    }

    /**
     * Parses the date of the pattern of "dd-MM-yyyy" into a <code>LocalDate</code> object.
     *
     * @return <li>The format is "yyyy-MM-ddT00:00".</li><li>Eg. "2025-08-26T00:00".</li>
     * @throws DateTimeParseException If the date cannot be parsed.
     */
    public LocalDate parseDate(String dateTime) throws InvalidCommandException {
        try {
            return LocalDate.parse(dateTime, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("Wrong Date format.\neg. 01-09-2025");
        }
    }

    /**
     * Converts the notesList into a <code>String</code> for displaying.
     */
    @Override
    public String showList() {
        return Catalogue.super.showList(null, notesList);
    }

    /**
     * Parses the dateTime into a date string of format "MMM dd yyyy".
     */
    @Override
    public String getDateString(LocalDateTime dateTime) {
        return dateTime == null
                ? ""
                : " on " + dateTime.toLocalDate().format(OUTPUT_DATE_FORMAT);
    }

    /**
     * Throws <code>InvalidCommandException</code> since <code>Note</code> cannot be marked.
     */
    @Override
    public Note labelItem(String s, boolean b) throws InvalidCommandException {
        throw new InvalidCommandException("Notes cannot be marked.");
    }

    /**
     * Adds the new <code>Note</code> into the notesList.
     *
     * @param note The description of the note.
     * @param type The type of note.
     * @return The new <code>Note</code> that is added.
     * @throws InvalidCommandException If input is of a wrong format.
     */
    @Override
    public Note addItem(String note, String type) throws InvalidCommandException {
        if (type.trim().equalsIgnoreCase(NOTE_TYPE)) {
            Note newNote = new Note(note);
            notesList.add(newNote);
            return newNote;
        } else {
            throw new InvalidCommandException("\"" + note + "\"");
        }
    }

    /**
     * Deletes the <code>Note</code> from the notesList.
     *
     * @param index The note index.
     * @return The deleted note.
     * @throws InvalidCommandException If notesList is empty or invalid note number.
     */
    @Override
    public Note deleteItem(String index) throws InvalidCommandException {
        if (notesList.isEmpty()) {
            throw new InvalidCommandException("No notes to delete.");
        }

        try {
            return notesList.remove(Integer.parseInt(index) - 1);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. note delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid note number.");
        }
    }

    /**
     * Finds all <code>Note</code> in the notesList by the keyword in the description.
     *
     * @param desc The keyword to find by.
     * @return A <code>String</code> representation of the filtered notesList.
     */
    @Override
    public String findItems(String desc) {
        ArrayList<Note> newNote = notesList.stream()
                .filter(n -> n.getDescription().contains(desc))
                .collect(Collectors.toCollection(ArrayList::new));
        return Catalogue.super.showList(null, newNote);
    }

    /**
     * Filters the notesList for notes created on the specific dateTime.
     * <p>
     * If the dateTime is of wrong format, it throws a <code>DateTimeParseException</code>.
     *
     * @param dt The dateTime to filter by.
     * @return A <code>String</code> representation of the filtered notesList.
     */
    @Override
    public String filterItems(String dt) throws InvalidCommandException {
        LocalDate date = parseDate(dt);
        ArrayList<Note> newNote = notesList.stream()
                .filter(n -> n.getDate().isEqual(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return Catalogue.super.showList(date.atStartOfDay(), newNote);
    }

    /**
     * Serialize the current notesList into the correct format.
     *
     * @return An <code>ArrayList</code> of string representation of notes in the correct format.
     */
    public ArrayList<String> serialize() {
        return notesList.stream()
                .map(Note::toFile)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
