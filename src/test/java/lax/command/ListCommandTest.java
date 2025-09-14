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

public class ListCommandTest {
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
        Command list = new ListCommand();

        assertEquals("There is no item in your list.", list.execute(catalogue, ui, storage));
    }
}
