package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.subject.Subject;

public class JsonAdaptedSubjectTest {
    private static final String VALID_SUBJECT = "Mathematics";
    private static final String INVALID_SUBJECT = ""; // Empty string
    private static final String INVALID_SUBJECT_SPACES = "   "; // Only spaces

    @Test
    public void constructor_withValidSubjectName_createsJsonAdaptedSubject() throws Exception {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        Subject subject = adaptedSubject.toModelType();
        assertEquals(VALID_SUBJECT, subject.subjectName);
    }

    @Test
    public void constructor_withSubjectSource_createsJsonAdaptedSubject() throws Exception {
        Subject sourceSubject = new Subject(VALID_SUBJECT);
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(sourceSubject);
        Subject convertedSubject = adaptedSubject.toModelType();
        assertEquals(sourceSubject, convertedSubject);
    }

    @Test
    public void toModelType_validSubjectName_returnsSubject() throws Exception {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        Subject subject = adaptedSubject.toModelType();
        assertEquals(VALID_SUBJECT, subject.subjectName);
    }

    @Test
    public void toModelType_invalidSubjectName_throwsIllegalValueException() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(INVALID_SUBJECT);
        assertThrows(IllegalValueException.class, adaptedSubject::toModelType, Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_invalidSubjectNameWithSpaces_throwsIllegalValueException() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(INVALID_SUBJECT_SPACES);
        assertThrows(IllegalValueException.class, adaptedSubject::toModelType, Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void getSubjectName_returnsCorrectName() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        assertEquals(VALID_SUBJECT, adaptedSubject.getSubjectName());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        assertEquals(adaptedSubject, adaptedSubject);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JsonAdaptedSubject adaptedSubject1 = new JsonAdaptedSubject(VALID_SUBJECT);
        JsonAdaptedSubject adaptedSubject2 = new JsonAdaptedSubject("Physics");
        assertNotEquals(adaptedSubject1, adaptedSubject2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        assertNotEquals(adaptedSubject, "Mathematics");
    }

    @Test
    public void equals_null_returnsFalse() {
        JsonAdaptedSubject adaptedSubject = new JsonAdaptedSubject(VALID_SUBJECT);
        assertNotEquals(null, adaptedSubject);
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        JsonAdaptedSubject adaptedSubject1 = new JsonAdaptedSubject(VALID_SUBJECT);
        JsonAdaptedSubject adaptedSubject2 = new JsonAdaptedSubject("Physics");
        assertNotEquals(adaptedSubject1.hashCode(), adaptedSubject2.hashCode());
    }
}
