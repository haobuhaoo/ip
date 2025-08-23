import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lax {
    public enum CommandList {START, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE}

    public enum TaskType {TODO, DEADLINE, EVENT}

    private static ArrayList<Task> loadTask() {
        ArrayList<Task> task = new ArrayList<>(100);
        File file = new File("./data/data.txt");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
            }
            return task;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split("\\|");
                boolean completed = data[1].trim().equals("1");

                Task t = null;
                try {
                    switch (TaskType.valueOf(data[0].trim().toUpperCase())) {
                    case TODO -> t = new Todo(data[2].trim(), completed);
                    case DEADLINE -> t = new Deadline(data[2].trim(), completed, data[3].trim());
                    case EVENT -> t = new Event(data[2].trim(), completed, data[3].trim(), data[4].trim());
                    }
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new RuntimeException(e);
                }
                task.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file from hard disk: " + e.getMessage());
        }

        return task;
    }

    private static void saveTask(ArrayList<Task> task) {
        try (FileWriter file = new FileWriter("./data/data.txt")) {
            for (Task t : task) {
                file.write(t.toFile() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    private static void labelTask(String[] cmd, ArrayList<Task> taskList, boolean mark) throws InvalidCommandException {
        if (taskList.isEmpty())
            throw new InvalidCommandException("No task to be " + (mark ? "marked" : "unmarked"));
        if (cmd.length != 2) throw new InvalidCommandException("eg. mark 1\neg. unmark 1");

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
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    private static void addTask(String command, ArrayList<Task> taskList, String type) throws InvalidCommandException {
        Task t;
        try {
            switch (TaskType.valueOf(type.toUpperCase())) {
            case TODO -> {
                try {
                    t = new Todo(command.split(" ", 2)[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. todo borrow book");
                }
            }
            case DEADLINE -> {
                try {
                    String temp = command.split(" ", 2)[1].trim();
                    String[] desc = temp.split("/by");
                    t = new Deadline(desc[0].trim(), desc[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. deadline return book /by Sunday");
                }
            }
            case EVENT -> {
                try {
                    String temp = command.split(" ", 2)[1].trim();
                    String[] desc = temp.split("/from");
                    String[] timing = desc[1].trim().split("/to");
                    t = new Event(desc[0].trim(), timing[0].trim(), timing[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. event project meeting /from Mon 2pm /to 4pm");
                }
            }
            default -> throw new InvalidCommandException("""
                    eg. todo borrow book
                    eg. deadline return book /by Sunday
                    eg. event project meeting /from Mon 2pm /to 4pm""");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + command + "\"");
        }

        taskList.add(t);
        System.out.println("Got it. I've added this task to the list:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private static void deleteTask(String[] cmd, ArrayList<Task> taskList) throws InvalidCommandException {
        if (taskList.isEmpty()) throw new InvalidCommandException("No task to delete.");
        if (cmd.length != 2) throw new InvalidCommandException("eg. delete 1");

        try {
            Task t = taskList.remove(Integer.parseInt(cmd[1]) - 1);
            System.out.println("Noted. I've removed this task:\n  " + t
                    + "\nNow you have " + taskList.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("eg. delete 1");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Invalid task number.");
        }
    }

    private static void cmdFunction(String command, ArrayList<Task> taskList) throws InvalidCommandException {
        String[] cmd = command.split(" ");

        try {
            switch (CommandList.valueOf(cmd[0].toUpperCase())) {
            case START -> System.out.println("Hello! I'm Lax.\nWhat can I do for you?");
            case LIST -> System.out.println(showList(taskList));
            case MARK, UNMARK -> labelTask(cmd, taskList, cmd[0].equals("mark"));
            case TODO, DEADLINE, EVENT -> addTask(command, taskList, cmd[0]);
            case DELETE -> deleteTask(cmd, taskList);
            default -> throw new InvalidCommandException("\"" + command + "\"");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + command + "\"");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = loadTask();

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

        saveTask(taskList);
        scanner.close();
    }
}
