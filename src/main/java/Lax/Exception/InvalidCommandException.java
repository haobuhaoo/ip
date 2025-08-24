package Lax.Exception;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String msg) {
        super("Invalid command.\n" + msg);
    }
}
