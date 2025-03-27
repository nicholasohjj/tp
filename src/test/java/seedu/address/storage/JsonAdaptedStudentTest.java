package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.HARRY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example@.com";
    private static final String INVALID_SUBJECT = " ";
    private static final String INVALID_ASSIGNMENT_NAME = " ";
    private static final String INVALID_DUE_DATE = "22-1-2023";

    private static final String VALID_NAME = HARRY.getName().toString();
    private static final String VALID_PHONE = HARRY.getPhone().toString();
    private static final String VALID_EMAIL = HARRY.getEmail().toString();
    private static final String VALID_ADDRESS = HARRY.getAddress().toString();
    private static final List<JsonAdaptedSubject> VALID_SUBJECTS = HARRY.getSubjects().stream()
            .map(JsonAdaptedSubject::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAssignment> VALID_ASSIGNMENTS = HARRY.getAssignments()
            .asUnmodifiableObservableList().stream()
            .map(JsonAdaptedAssignment::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_ADDRESS, VALID_EMAIL,
                VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_EMAIL,
                        VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_ADDRESS, VALID_EMAIL,
                VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, INVALID_EMAIL,
                        VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null,
                VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_EMAIL,
                        VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                 new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_EMAIL,
                         VALID_SUBJECTS, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidSubjects_throwsIllegalValueException() {
        List<JsonAdaptedSubject> invalidSubjects = new ArrayList<>(VALID_SUBJECTS);
        invalidSubjects.add(new JsonAdaptedSubject(INVALID_SUBJECT));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_EMAIL,
                        invalidSubjects, VALID_ASSIGNMENTS);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_invalidAssignments_throwsIllegalValueException() {
        List<JsonAdaptedAssignment> invalidAssignments = new ArrayList<>(VALID_ASSIGNMENTS);
        invalidAssignments.add(new JsonAdaptedAssignment(INVALID_ASSIGNMENT_NAME, INVALID_DUE_DATE, false));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_EMAIL,
                        VALID_SUBJECTS, invalidAssignments);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

}
