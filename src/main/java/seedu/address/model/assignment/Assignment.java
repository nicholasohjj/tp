package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.datetimeutil.Date;

/**
 * Represents an Assignment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAssignmentValue(String)}
 */
public class Assignment {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    // Identity fields
    public final String value;

    // Data fields
    public final Date dueDate;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param assignment A valid assignment name.
     */
    public Assignment(String assignment, Date dueDate) {
        requireAllNonNull(assignment, dueDate);
        checkArgument(isValidAssignmentValue(assignment), MESSAGE_CONSTRAINTS);
        this.value = assignment;
        this.dueDate = dueDate;
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
        return value.equals(otherAssignment.value)
                && dueDate.equals(otherAssignment.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, dueDate);
    }

    public static boolean isValidAssignmentValue(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    public boolean isSameAssignment(Assignment assignment) {
        return assignment.equals(this);
    }
}
