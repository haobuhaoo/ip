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

public class FilterCommandTest {
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
        catalogue.addItem("test task /by 09-09-2025 0000", "deadline");
        catalogue.addItem("testing 1, 2, 3 /by 10-10-2025 1234", "deadline");
        Command filter = new FilterCommand("08-09-2025 0000");

        assertEquals("""
                        Here are the items in your list on Sep 08 2025 12:00am:
                        1. [D][ ] test task (by: Sep 09 2025 12:00am)
                        2. [D][ ] testing 1, 2, 3 (by: Oct 10 2025 12:34pm)""",
                filter.execute(catalogue, ui, storage));
    }
}
