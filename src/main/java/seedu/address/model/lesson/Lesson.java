package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.student.Name;
import seedu.address.model.subject.Subject;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {

    // Identity fields
    private final Name studentName;
    private final Set<Subject> subjects = new HashSet<>();
    private final Date date;
    private final Time time;


    /**
     * Every field must be present and not null.
     * This is the original constructor for lesson from AB3
     */
    public Lesson(Set<Subject> subjects, Name studentName, Date date, Time time) {
        requireAllNonNull(subjects, studentName, date, time);
        this.studentName = studentName;
        this.date = date;
        this.time = time;
        this.subjects.addAll(subjects);

    }

    public Name getName() {
        return studentName;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }
    /**
     * Returns true if both lessons have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson otherLesson)) {
            return false;
        }

        return studentName.equals(otherLesson.studentName)
                && date.equals(otherLesson.date)
                && time.equals(otherLesson.time)
                && subjects.equals(otherLesson.subjects);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(studentName, date, time, subjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subjects", subjects)
                .add("name", studentName)
                .add("date", date)
                .add("time", time)
                .toString();
    }
}

