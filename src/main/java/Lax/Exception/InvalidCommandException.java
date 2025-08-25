package Lax.Exception;

/**
 * Represents the <code>Exception</code> that occurs when an invalid command is keyed in by the user.
 */
public class InvalidCommandException extends Exception {
    /**
     * Constructs the <code>Exception</code> with a <code>String</code> message to be printed out.
     */
    public InvalidCommandException(String msg) {
        super("Invalid command.\n" + msg);
    }
}
