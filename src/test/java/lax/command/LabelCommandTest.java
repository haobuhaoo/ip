package lax.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void execute_success() throws InvalidCommandException {
        catalogue.addItem("test task", "todo");
        catalogue.addItem("testing 1, 2, 3 /by 09-09-2025 0000", "deadline");
        Command label = new LabelCommand("2", "mark");

        assertEquals("""
                        Nice! I've marked this item as done:
                          [D][X] testing 1, 2, 3 (by: Sep 09 2025 12:00am)""",
                label.execute(catalogue, ui, storage));
    }
}
