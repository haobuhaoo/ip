import java.util.Scanner;

public class Lax {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Hello! I'm Lax.
                What can I do for you?""");

        String command = scanner.nextLine().trim().toLowerCase();
        if (!command.equals("bye")) {
            System.out.println(command);
        } else {
            System.out.println("Bye. Hope to see you again soon!");
        }
    }
}
