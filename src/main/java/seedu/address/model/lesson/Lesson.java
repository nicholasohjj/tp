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
    private final Subject subject;
    private final Date date;
    private final Time time;


    /**
     * Every field must be present and not null.
     * This is the original constructor for lesson from AB3
     */
    public Lesson(Subject subject, Name studentName, Date date, Time time) {
        requireAllNonNull(subject, studentName, date, time);
        this.studentName = studentName;
        this.date = date;
        this.time = time;
        this.subject = subject;

    }

    public Name getStudentName() {
        return studentName;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns true if both lessons have same date and time
     */
    public boolean isConflict(Lesson toCheck) {
        return date.equals(toCheck.getDate())
                && time.equals(toCheck.getTime());
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
                && subject.equals(otherLesson.subject);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(studentName, date, time, subject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .add("name", studentName)
                .add("date", date)
                .add("time", time)
                .toString();
    }
}

