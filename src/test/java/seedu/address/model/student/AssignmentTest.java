package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.Assignment;

public class AssignmentTest {
    @Test
    public void equals() {
        // same values -> returns true
        Assignment assignment = new Assignment("some assignment");
        Assignment assignmentCopy = new Assignment(assignment.value);
        assertTrue(assignment.equals(assignmentCopy));

        // same object -> returns true
        assertTrue(assignment.equals(assignment));

        // different types -> returns false
        assertFalse(assignment.equals(1));

        // null -> returns false
        assertFalse(assignment.equals(null));

        // different assignment -> returns false
        Assignment differentAssignment = new Assignment("another assignment");
        assertFalse(assignment.equals(differentAssignment));
    }

}
