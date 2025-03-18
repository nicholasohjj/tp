package seedu.address.model.datetimeutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Lesson's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in the format: D-M-YYYY and "
            + "after the current date";
    public static final DateTimeFormatter VALID_FORMAT = DateTimeFormatter.ofPattern("d-M-yyyy");
    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(test, VALID_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        boolean isBeforeCurrDate = parsedDate.isBefore(LocalDate.now());
        boolean isEqualCurrDate = parsedDate.isEqual(LocalDate.now());
        return !(isBeforeCurrDate || isEqualCurrDate);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date otherDate)) {
            return false;
        }

        return date.equals(otherDate.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    public int compareTo(Date dueDate) {
        return this.date.compareTo(dueDate.date);
    }
}
