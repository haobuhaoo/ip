package lax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import lax.command.AddCommand;
import lax.command.DeleteCommand;
import lax.command.ExitCommand;
import lax.command.FilterCommand;
import lax.command.FindCommand;
import lax.command.HelpCommand;
import lax.command.LabelCommand;
import lax.command.ListCommand;
import lax.exception.InvalidCommandException;

public class ParserTest {
    @Test
    public void parse_success() throws InvalidCommandException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));

        assertInstanceOf(LabelCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(LabelCommand.class, Parser.parse("unmark 1"));

        assertInstanceOf(AddCommand.class, Parser.parse("todo add"));
        assertInstanceOf(AddCommand.class, Parser.parse("deadline return book /by 23-08-2025 1800"));
        assertInstanceOf(AddCommand.class,
                Parser.parse("event project meeting /from 23-08-2025 1400 /to 23-08-2025 1600"));

        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));

        assertInstanceOf(FindCommand.class, Parser.parse("find book"));

        assertInstanceOf(FilterCommand.class, Parser.parse("filter 23-08-2025 1400"));

        assertInstanceOf(HelpCommand.class, Parser.parse("help"));

        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
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
            assertEquals(new HelpCommand(), Parser.parse("delete"));
            fail();
        } catch (InvalidCommandException e) {
            assertEquals("Invalid command.\n\"delete\"", e.getMessage());
        }
    }
}
