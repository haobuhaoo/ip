package lax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lax.item.task.Deadline;
import lax.item.task.Event;
import lax.item.task.Task;
import lax.item.task.Todo;

public class TaskStorageTest {
    private TaskStorage taskStorage;

    @BeforeEach
    public void setup() {
        taskStorage = new TaskStorage("./data/task.txt");
    }

    @Test
    public void createTask_validLine_success() {
        Task todo = taskStorage.createTask("todo | 1 | Read book");
        Task deadline = taskStorage.createTask("deadline | 0 | Submit report | 2025-08-26T13:24");
        Task event = taskStorage.createTask("event | 1 | Conference | 2025-08-26T13:24 | 2025-08-27T04:56");

        Todo sameTodo = new Todo("Read book", true);
        Deadline sameDeadline = new Deadline("Submit report", false, LocalDateTime.parse("2025-08-26T13:24"));
        Event sameEvent = new Event("Conference", true, LocalDateTime.parse("2025-08-26T13:24"),
                LocalDateTime.parse("2025-08-27T04:56"));

        assertEquals(sameTodo, todo);
        assertEquals(sameDeadline, deadline);
        assertEquals(sameEvent, event);
    }

    @Test
    public void createTask_invalidLine_handleCorruptedItem() {
        Task missingDeadlineFields = taskStorage.createTask("deadline | 1 | Submit report |");
        Task missingEventField = taskStorage.createTask("event | 1 | Conference | | 2025-08-26T13:24");

        assertNull(missingEventField);
        assertNull(missingDeadlineFields);
    }

    @Test
    public void parseLine_invalidDateTimeFormat_success() {
        Task invalidDateTime = taskStorage.parseLine("deadline | 0 | Submit report | 2025-08-26 13:24");
        assertNull(invalidDateTime);
    }
}
