package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.datetimeutil.Date;

/**
 * Represents an Assignment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAssignmentName(String)}
 */
public class Assignment implements Comparable<Assignment> {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should be alphanumeric "
            + "and can contain spaces, apostrophes, ampersands, and hyphens. "
            + "It should not exceed 50 characters.";

    public static final String VALIDATION_REGEX = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9 ]+$";

    // Identity fields
    public final String assignmentName;

    // Data fields
    public final Date dueDate;
    private boolean isDone;

    /**
     * Constructs a {@code Assignment} that is not done.
     *
     * @param assignment A valid assignment name.
     * @param dueDate    A valid due date.
     */
    public Assignment(String assignment, Date dueDate) {
        requireAllNonNull(assignment, dueDate);
        checkArgument(isValidAssignmentName(assignment), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignment;
        this.dueDate = dueDate;
        this.isDone = false;
    }

    /**
     * Constructs a {@code Assignment} given if it is done via {@code isDone}.
     *
     * @param assignment A valid assignment name.
     * @param dueDate    A valid due date.
     */
    public Assignment(String assignment, Date dueDate, boolean isDone) {
        requireAllNonNull(assignment, dueDate);
        checkArgument(isValidAssignmentName(assignment), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignment;
        this.dueDate = dueDate;
        this.isDone = isDone;
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
        return assignmentName.equals(otherAssignment.assignmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentName, dueDate);
    }

    public static boolean isValidAssignmentName(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    public static boolean isValidAssignmentDate(String date) {
        return Date.isValidDate(date);
    }

    public boolean isSameAssignment(Assignment assignment) {
        return this.assignmentName.equals(assignment.assignmentName);
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDone() {
        isDone = true;
    }

    public void setUndone() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Compares the assignment based on due date and assignment name.
     */
    @Override
    public int compareTo(Assignment o2) {
        if (this.dueDate.equals(o2.dueDate)) {
            return this.assignmentName.compareTo(o2.assignmentName);
        } else {
            return this.dueDate.compareTo(o2.dueDate);
        }
    }
}
