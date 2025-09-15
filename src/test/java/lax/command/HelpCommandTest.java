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

public class HelpCommandTest {
    private Ui ui;
    private Storage storage;
    private Catalogue catalogue;

    @BeforeEach
    public void setup() {
        ui = new Ui();
        storage = new TaskStorage("./data/task.txt");
        catalogue = new TaskList(new ArrayList<>());
    }

    @Test
    public void execute_success() throws InvalidCommandException, IOException {
        Command help = new HelpCommand();

        assertEquals("""
                        List of Commands:
                        >>> general command:
                        - help
                        - bye

                        zzzzz...
                        >>> task command:
                        (add a prefix "task" in front)
                        - list
                        - mark "task number"
                        - unmark "task number"
                        - todo "task name"
                        - deadline "task name" /by "due DateTime"
                        - event "task name" /from "start DateTime" /to "end DateTime"
                        - delete "task number"
                        - find "task description"
                        - filter "DateTime"

                        zzzzz...
                        >>> note command:
                        (add a prefix "note" in front)
                        - list
                        - add "note"
                        - delete "note number"
                        - find "note description"
                        - filter "DateTime\"""",
                help.execute(catalogue, ui, storage));
    }
}
