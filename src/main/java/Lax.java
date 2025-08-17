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

        public String toString() {
            return "[" + (completed ? "X" : " ") + "] " + name;
        }
    }

    private static class Todo extends Task {
        public Todo(String n) {
            super(n);
        }

        public String toString() {
            return "[T]" + super.toString();
        }
    }

    private static class Deadline extends Task {
        private String dueDate;

        public Deadline(String n, String d) {
            super(n);
            dueDate = d;
        }

        public String toString() {
            return "[D]" + super.toString() + " (by: " + dueDate + ")";
        }
    }

    private static class Event extends Task {
        private String startDate;
        private String endDate;

        public Event(String n, String s, String e) {
            super(n);
            startDate = s;
            endDate = e;
        }

        public String toString() {
            return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
        }
    }

    private static void addTask(String command, ArrayList<Task> taskList, String type) {
        Task t = null;
        switch (type) {
            case "todo" -> t = new Todo(command.substring(5));
            case "deadline" -> {
                String temp = command.substring(9);
                String[] split = temp.split("/by");
                t = new Deadline(split[0].trim(), split[1].trim());
            }
            case "event" -> {
                String temp = command.substring(6);
                String[] split = temp.split("/from");
                String[] timing = split[1].split("/to");
                t = new Event(split[0].trim(), timing[0].trim(), timing[1].trim());
            }
            default -> {
                System.out.println("Invalid task type.");
                return;
            }
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

    private static void cmdFunction(String command, ArrayList<Task> taskList) {
        String[] cmd = command.split(" ");
        boolean emptyList = taskList.isEmpty();

        switch (cmd[0]) {
            case "start" -> System.out.println("Hello! I'm Lax.\nWhat can I do for you?");
            case "list" -> System.out.println(showList(taskList));
            case "mark", "unmark" -> {
                boolean mark = cmd[0].equals("mark");
                if (emptyList) {
                    System.out.println("No task to be " + (mark ? "marked" : "unmarked"));
                    return;
                } else if (cmd.length != 2) {
                    System.out.println("Invalid task number.");
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
                        System.out.println("Nice! I've marked this task as done:\n  " + t);
                    } else {
                        if (!t.completed) {
                            System.out.println("Task \"" + t.name + "\" is already marked as not done");
                            return;
                        }
                        t.unmarkTask();
                        System.out.println("OK, I've marked this task as not done yet:\n  " + t);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number.");
                }
            }
            case "todo", "deadline", "event" -> addTask(command, taskList, cmd[0]);
            default -> System.out.println("Invalid command.");
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
