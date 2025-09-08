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

public class DeleteCommandTest {
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
        Command delete = new DeleteCommand("1");

        assertEquals("""
                        Noted. I've removed this item:
                          [T][ ] test task
                        Now you have 0 items in the list.""",
                delete.execute(catalogue, ui, storage));
    }
}
