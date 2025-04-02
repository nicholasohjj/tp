package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.datetimeutil.Date;

public class AssignmentTest {
    String FUTURE_DATE_1 = "10-04-2030";
    String FUTURE_DATE_2 = "11-04-2030";
    String PAST_DATE = "10-04-2020";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Assignment(null, null));
    }

    @Test
    public void constructor_invalidAssignmentName_throwsIllegalArgumentException() {
        String invalidName = "";
        Date validDate = new Date("10-04-2025");
        assertThrows(IllegalArgumentException.class, () -> new Assignment(invalidName, validDate));
    }

    @Test
    public void isValidAssignmentName() {
        assertThrows(NullPointerException.class, () -> Assignment.isValidAssignmentName(null));

        // invalid name
        assertFalse(Assignment.isValidAssignmentName("")); // empty string
        assertFalse(Assignment.isValidAssignmentName(" ")); // spaces only
        assertFalse(Assignment.isValidAssignmentName("^")); // only non-alphanumeric characters
        assertFalse(Assignment.isValidAssignmentName("assignment*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Assignment.isValidAssignmentName("some assignment")); // alphabets only
        assertTrue(Assignment.isValidAssignmentName("12345")); // numbers only
        assertTrue(Assignment.isValidAssignmentName("some assignment 2")); // alphanumeric characters
        assertTrue(Assignment.isValidAssignmentName("Some Assignment")); // with capital letters
        assertTrue(Assignment.isValidAssignmentName("Some Assignment 123")); // with numbers and letters
    }

    @Test
    public void isValidAssignmentDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Assignment.isValidAssignmentDate(null));

        // invalid date
        assertFalse(Assignment.isValidAssignmentDate("")); // empty string
        assertFalse(Assignment.isValidAssignmentDate(" ")); // spaces only
        assertFalse(Assignment.isValidAssignmentDate("invalid date")); // non-date string
        assertFalse(Assignment.isValidAssignmentDate(PAST_DATE)); // past date

        // valid date
        assertTrue(Assignment.isValidAssignmentDate(FUTURE_DATE_1)); // valid date
    }

    @Test
    public void equals() {
        Assignment assignment = new Assignment("some assignment", new Date(FUTURE_DATE_1));
        Assignment assignmentCopy = new Assignment(assignment.assignmentName, assignment.dueDate);

        // same values -> returns true
        assertTrue(assignment.equals(assignmentCopy));

        // same object -> returns true
        assertTrue(assignment.equals(assignment));

        // different types -> returns false
        assertFalse(assignment.equals(1));

        // null -> returns false
        assertFalse(assignment.equals(null));

        // different assignment -> returns false
        Assignment differentAssignment = new Assignment("another assignment", new Date("19-04-2025"));
        assertFalse(assignment.equals(differentAssignment));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        Assignment assignment = new Assignment("some assignment", new Date(FUTURE_DATE_1));
        Assignment assignmentCopy = new Assignment(assignment.assignmentName, assignment.dueDate);
        assertEquals(assignment.hashCode(), assignmentCopy.hashCode());
    }

    @Test
    public void setDone() {
        Assignment assignment = new Assignment("some assignment", new Date(FUTURE_DATE_1));
        assignment.setDone();
        assertTrue(assignment.isDone());
    }

    @Test
    public void setUndone() {
        Assignment assignment = new Assignment("some assignment", new Date("10-04-2025"));
        assignment.setDone();
        assignment.setUndone();
        assertFalse(assignment.isDone());
    }

    @Test
    public void compareTo() {
        Assignment assignment1 = new Assignment("assignment1", new Date(FUTURE_DATE_1));
        Assignment assignment2 = new Assignment("assignment2", new Date(FUTURE_DATE_1));
        Assignment assignment3 = new Assignment("assignment3", new Date(FUTURE_DATE_2));

        // same date, different names
        assertTrue(assignment1.compareTo(assignment2) < 0);

        // different dates
        assertTrue(assignment1.compareTo(assignment3) < 0);
    }
}