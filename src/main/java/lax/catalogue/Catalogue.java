package lax.catalogue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lax.exception.InvalidCommandException;
import lax.item.Item;

/**
 * Represents a list of items.
 */
public interface Catalogue {
    /**
     * The format of the dateTime that user inputs.
     */
    DateTimeFormatter INPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm", Locale.ENGLISH);

    /**
     * The format of the dateTime that the chatbot outputs.
     */
    DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma", Locale.ENGLISH);

    /**
     * Parses the dateTime of the pattern of "dd-MM-yyyy HHmm" into a <code>LocalDateTime</code> object.
     *
     * @return <li>The format is "yyyy-MM-ddTHH:mm".</li><li>Eg. "2025-08-26T13:24".</li>
     * @throws DateTimeParseException If the dateTime cannot be parsed.
     */
    default LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, INPUT_DATETIME_FORMAT);
    }

    /**
     * Parses the dateTime into a string.
     *
     * @return An empty string if dateTime is null. Else, a string dateTime in the format of
     *         "MMM dd yyyy hh:mma"
     */
    default String getDateString(LocalDateTime dateTime) {
        return dateTime == null
                ? ""
                : " on " + dateTime.format(OUTPUT_DATETIME_FORMAT)
                .replace("AM", "am")
                .replace("PM", "pm");
    }

    /**
     * Parses the catalogue into a string, with the timestamp if dateTime is not null.
     */
    default String createStringList(String dateString, ArrayList<? extends Item> catalogue) {
        if (catalogue.isEmpty()) {
            return "There is no item in your list" + dateString + ".";
        }

        String itemString = IntStream.range(1, catalogue.size() + 1)
                .mapToObj(i -> i + ". " + catalogue.get(i - 1).toString())
                .collect(Collectors.joining("\n"));
        return "Here are the items in your list" + dateString + ":\n" + itemString;
    }

    /**
     * Converts the <code>Catalogue</code> into a <code>String</code> for displaying.
     *
     * @param dateTime The dateTime used when filtering item.
     * @return A <code>String</code> representation of the catalogue with each item being listed out.
     */
    default String showList(LocalDateTime dateTime, ArrayList<? extends Item> catalogue) {
        String dateString = getDateString(dateTime);
        return createStringList(dateString, catalogue);
    }

    String showList();

    int size();

    Item labelItem(String s, boolean b) throws InvalidCommandException;

    Item addItem(String s, String t) throws InvalidCommandException;

    Item deleteItem(String s) throws InvalidCommandException;

    String findItems(String s);

    String filterItems(String s) throws InvalidCommandException;

    ArrayList<String> serialize();
}
