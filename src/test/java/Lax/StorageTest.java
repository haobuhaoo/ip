package Lax;

import Lax.Task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    String filePath;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        filePath = tempDir.resolve("./data/data.txt").toString();
    }
    @Test
    public void loadTask_fileDoesntExist_success() {
        assertEquals(0, new Storage(filePath).loadTask().size());
    }

    @Test
    public void loadTask_fileExist_success() throws IOException {
        File f = new File(filePath);
        f.getParentFile().mkdirs();

        FileWriter file = new FileWriter(filePath);
        file.write("todo | 0 | return book\n");
        file.write("deadline | 1 | assignment | 2025-08-23T19:41\n");
        file.close();

        assertEquals(2, new Storage(filePath).loadTask().size());
    }

    @Test
    public void saveTask_success() {
        ArrayList<Task> arrayList = new ArrayList<>();
        arrayList.add(new Todo("read book"));
        arrayList.add(new Deadline("return book", LocalDateTime.parse("2025-08-25T13:50")));
        arrayList.add(new Event("project meeting", LocalDateTime.parse("2025-08-26T14:00"),
                LocalDateTime.parse("2025-08-26T16:00")));
        TaskList t = new TaskList(arrayList);

        File f = new File(filePath);
        f.getParentFile().mkdirs();
        new Storage(filePath).saveTask(t);

        assertEquals(3, t.size());
    }
}
