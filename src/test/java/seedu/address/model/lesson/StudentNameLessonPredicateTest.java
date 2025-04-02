package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Name;
import seedu.address.testutil.LessonBuilder;

public class StudentNameLessonPredicateTest {

    @Test
    public void test_nameMatches_returnsTrue() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Alice"));
        Lesson lesson = new LessonBuilder().withName("Alice").build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    public void test_nameDoesNotMatch_returnsFalse() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Alice"));
        Lesson lesson = new LessonBuilder().withName("Bob").build();
        assertFalse(predicate.test(lesson));
    }

    @Test
    public void test_namePartiallyMatches_returnsTrue() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Ali"));
        Lesson lesson = new LessonBuilder().withName("Alice").build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    public void test_nameCaseInsensitiveMatch_returnsTrue() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("alice"));
        Lesson lesson = new LessonBuilder().withName("Alice").build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Alice"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_null_returnsFalse() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Alice"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(new Name("Alice"));
        assertFalse(predicate.equals(5));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        StudentNameLessonPredicate predicate1 = new StudentNameLessonPredicate(new Name("Alice"));
        StudentNameLessonPredicate predicate2 = new StudentNameLessonPredicate(new Name("Bob"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_sameName_returnsTrue() {
        StudentNameLessonPredicate predicate1 = new StudentNameLessonPredicate(new Name("Alice"));
        StudentNameLessonPredicate predicate2 = new StudentNameLessonPredicate(new Name("Alice"));
        assertTrue(predicate1.equals(predicate2));
    }
}