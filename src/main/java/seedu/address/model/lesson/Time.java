package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should be of format: HH:MM, and be in 24 hour format";
    public static final String VALIDATION_REGEX = "([01][0-9]|2[0-3]):([0-5][0-9])";
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
        return test.matches(VALIDATION_REGEX);
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
