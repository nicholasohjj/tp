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
    public final String assignmentName;

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
        this.assignmentName = assignment;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return assignmentName;
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
        return assignmentName.equals(otherAssignment.assignmentName)
                && dueDate.equals(otherAssignment.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentName, dueDate);
    }

    public static boolean isValidAssignmentValue(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    public static boolean isValidAssignmentDate(String date) {
        return Date.isValidDate(date);
    }

    public boolean isSameAssignment(Assignment assignment) {
        return assignment.equals(this);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int compareTo(Assignment o2) {
        if (this.dueDate.equals(o2.dueDate)) {
            return this.assignmentName.compareTo(o2.assignmentName);
        } else {
            return this.dueDate.compareTo(o2.dueDate);
        }
    }
}
