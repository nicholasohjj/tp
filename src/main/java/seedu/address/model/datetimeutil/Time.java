package seedu.address.model.datetimeutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Lesson's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should be of format: HH:MM, be in 24 hour format, and within hours of 8am-9pm";
    public static final DateTimeFormatter VALID_FORMAT = DateTimeFormatter.ofPattern("H:m");
    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        LocalTime parsedTime;
        try {
            parsedTime = LocalTime.parse(test, VALID_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        LocalTime afterTime = LocalTime.of(7, 59);
        LocalTime beforeTime = LocalTime.of(21, 1);
        return parsedTime.isBefore(beforeTime) && parsedTime.isAfter(afterTime);
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Time otherTime)) {
            return false;
        }

        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
