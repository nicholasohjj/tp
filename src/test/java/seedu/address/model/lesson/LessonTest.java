package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.testutil.TypicalLessons.ALICE;
import static seedu.address.testutil.TypicalLessons.BENNY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;
public class LessonTest {

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
        String expected = Lesson.class.getCanonicalName() + "{subjects=" + ALICE.getSubjects()
                + ", name=" + ALICE.getName() + ", date=" + ALICE.getDate()
                + ", time=" + ALICE.getTime() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
