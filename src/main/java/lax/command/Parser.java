package lax.command;

import lax.exception.InvalidCommandException;

/**
 * Represents the tool used to analyse user inputs.
 */
public class Parser {
    /**
     * List of item prefix.
     */
    public enum Prefix { TASK, NOTE, HELP, BYE }

    /**
     * List of commands for task handling. It has the prefix TASK.
     */
    public enum TaskCommandList { LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, FILTER }

    /**
     * List of commands for note-taking. It has the prefix NOTE.
     */
    public enum NoteCommandList { LIST, ADD, DELETE, FIND, FILTER }

    /**
     * Splits the input command string input into task, note or general command and parses them into its
     * corresponding command.
     *
     * @throws InvalidCommandException If command prefix is not in <code>Prefix</code> or
     *                                 <code>GeneralCommandList</code>, or is incomplete.
     */
    public static Command parse(String command) throws InvalidCommandException {
        String[] cmd = command.split(" ", 2);
        assert cmd.length <= 2 : "input should be split into prefix and command details";

        String invalidCmd = "\"" + command + "\"";
        try {
            switch (Prefix.valueOf(cmd[0].trim().toUpperCase())) {
            case TASK -> {
                return parseTaskCmd(cmd[1].trim());
            }
            case NOTE -> {
                return toggleNoteCommand(parseNoteCmd(cmd[1].trim()));
            }
            case HELP -> {
                if (cmd.length != 1) {
                    throw new InvalidCommandException(invalidCmd);
                }
                return new HelpCommand();
            }
            case BYE -> {
                if (cmd.length != 1) {
                    throw new InvalidCommandException(invalidCmd);
                }
                return new ExitCommand();
            }
            default -> throw new InvalidCommandException(invalidCmd);
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new InvalidCommandException(invalidCmd);
        }
    }

    /**
     * Parses the task command string and outputs the corresponding <code>Command</code>.
     *
     * @param command The rest of the command string after removing the prefix "task".
     * @return The corresponding <code>Command</code> object.
     * @throws InvalidCommandException If command type is not in <code>TaskCommandList</code> or is incomplete.
     */
    public static Command parseTaskCmd(String command) throws InvalidCommandException {
        String[] cmd = command.split(" ", 2);
        assert cmd.length <= 2 : "task command should be split into type and task details";

        String invalidCmd = "\"task " + command + "\"";
        try {
            switch (TaskCommandList.valueOf(cmd[0].trim().toUpperCase())) {
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
            case FILTER -> {
                return new FilterCommand(cmd[1].trim());
            }
            default -> throw new InvalidCommandException(invalidCmd);
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new InvalidCommandException(invalidCmd);
        }
    }

    /**
     * Parses note command string and return the corresponding <code>Command</code>.
     *
     * @param command The rest of the command string after removing the prefix "note".
     * @return The corresponding <code>Command</code> object.
     * @throws InvalidCommandException If command type is not in <code>NoteCommandList</code> or is incomplete.
     */
    private static Command parseNoteCmd(String command) throws InvalidCommandException {
        String[] cmd = command.split(" ", 2);
        assert cmd.length <= 2 : "note command should split into type and note details";

        String prefix = "note";
        String invalidCmd = "\"note " + command + "\"";
        try {
            switch (NoteCommandList.valueOf(cmd[0].trim().toUpperCase())) {
            case LIST -> {
                return new ListCommand();
            }
            case ADD -> {
                return new AddCommand(cmd[1].trim(), prefix);
            }
            case DELETE -> {
                return new DeleteCommand(cmd[1].trim());
            }
            case FIND -> {
                return new FindCommand(cmd[1].trim());
            }
            case FILTER -> {
                return new FilterCommand(cmd[1].trim());
            }
            default -> throw new InvalidCommandException(invalidCmd);
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new InvalidCommandException(invalidCmd);
        }
    }

    /**
     * Sets the <code>boolean</code> isNoteCommand to true before returning the command.
     */
    private static Command toggleNoteCommand(Command command) {
        command.setNoteCommand(true);
        return command;
    }
}
