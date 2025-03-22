package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.subject.Subject;

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
    public void isValidSubjectName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Subject.isValidSubjectName("")); // empty string
        assertFalse(Subject.isValidSubjectName(" ")); // spaces only
        assertFalse(Subject.isValidSubjectName("&")); // only non-alphanumeric characters
        assertFalse(Subject.isValidSubjectName(" Math")); // leading space

        // valid name
        assertTrue(Subject.isValidSubjectName("computer science")); // alphabets only
        assertTrue(Subject.isValidSubjectName("2103")); // numbers only
        assertTrue(Subject.isValidSubjectName("cs2103t")); // alphanumeric characters
        assertTrue(Subject.isValidSubjectName("Math")); // with capital letters
        assertTrue(Subject.isValidSubjectName("Introduction to AI & Machine Learning")); // long names
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
