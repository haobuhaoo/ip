package lax.item.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeadlineTest {
    private Deadline deadline;
    private LocalDateTime dueDate;

    @BeforeEach
    public void setup() {
        dueDate = LocalDateTime.now().plusHours(2);
        deadline = new Deadline("Submit report", dueDate);
    }

    @Test
    public void toFile_success() {
        assertEquals("deadline | 0 | Submit report | " + dueDate, deadline.toFile());

        deadline.markTask();
        assertEquals("deadline | 1 | Submit report | " + dueDate, deadline.toFile());
    }

    @Test
    public void toString_success() {
        assertEquals("[D][ ] Submit report (by: " + deadline.parseDateTime(dueDate) + ")",
                deadline.toString());

        deadline.markTask();
        assertEquals("[D][X] Submit report (by: " + deadline.parseDateTime(dueDate) + ")",
                deadline.toString());
    }

    @Test
    public void equals_success() {
        Deadline sameDeadline = new Deadline("Submit report", dueDate);
        Deadline differentName = new Deadline("Submit essay", dueDate);
        Deadline differentDate = new Deadline("Submit report", dueDate.plusDays(1));

        assertEquals(deadline, sameDeadline);
        assertNotEquals(deadline, differentName);
        assertNotEquals(deadline, differentDate);
    }

    @Test
    public void hashCode_success() {
        Deadline sameDeadline = new Deadline("Submit report", dueDate);
        Deadline differentName = new Deadline("Submit essay", dueDate);
        Deadline differentDate = new Deadline("Submit report", dueDate.plusDays(1));

        assertEquals(deadline.hashCode(), sameDeadline.hashCode());
        assertNotEquals(deadline.hashCode(), differentName.hashCode());
        assertNotEquals(deadline.hashCode(), differentDate.hashCode());
    }
}
