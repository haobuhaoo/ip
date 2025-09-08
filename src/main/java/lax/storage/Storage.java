package lax.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lax.catalogue.Catalogue;
import lax.item.Item;

/**
 * Represents the database of the chatbot specified at the <code>filePath</code>.
 */
public class Storage {
    /**
     * The path of the database file.
     */
    private final String filePath;

    /**
     * The total number of corrupted lines in the file.
     */
    private final int[] corrupted = { 0 };

    /**
     * Constructs the database at the specified file path.
     *
     * @param f The file path.
     */
    public Storage(String f) {
        filePath = f;
    }

    /**
     * Creates the file to store the catalogue.
     */
    private void createFile(File file) {
        try {
            if (!file.createNewFile()) {
                System.out.println("File could not be created.");
            }
        } catch (IOException e) {
            System.out.println("Error creating new file: " + e.getMessage());
        }
    }

    /**
     * Creates the parent directory for the file that stores the catalogue.
     */
    private void createFileDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                System.out.println("Error creating parent directory.");
            }
        }
    }

    /**
     * Increments the total count of corrupted lines and prints out the line that is corrupted.
     */
    protected void handleCorruptedItem(String line) {
        corrupted[0]++;
        System.out.println("Skipping corrupted item: " + line);
    }

    /**
     * Prints the total number of corrupted lines in the file.
     */
    private void printTotalCorruptedItem() {
        System.out.println("Total Corrupted Items: " + corrupted[0] + "\n");
    }

    /**
     * Loads the file and parses every line which creates an arraylist of items based on what the function
     * parseLine creates.
     *
     * @param <T>       An item or its subtypes.
     * @param arrayList The arraylist to store the items.
     * @param parseLine The function that converts the string line to an <code>Item</code> object.
     * @return The arraylist with the items or an empty arraylist.
     */
    protected <T extends Item> ArrayList<T> load(ArrayList<T> arrayList, Function<String, T> parseLine) {
        File file = new File(filePath);

        if (!file.exists()) {
            createFileDirectory(file);
            createFile(file);
            return arrayList;
        }

        try (Stream<String> lines = Files.lines(file.toPath())) {
            arrayList = lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(parseLine)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (corrupted[0] > 0) {
                printTotalCorruptedItem();
            }
        } catch (IOException e) {
            System.out.println("Error reading file from hard disk: " + e.getMessage());
        }

        return arrayList;
    }

    /**
     * Saves the existing version of <code>Catalogue</code> into the file specified by writing directly over
     * the existing file.
     *
     * @param catalogue The <code>Catalogue</code> that is being read and write into the file.
     */
    public void saveTask(Catalogue catalogue) {
        try {
            Path path = new File(filePath).toPath();
            Files.write(path, catalogue.serialize());
        } catch (IOException e) {
            throw new RuntimeException("Error saving task: " + e);
        }
    }
}
