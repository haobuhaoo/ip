package lax.item.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventTest {
    private Event event;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    public void setup() {
        startDate = LocalDateTime.now().plusHours(2);
        endDate = LocalDateTime.now().plusHours(5);
        event = new Event("Team meeting", startDate, endDate);
    }

    @Test
    public void toFile_success() {
        assertEquals("event | 0 | Team meeting | " + startDate + " | " + endDate, event.toFile());

        event.markTask();
        assertEquals("event | 1 | Team meeting | " + startDate + " | " + endDate, event.toFile());
    }

    @Test
    public void toString_success() {
        assertEquals("[E][ ] Team meeting (from: " + event.parseDateTime(startDate)
                        + " to: " + event.parseDateTime(endDate) + ")",
                event.toString());

        event.markTask();
        assertEquals("[E][X] Team meeting (from: " + event.parseDateTime(startDate)
                        + " to: " + event.parseDateTime(endDate) + ")",
                event.toString());
    }

    @Test
    public void equals_success() {
        Event sameEvent = new Event("Team meeting", startDate, endDate);
        Event differentName = new Event("Submit essay", startDate, endDate);
        Event differentStartDate = new Event("Team meeting", startDate.plusDays(1), endDate);
        Event differentEndDate = new Event("Team meeting", startDate, endDate.plusDays(1));

        assertEquals(event, sameEvent);
        assertNotEquals(event, differentName);
        assertNotEquals(event, differentStartDate);
        assertNotEquals(event, differentEndDate);
    }

    @Test
    public void hashCode_success() {
        Event sameEvent = new Event("Team meeting", startDate, endDate);
        Event differentName = new Event("Submit essay", startDate, endDate);
        Event differentStartDate = new Event("Team meeting", startDate.plusDays(1), endDate);
        Event differentEndDate = new Event("Team meeting", startDate, endDate.plusDays(1));

        assertEquals(event.hashCode(), sameEvent.hashCode());
        assertNotEquals(event.hashCode(), differentName.hashCode());
        assertNotEquals(event.hashCode(), differentStartDate.hashCode());
        assertNotEquals(event.hashCode(), differentEndDate.hashCode());
    }
}
