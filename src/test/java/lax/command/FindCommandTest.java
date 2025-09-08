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

public class FindCommandTest {
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
        catalogue.addItem("testing 1, 2, 3", "todo");
        Command find = new FindCommand("testing");

        assertEquals("Here are the items in your list:\n1. [T][ ] testing 1, 2, 3",
                find.execute(catalogue, ui, storage));
    }
}
