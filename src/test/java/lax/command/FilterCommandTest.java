package lax.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private String parseDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
    }

    private String printDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma"))
                .replace("AM", "am").replace("PM", "pm");
    }

    @BeforeEach
    void setup() {
        ui = new Ui();
        storage = new TaskStorage("./data/task.txt");
        catalogue = new TaskList(new ArrayList<>());
    }

    @Test
    public void execute_success() throws InvalidCommandException, IOException {
        LocalDateTime firstDate = LocalDateTime.now().plusDays(1);
        LocalDateTime secondDate = LocalDateTime.now().plusDays(32);
        String firstDateString = parseDate(firstDate);
        String secondDateString = parseDate(secondDate);
        String today = parseDate(LocalDateTime.now());

        catalogue.addItem("test task /by " + firstDateString, "deadline");
        catalogue.addItem("testing 1, 2, 3 /by " + secondDateString, "deadline");
        Command filter = new FilterCommand(today);

        String formattedToday = printDate(LocalDateTime.now());
        String formattedDate1 = printDate(firstDate);
        String formattedDate2 = printDate(secondDate);
        assertEquals("Here are the items in your list on " + formattedToday + ":\n"
                        + "1. [D][ ] test task (by: " + formattedDate1 + ")\n"
                        + "2. [D][ ] testing 1, 2, 3 (by: " + formattedDate2 + ")",
                filter.execute(catalogue, ui, storage));
    }
}
