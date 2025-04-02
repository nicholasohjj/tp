package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ALICE;
import static seedu.address.testutil.TypicalLessons.BENNY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;
public class LessonTest {
    String FUTURE_DATE_1 = "01-02-2030";
    String FUTURE_DATE_2 = "02-02-2030";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null, null, null));
    }

    @Test
    public void isConflict_sameDateAndTime_returnsTrue() {
        Lesson lesson = new LessonBuilder().withDate(FUTURE_DATE_1).withTime("10:00").build();
        Lesson conflictingLesson = new LessonBuilder().withDate(FUTURE_DATE_1).withTime("10:00").build();
        assertTrue(lesson.isConflict(conflictingLesson));
    }

    @Test
    public void isConflict_differentDate_returnsFalse() {
        Lesson lesson = new LessonBuilder().withDate(FUTURE_DATE_1).withTime("10:00").build();
        Lesson nonConflictingLesson = new LessonBuilder().withDate(FUTURE_DATE_2).withTime("10:00").build();
        assertFalse(lesson.isConflict(nonConflictingLesson));
    }

    @Test
    public void isConflict_differentTime_returnsFalse() {
        Lesson lesson = new LessonBuilder().withDate(FUTURE_DATE_1).withTime("10:00").build();
        Lesson nonConflictingLesson = new LessonBuilder().withDate(FUTURE_DATE_1).withTime("11:00").build();
        assertFalse(lesson.isConflict(nonConflictingLesson));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(ALICE.equals(ALICE));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(ALICE.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(ALICE.equals(5));
    }

    @Test
    public void equals_differentLesson_returnsFalse() {
        assertFalse(ALICE.equals(BENNY));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Lesson aliceCopy = new LessonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        Lesson aliceCopy = new LessonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Lesson aliceCopy = new LessonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different lesson -> returns false
        assertFalse(ALICE.equals(BENNY));

        // different name -> returns false
        Lesson editedAlice = new LessonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new LessonBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different time -> returns false
        editedAlice = new LessonBuilder(ALICE).withTime(VALID_TIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Lesson.class.getCanonicalName() + "{subject=" + ALICE.getSubject()
                + ", name=" + ALICE.getStudentName() + ", date=" + ALICE.getDate()
                + ", time=" + ALICE.getTime() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
