package Lax;

import Lax.Command.AddCommand;
import Lax.Command.Command;
import Lax.Command.DeleteCommand;
import Lax.Command.ExitCommand;
import Lax.Command.FindCommand;
import Lax.Command.HelpCommand;
import Lax.Command.LabelCommand;
import Lax.Command.ListCommand;
import Lax.Exception.InvalidCommandException;

/**
 * Represents the tool used to analyse user inputs.
 */
public class Parser {
    /**
     * List of available commands.
     */
    public enum CommandList {LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, HELP, BYE}

    /**
     * Parses the user input String and outputs the corresponding <code>Command</code>.
     *
     * @param command User input String.
     * @return The corresponding Command.
     * @throws InvalidCommandException If command is not in <code>CommandList</code> or is incomplete.
     */
    public static Command parse(String command) throws InvalidCommandException {
        String[] cmd = command.split(" ", 2);

        try {
            switch (CommandList.valueOf(cmd[0].trim().toUpperCase())) {
            case LIST -> {
                return new ListCommand();
            }
            case MARK, UNMARK -> {
                return new LabelCommand(cmd[1].trim(), cmd[0].trim());
            }
            case TODO, DEADLINE, EVENT -> {
                return new AddCommand(cmd[1].trim(), cmd[0].trim());
            }
            case DELETE -> {
                return new DeleteCommand(cmd[1].trim());
            }
            case FIND -> {
                return new FindCommand(cmd[1].trim());
            }
            case HELP -> {
                return new HelpCommand();
            }
            case BYE -> {
                return new ExitCommand();
            }
            default -> throw new InvalidCommandException("\"" + command + "\"");
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new InvalidCommandException("\"" + command + "\"");
        }
    }
}
