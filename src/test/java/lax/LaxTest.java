package lax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class LaxTest {
    @Test
    public void getResponse_success() throws IOException {
        Lax lax = new Lax("./data/task.txt", "./data/notes.txt");
        assertEquals("Bye zzzzz...", lax.getResponse("bye"));

        // empty command
        assertEquals("zzzzz...\nInvalid command.\nEmpty command", lax.getResponse(""));

        // shows invalid command error message
        assertEquals("zzzzz...\nInvalid command.\n\"lax\"", lax.getResponse("lax"));

        // shows invalid datetime format error message
        assertEquals("zzzzz...\nWrong DateTime format.\neg. 23-08-2025 1800",
                lax.getResponse("task deadline return book /by 2024-10-10 1800"));
    }
}
