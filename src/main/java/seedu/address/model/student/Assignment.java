package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

public class Assignment {

    public final String value;

    public Assignment(String assignment) {
        requireNonNull(assignment);
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
}
