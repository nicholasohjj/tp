package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.LessonBuilder;

public class JsonAdaptedLessonTest {
    private static final String VALID_NAME = "Alice Pauline";
    private static final String VALID_DATE = "01-01-2026";
    private static final String VALID_TIME = "10:00";
    private static final String VALID_SUBJECT = "Math";

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "2023-01-01"; // Wrong format
    private static final String INVALID_TIME = "10:00 to 12:00"; // Wrong format
    private static final String INVALID_SUBJECT = ""; // Empty string

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, VALID_DATE, VALID_TIME);
        Lesson expectedLesson = new LessonBuilder()
                .withName(VALID_NAME)
                .withDate(VALID_DATE)
                .withTime(VALID_TIME)
                .withSubject(VALID_SUBJECT)
                .build();

        assertEquals(expectedLesson, lesson.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, null, VALID_DATE, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, lesson::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, INVALID_NAME, VALID_DATE, VALID_TIME);
        assertThrows(IllegalValueException.class, lesson::toModelType, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, null, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, lesson::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, INVALID_DATE, VALID_TIME);
        assertThrows(IllegalValueException.class, lesson::toModelType, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, VALID_DATE, null);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, lesson::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, VALID_DATE, INVALID_TIME);
        assertThrows(IllegalValueException.class, lesson::toModelType, Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_NAME, VALID_DATE, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Subject.class.getSimpleName());
        assertThrows(IllegalValueException.class, lesson::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(INVALID_SUBJECT, VALID_NAME, VALID_DATE, VALID_TIME);
        assertThrows(IllegalValueException.class, lesson::toModelType, Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void constructor_withLessonSource_createsValidJsonAdaptedLesson() throws Exception {
        Lesson sourceLesson = new LessonBuilder()
                .withName(VALID_NAME)
                .withDate(VALID_DATE)
                .withTime(VALID_TIME)
                .withSubject(VALID_SUBJECT)
                .build();

        JsonAdaptedLesson jsonLesson = new JsonAdaptedLesson(sourceLesson);
        Lesson convertedLesson = jsonLesson.toModelType();

        assertEquals(sourceLesson, convertedLesson);
    }

    @Test
    public void constructor_withJsonFields_createsValidJsonAdaptedLesson() throws Exception {
        JsonAdaptedLesson jsonLesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_NAME, VALID_DATE, VALID_TIME);
        Lesson convertedLesson = jsonLesson.toModelType();

        Lesson expectedLesson = new LessonBuilder()
                .withName(VALID_NAME)
                .withDate(VALID_DATE)
                .withTime(VALID_TIME)
                .withSubject(VALID_SUBJECT)
                .build();

        assertEquals(expectedLesson, convertedLesson);
    }
}
