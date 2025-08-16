import java.util.ArrayList;
import java.util.Scanner;

public class Lax {
    public static String showList(ArrayList<String> list) {
        if (list.isEmpty()) return "Please add task";

        StringBuilder s = new StringBuilder();
        int n = 1;
        for (String i : list) {
            if (n > 1) s.append("\n");
            s.append(n).append(". ").append(i);
            n++;
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> taskList = new ArrayList<>(100);

        String command = "start";
        while (!command.equals("bye")) {
            switch (command) {
                case "start":
                    System.out.println("""
                            Hello! I'm Lax.
                            What can I do for you?""");
                    break;

                case "list":
                    System.out.println(showList(taskList));
                    break;

                default:
                    taskList.add(command);
                    System.out.println("added: " + command);
                    break;
            }
            command = scanner.nextLine().trim().toLowerCase();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
