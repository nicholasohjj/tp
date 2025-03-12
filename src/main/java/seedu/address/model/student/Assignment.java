package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Assignment {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String value;

    public Assignment(String assignment) {
        requireNonNull(assignment);
        checkArgument(isValidAssignmentValue(assignment), MESSAGE_CONSTRAINTS);
        value = assignment;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return value.equals(otherAssignment.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static boolean isValidAssignmentValue(String value) {
        return value.matches(VALIDATION_REGEX);
    }
}
