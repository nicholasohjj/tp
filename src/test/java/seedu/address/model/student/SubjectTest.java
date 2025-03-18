package seedu.address.model.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class SubjectTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("&")); // only non-alphanumeric characters
        assertFalse(Subject.isValidSubject(" Math")); // leading space

        // valid name
        assertTrue(Subject.isValidSubject("computer science")); // alphabets only
        assertTrue(Subject.isValidSubject("2103")); // numbers only
        assertTrue(Subject.isValidSubject("cs2103t")); // alphanumeric characters
        assertTrue(Subject.isValidSubject("Math")); // with capital letters
        assertTrue(Subject.isValidSubject("Introduction to AI & Machine Learning")); // long names
    }
    @Test
    public void equals() {
        Subject subject = new Subject("Math");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("Math")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("CS2103T")));
    }
}
