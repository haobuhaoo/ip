package lax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lax.catalogue.NoteList;
import lax.item.notes.Note;

public class StorageTest {
    @TempDir
    Path tempNotesDir;

    private String filePath;

    @BeforeEach
    public void setup() {
        filePath = tempNotesDir.resolve("./data/notes.txt").toString();
    }

    @Test
    public void load_fileDoesntExist_success() throws IOException {
        assertEquals(0, new Storage(filePath).load(new ArrayList<>(), Note::new).size());
    }

    @Test
    public void load_fileExist_success() throws IOException {
        File f = new File(filePath);
        if (!f.getParentFile().mkdirs()) {
            System.out.println("Error creating parent directory.");
        }

        FileWriter file = new FileWriter(filePath);
        file.write("note 1\n");
        file.write("note 2");
        file.close();

        assertEquals(2, new Storage(filePath).load(new ArrayList<>(), Note::new).size());
    }

    @Test
    public void load_skipCorruptedLines_success() throws IOException {
        File f = new File(filePath);
        if (!f.getParentFile().mkdirs()) {
            System.out.println("Error creating parent directory.");
        }

        FileWriter file = new FileWriter(filePath);
        file.write("note 1\n");
        file.write("#corrupted 1\n");
        file.write("note 2\n");
        file.write("#corrupted 2\n");
        file.close();

        Function<String, Note> parseLine = line -> {
            if (line.contains("#")) {
                return null;
            }
            return new Note(line);
        };

        assertEquals(2, new Storage(filePath).load(new ArrayList<>(), parseLine).size());
    }

    @Test
    public void saveTask_success() throws IOException {
        ArrayList<Note> arrayList = new ArrayList<>();
        arrayList.add(new Note("note 1"));
        arrayList.add(new Note("note 2"));
        NoteList notes = new NoteList(arrayList);

        File f = new File(filePath);
        if (!f.getParentFile().mkdirs()) {
            System.out.println("Error creating parent directory.");
        }
        new Storage(filePath).saveTask(notes);

        assertEquals(2, new Storage(filePath).load(new ArrayList<>(), Note::new).size());
    }
}
