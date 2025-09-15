package lax.item.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodoTest {
    private Todo todo;

    @BeforeEach
    public void setup() {
        todo = new Todo("Test Todo");
    }

    @Test
    public void toFile_success() {
        assertEquals("todo | 0 | Test Todo", todo.toFile());

        todo.markTask();
        assertEquals("todo | 1 | Test Todo", todo.toFile());
    }

    @Test
    public void toString_success() {
        assertEquals("[T][ ] Test Todo", todo.toString());

        todo.markTask();
        assertEquals("[T][X] Test Todo", todo.toString());
    }

    @Test
    public void equals_success() {
        Todo sameTodo = new Todo("test todo");
        Todo differentTodo = new Todo("Different Todo");

        assertEquals(todo, sameTodo);
        assertNotEquals(todo, differentTodo);
    }

    @Test
    public void hashCode_success() {
        Todo sameTodo = new Todo("test todo");
        Todo differentTodo = new Todo("Different Todo");

        assertEquals(todo.hashCode(), sameTodo.hashCode());
        assertNotEquals(todo.hashCode(), differentTodo.hashCode());
    }
}
