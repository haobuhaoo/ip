package lax.catalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lax.exception.InvalidCommandException;
import lax.item.notes.Note;

public class NoteListTest {
    private ArrayList<Note> arrayList;
    private Note note1;
    private Note note2;

    @BeforeEach
    public void setup() {
        arrayList = new ArrayList<>();
        note1 = new Note("note 1", LocalDate.parse("2025-09-08"));
        note2 = new Note("note 2", LocalDate.parse("2025-09-08"));
    }

    @Test
    public void parseDate_success() throws InvalidCommandException {
        NoteList n = new NoteList(arrayList);
        assertEquals(LocalDate.parse("2025-09-08"), n.parseDate("08-09-2025"));
    }

    @Test
    public void parseDate_exceptionThrown() {
        try {
            new NoteList(arrayList).parseDate("2025/09/08");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nWrong Date format.\neg. 01-09-2025", e.getMessage());
        }
    }

    @Test
    public void showList_emptyList_success() {
        assertEquals("There is no item in your list.",
                new NoteList(arrayList).showList());

        assertEquals("There is no item in your list on Sep 08 2025.",
                new NoteList(arrayList).showList(LocalDateTime.parse("2025-09-08T00:00"), arrayList));
    }

    @Test
    public void showList_nonEmptyList_success() {
        arrayList.add(note1);
        NoteList n = new NoteList(arrayList);

        assertEquals("Here are the items in your list:\n1. [Sep 08 2025] note 1",
                n.showList());

        assertEquals("Here are the items in your list on Sep 08 2025:\n"
                        + "1. [Sep 08 2025] note 1",
                n.showList(LocalDateTime.parse("2025-09-08T00:00"), arrayList));
    }

    @Test
    public void getDateString_success() {
        NoteList n = new NoteList(arrayList);
        assertEquals(" on Sep 08 2025", n.getDateString(LocalDateTime.parse("2025-09-08T00:00")));
        assertEquals("", n.getDateString(null));
    }

    @Test
    public void labelItem_exceptionThrown() {
        try {
            new NoteList(arrayList).labelItem("1", true);
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNotes cannot be marked.", e.getMessage());
        }
    }

    @Test
    public void addItem_noteAdded_success() throws InvalidCommandException {
        NoteList n = new NoteList(arrayList);
        Note note = n.addItem("test note", "note");
        String dateNow = note.parseDate(LocalDate.now());

        assertEquals("Here are the items in your list:\n1. [" + dateNow + "] test note",
                n.showList());
    }

    @Test
    public void addItem_invalidNoteType_exceptionThrown() {
        NoteList n = new NoteList(arrayList);

        // invalid note type
        try {
            n.addItem("test note", "notNote");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n\"test note\"", e.getMessage());
        }

        // empty note description
        try {
            n.addItem("", "note");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nThe description of a note cannot be empty.", e.getMessage());
        }

        // note already exists
        try {
            arrayList.add(note1);
            n.addItem("note 1", "note");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nThis note already exists.", e.getMessage());
        }
    }

    @Test
    public void deleteItem_notesDeleted_success() throws InvalidCommandException {
        arrayList.add(note1);
        NoteList n = new NoteList(arrayList);
        n.deleteItem("1");

        assertEquals("There is no item in your list.", n.showList());
    }

    @Test
    public void deleteItem_emptyList_success() {
        try {
            new NoteList(arrayList).deleteItem("1");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNo notes to delete.", e.getMessage());
        }
    }

    @Test
    public void deleteItem_invalidCommand_exceptionThrown() {
        arrayList.add(note1);
        NoteList n = new NoteList(arrayList);

        // invalid note number
        try {
            assertEquals(note1, n.deleteItem("x"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\neg. note delete 1", e.getMessage());
        }

        // note number greater than list size
        try {
            assertEquals(note1, n.deleteItem("3"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nInvalid note number.", e.getMessage());
        }
    }

    @Test
    public void findItems_notesFound_success() {
        arrayList.add(note1);
        arrayList.add(note2);

        // notes in notesList
        assertEquals("""
                        Here are the items in your list:
                        1. [Sep 08 2025] note 1
                        2. [Sep 08 2025] note 2""",
                new NoteList(arrayList).findItems("note"));

        // task not in tasklist
        assertEquals("There is no item in your list.",
                new NoteList(arrayList).findItems("notes not in list"));
    }

    @Test
    public void filterItems_notesFiltered_success() throws InvalidCommandException {
        arrayList.add(note1);
        arrayList.add(note2);

        // notes in notesList
        assertEquals("""
                        Here are the items in your list on Sep 08 2025:
                        1. [Sep 08 2025] note 1
                        2. [Sep 08 2025] note 2""",
                new NoteList(arrayList).filterItems("08-09-2025"));

        // notes not in notesList
        assertEquals("There is no item in your list on Jan 01 2050.",
                new NoteList(arrayList).filterItems("01-01-2050"));
    }

    @Test
    public void serialize_success() {
        arrayList.add(note1);
        NoteList n = new NoteList(arrayList);

        assertEquals("2025-09-08 | note 1", n.serialize().get(0));
    }
}
