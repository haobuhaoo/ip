import java.util.ArrayList;
import java.util.Scanner;

public class Lax {
    private static void addTask(String command, ArrayList<Task> taskList, String type) throws InvalidCommandException {
        Task t;
        switch (type) {
            case "todo" -> {
                try {
                    t = new Todo(command.substring(5));
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. todo borrow book");
                }
            }
            case "deadline" -> {
                try {
                    String temp = command.substring(9);
                    String[] split = temp.split("/by");
                    t = new Deadline(split[0].trim(), split[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. deadline return book /by Sunday");
                }
            }
            case "event" -> {
                try {
                    String temp = command.substring(6);
                    String[] split = temp.split("/from");
                    String[] timing = split[1].split("/to");
                    t = new Event(split[0].trim(), timing[0].trim(), timing[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. event project meeting /from Mon 2pm /to 4pm");
                }
            }
            default -> throw new InvalidCommandException("""
                    eg. todo borrow book
                    eg. deadline return book /by Sunday
                    eg. event project meeting /from Mon 2pm /to 4pm""");
        }
        taskList.add(t);
        System.out.println("Got it. I've added this task to the list:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private static String showList(ArrayList<Task> list) {
        if (list.isEmpty()) return "There is no task in your list.";

        StringBuilder s = new StringBuilder("Here are the tasks in your list:");
        int n = 1;
        for (Task i : list) {
            s.append("\n").append(n).append(". ").append(i.toString());
            n++;
        }
        return s.toString();
    }

    private static void cmdFunction(String command, ArrayList<Task> taskList) throws InvalidCommandException {
        String[] cmd = command.split(" ");
        boolean emptyList = taskList.isEmpty();

        switch (cmd[0]) {
            case "start" -> System.out.println("Hello! I'm Lax.\nWhat can I do for you?");
            case "list" -> System.out.println(showList(taskList));
            case "mark", "unmark" -> {
                boolean mark = cmd[0].equals("mark");
                if (emptyList) {
                    throw new InvalidCommandException("No task to be " + (mark ? "marked" : "unmarked"));
                } else if (cmd.length != 2) {
                    throw new InvalidCommandException("eg. mark 1\neg. unmark 1");
                }

                try {
                    Task t = taskList.get(Integer.parseInt(cmd[1]) - 1);
                    if (mark) {
                        if (t.isCompleted()) {
                            throw new InvalidCommandException("Task \"" + t.getName() + "\" is already marked as done");
                        }

                        t.markTask();
                        System.out.println("Nice! I've marked this task as done:\n  " + t);
                    } else {
                        if (!t.isCompleted()) {
                            throw new InvalidCommandException("Task \"" + t.getName() + "\" is already marked as not done");
                        }

                        t.unmarkTask();
                        System.out.println("OK, I've marked this task as not done yet:\n  " + t);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("eg. mark 1\neg. unmark 1");
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("Invalid task number");
                }
            }
            case "todo", "deadline", "event" -> addTask(command, taskList, cmd[0]);
            default -> throw new InvalidCommandException("");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>(100);

        String command = "start";
        do {
            if (!command.isEmpty()) {
                try {
                    cmdFunction(command, taskList);
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Please key something in.");
            }
            command = scanner.nextLine().trim().toLowerCase();
        } while (!command.equals("bye"));
        System.out.println("Bye. Hope to see you again soon!");
    }
}
