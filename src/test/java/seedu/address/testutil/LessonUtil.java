package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Lesson lesson) {
        return AddLessonCommand.COMMAND_WORD + " " + getLessonDetails(lesson);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    private static String getLessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SUBJECT + lesson.getSubject().subjectName + " ");
        sb.append(PREFIX_NAME + lesson.getStudentName().fullName + " ");
        sb.append(PREFIX_DATE + lesson.getDate().date + " ");
        sb.append(PREFIX_TIME + lesson.getTime().time + " ");
        return sb.toString();
    }

    public static String getEditLessonDescriptorDetails(EditLessonCommand.EditLessonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getSubject().ifPresent(subject -> sb.append(PREFIX_SUBJECT).append(subject.subjectName).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.date).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TIME).append(time.time).append(" "));
        return sb.toString();
    }
}
