import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lax {
    private static ArrayList<Task> taskList;

    public enum CommandList {START, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND}

    public enum TaskType {TODO, DEADLINE, EVENT}

    private static ArrayList<Task> loadTask() {
        ArrayList<Task> taskList = new ArrayList<>(100);
        File file = new File("./data/data.txt");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
            }
            return taskList;
        }

        try (Scanner scanner = new Scanner(file)) {
            int corrupted = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split("\\|");
                boolean completed = data[1].trim().equals("1");

                try {
                    Task t = null;
                    switch (TaskType.valueOf(data[0].trim().toUpperCase())) {
                    case TODO -> t = new Todo(data[2].trim(), completed);
                    case DEADLINE -> t = new Deadline(data[2].trim(), completed,
                            LocalDateTime.parse(data[3].trim()));
                    case EVENT -> t = new Event(data[2].trim(), completed,
                            LocalDateTime.parse(data[3].trim()), LocalDateTime.parse(data[4].trim()));
                    }
                    taskList.add(t);
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new RuntimeException(e);
                } catch (DateTimeParseException e) {
                    corrupted++;
                    System.out.println("Skipping corrupted task: " + line);
                }
            }

            if (corrupted > 0) {
                System.out.println("Total Corrupted Tasks: " + corrupted + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file from hard disk: " + e.getMessage());
        }

        return taskList;
    }

    private static void saveTask() {
        try (FileWriter file = new FileWriter("./data/data.txt")) {
            for (Task t : taskList) {
                file.write(t.toFile() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String showList(ArrayList<Task> list, LocalDateTime dateTime) {
        String tempString = dateTime == null ? "" : " on " + dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"));
        if (list.isEmpty()) return "There is no task in your list" + tempString + ".";

        StringBuilder s = new StringBuilder("Here are the tasks in your list" + tempString + ":");
        int n = 1;
        for (Task i : list) {
            s.append("\n").append(n).append(". ").append(i.toString());
            n++;
        }
        return s.toString();
    }

    private static LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    private static void labelTask(String[] cmd, boolean mark) throws InvalidCommandException {
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

    private static void addTask(String command, String type) throws InvalidCommandException {
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
                    String[] data = temp.split("/by");
                    t = new Deadline(data[0].trim(), parseDateTime(data[1].trim()));
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. deadline return book /by 23/08/2025 1800");
                }
            }
            case EVENT -> {
                try {
                    String temp = command.split(" ", 2)[1].trim();
                    String[] data = temp.split("/from");
                    String[] timing = data[1].trim().split("/to");
                    t = new Event(data[0].trim(), parseDateTime(timing[0].trim()), parseDateTime(timing[1].trim()));
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidCommandException("eg. event project meeting "
                            + "/from 23/08/2025 1400 /to 23/08/2025 1600");
                }
            }
            default -> throw new InvalidCommandException("""
                    eg. todo borrow book
                    eg. deadline return book /by 23/08/2025 1800
                    eg. event project meeting /from 23/08/2025 1400 /to 23/08/2025 1600""");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + command + "\"");
        }

        taskList.add(t);
        System.out.println("Got it. I've added this task to the list:\n  " + t
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    private static void deleteTask(String[] cmd) throws InvalidCommandException {
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

    private static String showTask(String command) throws InvalidCommandException {
        String[] cmd = command.split(" ", 2);
        LocalDateTime dateTime;
        try {
            dateTime = parseDateTime(cmd[1]);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("eg. find 23/08/2025 1600");
        }

        ArrayList<Task> newTask = new ArrayList<>(100);
        for (Task t : taskList) {
            if (t instanceof Deadline temp) {
                if (temp.getDueDate().isEqual(dateTime) || temp.getDueDate().isAfter(dateTime)) {
                    newTask.add(t);
                }
            } else if (t instanceof Event temp) {
                if ((temp.getStartDate().isEqual(dateTime) || temp.getStartDate().isBefore(dateTime))
                        && (temp.getEndDate().isEqual(dateTime) || temp.getEndDate().isAfter(dateTime))) {
                    newTask.add(t);
                }
            }
        }
        return showList(newTask, dateTime);
    }

    private static void cmdFunction(String command) throws InvalidCommandException {
        if (taskList == null) return;
        String[] cmd = command.split(" ");

        try {
            switch (CommandList.valueOf(cmd[0].toUpperCase())) {
            case START -> System.out.println("Hello! I'm Lax.\nWhat can I do for you?");
            case LIST -> System.out.println(showList(taskList, null));
            case MARK, UNMARK -> labelTask(cmd, cmd[0].equals("mark"));
            case TODO, DEADLINE, EVENT -> addTask(command, cmd[0]);
            case DELETE -> deleteTask(cmd);
            case FIND -> System.out.println(showTask(command));
            default -> throw new InvalidCommandException("\"" + command + "\"");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("\"" + command + "\"");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        taskList = loadTask();

        String command = "start";
        do {
            if (!command.isEmpty()) {
                try {
                    cmdFunction(command);
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (DateTimeParseException e) {
                    System.out.println("Wrong DateTime format.\neg. 23/08/2025 1800");
                }
            } else {
                System.out.println("Please key something in.");
            }
            command = scanner.nextLine().trim().toLowerCase();
        } while (!command.equals("bye"));
        System.out.println("Bye. Hope to see you again soon!");

        saveTask();
        scanner.close();
    }
}
