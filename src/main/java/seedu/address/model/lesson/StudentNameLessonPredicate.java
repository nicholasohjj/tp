package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Lesson}'s {@code Name} matches the keyword given.
 */
public class StudentNameLessonPredicate implements Predicate<Lesson> {
    private final String keyword;

    /**
     * Constructor for StudentNameLessonPredicate
     * @param keyword Keyword to match
     */
    public StudentNameLessonPredicate(String keyword) {
        requireNonNull(keyword);
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.getName().fullName.toLowerCase().contains(keyword);
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
        return keyword.equals(otherStudentNameLessonPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
