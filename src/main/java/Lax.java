import java.util.ArrayList;
import java.util.Scanner;

public class Lax {
    private static class Task {
        private String name;
        private Boolean completed;

        public Task(String n) {
            name = n;
            completed = false;
        }

        public void markTask() {
            completed = true;
        }

        public void unmarkTask() {
            completed = false;
        }
    }

    private static void addTask(String command, ArrayList<Task> taskList) {
        taskList.add(new Task(command));
        System.out.println("added: " + command);
    }

    private static String showList(ArrayList<Task> list) {
        if (list.isEmpty()) return "There is no task in your list.";

        StringBuilder s = new StringBuilder("Here are the tasks in your list:");
        int n = 1;
        for (Task i : list) {
            s.append("\n").append(n).append(". [").append(i.completed ? "X" : " ").append("] ").append(i.name);
            n++;
        }
        return s.toString();
    }

    private static void cmdFunction(String command, ArrayList<Task> taskList) {
        String[] cmd = command.split(" ");
        boolean emptyList = taskList.isEmpty();

        switch (cmd[0]) {
            case "start":
                System.out.println("Hello! I'm Lax.\nWhat can I do for you?");
                return;

            case "list":
                System.out.println(showList(taskList));
                return;

            case "mark", "unmark":
                boolean mark = cmd[0].equals("mark");
                if (emptyList) {
                    System.out.println("No task to be " + (mark ? "marked" : "unmarked"));
                    return;
                } else if (cmd.length != 2) {
                    addTask(command, taskList);
                    return;
                }

                try {
                    Task t = taskList.get(Integer.parseInt(cmd[1]) - 1);
                    if (mark) {
                        if (t.completed) {
                            System.out.println("Task \"" + t.name + "\" is already marked as done");
                            return;
                        }
                        t.markTask();
                        System.out.println("Nice! I've marked this task as done: \n" + "  [X] " + t.name);
                    } else {
                        if (!t.completed) {
                            System.out.println("Task \"" + t.name + "\" is already marked as not done");
                            return;
                        }
                        t.unmarkTask();
                        System.out.println("OK, I've marked this task as not done yet: \n" + "  [ ] " + t.name);
                    }
                    return;
                } catch (NumberFormatException e) {
                    addTask(command, taskList);
                    return;
                }

            default:
                addTask(command, taskList);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>(100);

        String command = "start";
        do {
            if (!command.isEmpty()) {
                cmdFunction(command, taskList);
            } else {
                System.out.println("Please key something in.");
            }
            command = scanner.nextLine().trim().toLowerCase();
        } while (!command.equals("bye"));
        System.out.println("Bye. Hope to see you again soon!");
    }
}
