package seedu.address.testutil;

import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.subject.Subject;

/**
 * A utility class to help with building EditLessonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLessonDescriptor} with fields containing {@code lesson}'s details
     */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setName(lesson.getStudentName());
        descriptor.setDate(lesson.getDate());
        descriptor.setTime(lesson.getTime());
        descriptor.setSubject(lesson.getSubject());
    }

    /**
     * Sets the {@code Name} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Parses the {@code subject} into a {@code Subject} and set it to the {@code EditLessonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withSubject(String subject) {
        Subject newSubject = new Subject(subject);
        descriptor.setSubject(newSubject);
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
