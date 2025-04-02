package seedu.address.model.lesson;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Name;

/**
 * Tests that a {@code Lesson}'s {@code Name} matches the keyword given.
 */
public class StudentNameLessonPredicate implements Predicate<Lesson> {
    private final Name studentName;

    /**
     * Constructor for StudentNameLessonPredicate
     * @param studentName StudentName to match
     */
    public StudentNameLessonPredicate(Name studentName) {
        this.studentName = studentName;
    }

    @Override
    public boolean test(Lesson lesson) {
        String lessonName = lesson.getStudentName().fullName;
        String targetName = studentName.fullName;

        return lessonName != null && targetName != null
                && lessonName.toLowerCase().contains(targetName.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentNameLessonPredicate)) {
            return false;
        }

        StudentNameLessonPredicate otherStudentNameLessonPredicate = (StudentNameLessonPredicate) other;
        return studentName.equals(otherStudentNameLessonPredicate.studentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("studentName", studentName).toString();
    }
}
