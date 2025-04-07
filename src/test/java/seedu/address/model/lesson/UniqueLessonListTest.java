package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.testutil.LessonBuilder;

public class UniqueLessonListTest {

    private final UniqueLessonList uniqueLessonList = new UniqueLessonList();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(uniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        uniqueLessonList.add(ALICE);
        assertTrue(uniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLessonList.add(ALICE);
        Lesson editedAlice = new LessonBuilder(ALICE).build();
        assertTrue(uniqueLessonList.contains(editedAlice));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueLessonList.add(ALICE);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.add(ALICE));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueLessonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void clashes_conflictingLesson_returnsTrue() {
        uniqueLessonList.add(ALICE);
        Lesson conflicting = new LessonBuilder(ALICE).withTime(ALICE.getTime().toString()).build();
        assertTrue(uniqueLessonList.clashes(conflicting));
    }

    @Test
    public void clashes_nonConflictingLesson_returnsFalse() {
        uniqueLessonList.add(ALICE);
        Lesson nonConflicting = new LessonBuilder(ALICE).withTime("16:00").build(); // assume 16:00 doesn't clash
        assertFalse(uniqueLessonList.clashes(nonConflicting));
    }

    @Test
    public void remove_existingLesson_success() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.remove(ALICE);
        assertFalse(uniqueLessonList.contains(ALICE));
    }

    @Test
    public void remove_lessonNotInList_throwsLessonNotFoundException() {
        Lesson notInList = new LessonBuilder().build();
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(notInList));
    }

    @Test
    public void setLessons_withDuplicateLessons_throwsDuplicateLessonException() {
        Lesson lesson1 = new LessonBuilder().build();
        List<Lesson> duplicateLessons = List.of(lesson1, lesson1);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLessons(duplicateLessons));
    }

    @Test
    public void setLessons_validList_replacesList() {
        Lesson lesson1 = new LessonBuilder().build();
        List<Lesson> newLessons = List.of(lesson1);
        uniqueLessonList.setLessons(newLessons);
        assertTrue(uniqueLessonList.contains(lesson1));
    }


    @Test
    public void hashCode_consistentWithEquals() {
        UniqueLessonList anotherList = new UniqueLessonList();
        uniqueLessonList.add(ALICE);
        anotherList.add(ALICE);
        assertEquals(uniqueLessonList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueLessonList.asUnmodifiableObservableList().toString(), uniqueLessonList.toString());
    }
}
