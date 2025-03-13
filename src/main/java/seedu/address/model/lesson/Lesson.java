package seedu.address.model.lesson;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;
import seedu.address.model.student.Student;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {

    // Identity fields
    private final Name name;
    private final Subject subject;

    // Data fields
    private final Student student;
    private final Date date;
    private final Time time;

    /**
     * Every field must be present and not null.
     * This is the original constructor for student from AB3
     */
    public Lesson(Student student, Date date, Time time) {
        requireAllNonNull(student, date, time);
        this.student = student;
        this.name = student.getName();
        this.date = date;
        this.time = time;
        this.subject = student.getSubject();
    }

    public Student getStudent() {
        return student;
    }

    public Name getName() {
        return name;
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

        return name.equals(otherLesson.name)
                && student.equals(otherLesson.student)
                && date.equals(otherLesson.date)
                && time.equals(otherLesson.time)
                && subject.equals(otherLesson.subject);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, student, date, time, subject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .add("time", time)
                .add("subject", subject)
                .toString();
    }
}

