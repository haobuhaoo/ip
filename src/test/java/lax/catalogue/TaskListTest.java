package lax.catalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lax.exception.InvalidCommandException;
import lax.item.task.Deadline;
import lax.item.task.Event;
import lax.item.task.Task;
import lax.item.task.Todo;

public class TaskListTest {
    private ArrayList<Task> arrayList;
    private Todo todo;
    private Deadline deadline;
    private Event event;

    @BeforeEach
    void setup() {
        arrayList = new ArrayList<>();
        todo = new Todo("read book");
        deadline = new Deadline("return book", LocalDateTime.parse("2025-08-25T13:50"));
        event = new Event("project meeting", LocalDateTime.parse("2025-08-26T14:00"),
                LocalDateTime.parse("2025-08-26T16:00"));
    }

    @Test
    public void showList_emptyList_success() {
        assertEquals("There is no item in your list.",
                new TaskList(arrayList).showList());

        assertEquals("There is no item in your list on Aug 25 2025 12:00am.",
                new TaskList(arrayList).showList(LocalDateTime.parse("2025-08-25T00:00"), arrayList));
    }

    @Test
    public void showList_nonEmptyList_success() {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        assertEquals("Here are the items in your list:\n1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                t.showList());

        assertEquals("Here are the items in your list on Aug 25 2025 12:00am:\n"
                        + "1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                t.showList(LocalDateTime.parse("2025-08-25T00:00"), arrayList));
    }

    @Test
    public void labelItem_correctLabel_success() throws InvalidCommandException {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);
        assertTrue(t.labelItem("1", true).isCompleted());
        assertFalse(t.labelItem("1", false).isCompleted());
    }

    @Test
    public void labelItem_emptyList_exceptionThrown() {
        try {
            assertTrue(new TaskList(arrayList).labelItem("1", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNo task to be marked", e.getMessage());
        }
    }

    @Test
    public void labelItem_invalidCommand_exceptionThrown() {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        // invalid task number
        try {
            assertTrue(t.labelItem("x", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\neg. task mark 1\neg. task unmark 1", e.getMessage());
        }

        // task number greater than list size
        try {
            assertTrue(t.labelItem("3", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nInvalid task number.", e.getMessage());
        }

        // task already unmarked
        try {
            assertTrue(t.labelItem("1", false).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nTask \"return book\" is already marked as not done",
                    e.getMessage());
        }
    }

    @Test
    public void addItem_taskAdded_success() throws InvalidCommandException {
        TaskList t = new TaskList(arrayList);
        t.addItem("read book", "todo");

        assertEquals("Here are the items in your list:\n1. [T][ ] read book",
                t.showList());
    }

    @Test
    public void addItem_invalidTask_exceptionThrown() {
        try {
            TaskList t = new TaskList(arrayList);
            t.addItem("project meeting", "event");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n"
                            + "eg. task event project meeting /from 23-08-2025 1400 /to 23-08-2025 1600",
                    e.getMessage());
        }
    }

    @Test
    public void addItem_invalidDateTime_exceptionThrown() throws InvalidCommandException {
        try {
            TaskList t = new TaskList(arrayList);
            t.addItem("project meeting /from 23/08/2025 1400 /to 2025-08-23 16:00", "event");
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '23/08/2025 1400' could not be parsed at index 2",
                    e.getMessage());
        }
    }

    @Test
    public void deleteItem_taskDeleted_success() throws InvalidCommandException {
        arrayList.add(todo);
        TaskList t = new TaskList(arrayList);
        t.deleteItem("1");

        assertEquals("There is no item in your list.", t.showList());
    }

    @Test
    public void deleteItem_emptyList_success() {
        try {
            new TaskList(arrayList).deleteItem("1");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNo task to delete.", e.getMessage());
        }
    }

    @Test
    public void deleteItem_invalidCommand_exceptionThrown() {
        arrayList.add(todo);
        TaskList t = new TaskList(arrayList);

        // invalid task number
        try {
            assertEquals(todo, t.deleteItem("x"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\neg. task delete 1", e.getMessage());
        }

        // task number greater than list size
        try {
            assertEquals(todo, t.deleteItem("3"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nInvalid task number.", e.getMessage());
        }
    }

    @Test
    public void findItems_taskFound_success() {
        arrayList.add(todo);
        arrayList.add(deadline);
        arrayList.add(event);

        // task in taskList
        assertEquals("""
                        Here are the items in your list:
                        1. [T][ ] read book
                        2. [D][ ] return book (by: Aug 25 2025 01:50pm)""",
                new TaskList(arrayList).findItems("book"));

        // task not in taskList
        assertEquals("There is no item in your list.",
                new TaskList(arrayList).findItems("task not in list"));
    }

    @Test
    public void filterItems_taskFiltered_success() throws InvalidCommandException {
        arrayList.add(todo);
        arrayList.add(deadline);
        arrayList.add(event);

        // task in taskList
        assertEquals("Here are the items in your list on Aug 23 2025 06:00pm:\n"
                        + "1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                new TaskList(arrayList).filterItems("23-08-2025 1800"));

        // task not in taskList
        assertEquals("There is no item in your list on Jan 01 2050 12:00am.",
                new TaskList(arrayList).filterItems("01-01-2050 0000"));
    }

    @Test
    public void serialize_success() {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        assertEquals("deadline | 0 | return book | 2025-08-25T13:50", t.serialize().get(0));
    }
}
