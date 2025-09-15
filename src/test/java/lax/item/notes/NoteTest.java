package lax.item.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteTest {
    private LocalDate today;
    private Note note;

    @BeforeEach
    public void setup() {
        today = LocalDate.now();
        note = new Note("Test note", today);
    }

    @Test
    public void toFile_success() {
        assertEquals(today.toString() + " | Test note", note.toFile());
    }

    @Test
    public void toString_success() {
        assertEquals("[" + note.parseDate(today) + "] Test note", note.toString());
    }
}
