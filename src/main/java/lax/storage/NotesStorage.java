package lax.storage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import lax.catalogue.NoteList;
import lax.item.notes.Note;

/**
 * Represents the database storage for notes.
 */
public class NotesStorage extends Storage {
    /**
     * The maximum number of notes that can be stored.
     */
    private static final int MAX_NOTES = 100;

    /**
     * Constructs a <code>NotesStorage</code> object with the specified file path.
     */
    public NotesStorage(String filePath) {
        super(filePath);
    }

    /**
     * Creates the specific <code>Note</code> based on what is written in the file.
     *
     * @param line The line that is read by the scanner.
     * @return The <code>Note</code> object created.
     * @throws DateTimeParseException If the format for date is wrong.
     */
    private Note createNote(String line) throws DateTimeParseException {
        String[] data = line.trim().split("\\|");
        assert data.length == 2 : "notes in the file should have the date and description";

        if (data[0].trim().isEmpty() || data[1].trim().isEmpty()) {
            super.handleCorruptedItem(line);
            return null;
        }

        return new Note(data[1].trim(), LocalDate.parse(data[0].trim()));
    }

    /**
     * Parses the line into a <code>Note</code> object.
     *
     * @param line The line to parse.
     * @return The created <code>Note</code>.
     */
    private Note parseLine(String line) {
        try {
            return createNote(line);
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            super.handleCorruptedItem(line);
            return null;
        }
    }

    /**
     * Loads the file specified in filePath by reading every line of the file and converting it into a
     * <code>Note</code>, which then adds it into a notesList and is returned.
     *
     * @return The list of notes stored previously or a new empty list.
     * @throws IOException If there is an error reading the file.
     */
    public NoteList loadNotes() throws IOException {
        ArrayList<Note> notesList = super.load(new ArrayList<>(MAX_NOTES), this::parseLine);
        return new NoteList(notesList);
    }
}
