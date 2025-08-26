package Lax.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Lax.Exception.InvalidCommandException;

public class TaskListTest {
    ArrayList<Task> arrayList;
    Todo todo;
    Deadline deadline;
    Event event;

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
        assertEquals("There is no task in your list.",
                new TaskList(arrayList).showList(null));

        assertEquals("There is no task in your list on Aug 25 2025 12:00am.",
                new TaskList(arrayList).showList(LocalDateTime.parse("2025-08-25T00:00")));
    }

    @Test
    public void showList_nonEmptyList_success() {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        assertEquals("Here are the tasks in your list:\n1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                t.showList(null));

        assertEquals("Here are the tasks in your list on Aug 25 2025 12:00am:\n"
                        + "1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                t.showList(LocalDateTime.parse("2025-08-25T00:00")));
    }

    @Test
    public void labelTask_correctLabel_success() throws InvalidCommandException {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        assertTrue(t.labelTask("1", true).isCompleted());
        assertFalse(t.labelTask("1", false).isCompleted());
    }

    @Test
    public void labelTask_emptyList_exceptionThrown() {
        try {
            assertTrue(new TaskList(arrayList).labelTask("1", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNo task to be marked", e.getMessage());
        }
    }

    @Test
    public void labelTask_invalidCommand_exceptionThrown() {
        arrayList.add(deadline);
        TaskList t = new TaskList(arrayList);

        // invalid task number
        try {
            assertTrue(t.labelTask("x", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\neg. mark 1\neg. unmark 1", e.getMessage());
        }

        // task number greater than list size
        try {
            assertTrue(t.labelTask("3", true).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nInvalid task number.", e.getMessage());
        }

        // task already unmarked
        try {
            assertTrue(t.labelTask("1", false).isCompleted());
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nTask \"return book\" is already marked as not done",
                    e.getMessage());
        }
    }


    @Test
    public void addTask_taskAdded_success() throws InvalidCommandException {
        TaskList t = new TaskList(arrayList);
        t.addTask("read book", "todo");

        assertEquals("Here are the tasks in your list:\n1. [T][ ] read book",
                t.showList(null));
    }

    @Test
    public void addTask_invalidTask_exceptionThrown() {
        try {
            TaskList t = new TaskList(arrayList);
            t.addTask("project meeting", "event");
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n"
                            + "eg. event project meeting /from 23-08-2025 1400 /to 23-08-2025 1600",
                    e.getMessage());
        }
    }

    @Test
    public void addTask_invalidDateTime_exceptionThrown() throws InvalidCommandException {
        try {
            TaskList t = new TaskList(arrayList);
            t.addTask("project meeting /from 23/08/2025 1400 /to 2025-08-23 16:00", "event");
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '23/08/2025 1400' could not be parsed at index 2",
                    e.getMessage());
        }
    }

    @Test
    public void deleteTask_taskDeleted_success() throws InvalidCommandException {
        arrayList.add(todo);
        TaskList t = new TaskList(arrayList);
        t.deleteTask("1");

        assertEquals("There is no task in your list.", t.showList(null));
    }

    @Test
    public void deleteTask_emptyList_success() {
        try {
            assertEquals(todo, new TaskList(arrayList).deleteTask("1"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nNo task to delete.", e.getMessage());
        }
    }

    @Test
    public void deleteTask_invalidCommand_exceptionThrown() {
        arrayList.add(todo);
        TaskList t = new TaskList(arrayList);

        // invalid task number
        try {
            assertEquals(todo, t.deleteTask("x"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\neg. delete 1", e.getMessage());
        }

        // task number greater than list size
        try {
            assertEquals(todo, t.deleteTask("3"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\nInvalid task number.", e.getMessage());
        }
    }

    @Test
    public void findTask_taskFound_success() {
        arrayList.add(todo);
        arrayList.add(deadline);
        arrayList.add(event);

        // task in tasklist
        assertEquals("Here are the tasks in your list:\n" + "1. [T][ ] read book\n"
                        + "2. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                new TaskList(arrayList).findTask("book"));

        // task not in tasklist
        assertEquals("There is no task in your list.",
                new TaskList(arrayList).findTask("task not in list"));
    }

    @Test
    public void filterTask_taskFiltered_success() {
        arrayList.add(todo);
        arrayList.add(deadline);
        arrayList.add(event);

        // task in tasklist
        assertEquals("Here are the tasks in your list on Aug 23 2025 06:00pm:\n"
                        + "1. [D][ ] return book (by: Aug 25 2025 01:50pm)",
                new TaskList(arrayList).filterTask("23-08-2025 1800"));

        // task not in tasklist
        assertEquals("There is no task in your list on Jan 01 2050 12:00am.",
                new TaskList(arrayList).filterTask("01-01-2050 0000"));
    }
}