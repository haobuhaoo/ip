package lax.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lax.catalogue.Catalogue;
import lax.catalogue.TaskList;
import lax.exception.InvalidCommandException;
import lax.storage.Storage;
import lax.storage.TaskStorage;
import lax.ui.Ui;

public class LabelCommandTest {
    private Ui ui;
    private Storage storage;
    private Catalogue catalogue;

    @BeforeEach
    void setup() {
        ui = new Ui();
        storage = new TaskStorage("./data/task.txt");
        catalogue = new TaskList(new ArrayList<>());
    }

    @Test
    public void execute_success() throws InvalidCommandException, IOException {
        catalogue.addItem("test task", "todo");
        catalogue.addItem("testing 1, 2, 3", "todo");
        Command label = new LabelCommand("2", "mark");

        assertEquals("""
                        Nice! I've marked this item as done:
                          [T][X] testing 1, 2, 3""",
                label.execute(catalogue, ui, storage));
    }
}
