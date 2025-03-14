package seedu.address.testutil;

import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_NAME = "John Baker";
    public static final String DEFAULT_SUBJECT = "CS2103T";
    public static final String DEFAULT_DATE = "11-08-2025";
    public static final String DEFAULT_TIME = "14:00";

    private Name name;
    private Date date;
    private Time time;
    private Subject subject;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        subject = new Subject(DEFAULT_SUBJECT);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        name = lessonToCopy.getName();
        date = lessonToCopy.getDate();
        time = lessonToCopy.getTime();
        subject = lessonToCopy.getSubject();
    }

    /**
     * Sets the {@code Name} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }
    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building
     */
    public LessonBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }
    public Lesson build() {
        return new Lesson(subject, name, date, time);
    }

}
