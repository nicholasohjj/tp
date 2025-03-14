package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {

    // Identity fields
    private final Name studentName;
    private final Subject subject;

    // Data fields
    private final Date date;
    private final Time time;

    /**
     * Every field must be present and not null.
     * This is the original constructor for student from AB3
     */
    public Lesson(Subject subject, Name studentName, Date date, Time time) {
        requireAllNonNull(subject, date, time);
        this.subject = subject;
        this.studentName = studentName;
        this.date = date;
        this.time = time;
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

    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns true if both Lessons have the same student name, date and time.
     * This defines a weaker notion of equality between two lessons.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getName().equals(getName())
                && otherLesson.getDate().equals(getDate())
                && otherLesson.getTime().equals(getTime());
    }

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two lessons.
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
                .add("name", studentName)
                .add("date", date)
                .add("time", time)
                .add("subject", subject)
                .toString();
    }
}

