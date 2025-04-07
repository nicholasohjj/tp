package seedu.address.model.datetimeutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Lesson's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Error: Date must be a valid date, "
            + "in DD-MM-YYYY or D-M-YYYY format (e.g., 05-10-2023 or 5-10-2023),"
            + "\nand must be a future date (after today)";
    public static final DateTimeFormatter VALID_FORMAT = DateTimeFormatter.ofPattern("d-M-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

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
            return parsedDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("dd MMM yyyy").format(LocalDate.parse(date, VALID_FORMAT));
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
