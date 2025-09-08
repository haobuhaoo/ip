package lax.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import lax.exception.InvalidCommandException;

public class ParserTest {
    @Test
    public void parse_success() throws InvalidCommandException {
        // general command
        assertInstanceOf(HelpCommand.class, Parser.parse("help"));
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));

        // task command
        assertInstanceOf(ListCommand.class, Parser.parse("task list"));

        assertInstanceOf(LabelCommand.class, Parser.parse("task mark 1"));
        assertInstanceOf(LabelCommand.class, Parser.parse("task unmark 1"));

        assertInstanceOf(AddCommand.class, Parser.parse("task todo read book"));
        assertInstanceOf(AddCommand.class, Parser.parse("task deadline return book /by 23-08-2025 1800"));
        assertInstanceOf(AddCommand.class,
                Parser.parse("task event project meeting /from 23-08-2025 1400 /to 23-08-2025 1600"));

        assertInstanceOf(DeleteCommand.class, Parser.parse("task delete 1"));

        assertInstanceOf(FindCommand.class, Parser.parse("task find book"));

        assertInstanceOf(FilterCommand.class, Parser.parse("task filter 23-08-2025 1400"));

        // note command
        assertInstanceOf(ListCommand.class, Parser.parse("note list"));

        assertInstanceOf(AddCommand.class, Parser.parse("note add the new book I'm reading is interesting"));

        assertInstanceOf(DeleteCommand.class, Parser.parse("note delete 1"));

        assertInstanceOf(FindCommand.class, Parser.parse("note find book"));

        assertInstanceOf(FilterCommand.class, Parser.parse("note filter 23-08-2025"));
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        // command does not exist
        try {
            assertEquals(new HelpCommand(), Parser.parse("test"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n\"test\"", e.getMessage());
        }

        // command missing some details
        try {
            assertEquals(new HelpCommand(), Parser.parse("task delete"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n\"task delete\"", e.getMessage());
        }
    }
}
