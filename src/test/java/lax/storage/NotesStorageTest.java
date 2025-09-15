package lax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lax.item.notes.Note;

public class NotesStorageTest {
    private NotesStorage notesStorage;

    @BeforeEach
    public void setup() {
        notesStorage = new NotesStorage("./data/notes.txt");
    }

    @Test
    public void createNote_validLine_success() throws DateTimeParseException {
        Note note = notesStorage.createNote("2025-09-15 | Meeting notes");
        Note sameNote = new Note("Meeting notes", LocalDate.of(2025, 9, 15));
        assertEquals(note.toString(), sameNote.toString());
    }

    @Test
    public void createNote_invalidLine_success() throws DateTimeParseException {
        Note note = notesStorage.createNote(" | Meeting notes");
        assertNull(note);
    }

    @Test
    public void parseLine_invalidDateTimeFormat_success() {
        Note note = notesStorage.parseLine("2025/09/15 | Meeting notes");
        assertNull(note);
    }
}
