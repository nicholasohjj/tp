package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Error: Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Error: Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "Error: The student index provided "
            + "is invalid!";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "Error: The student index provided is out of bounds!";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "Error: The lesson index provided is invalid!";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_LESSONS_LISTED_OVERVIEW = "%1$d lessons listed!";
    public static final String MESSAGE_EMPTY_FIELD = "Error: %1$s field cannot be empty!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Error: Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_ADD_ASSIGNMENT_SUCCESS = "New assignment added to %1$s: %2$s";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "Error: Student list is empty! Nothing to delete";
    public static final String MESSAGE_EMPTY_LESSON_LIST = "Error: Lesson list is empty! Nothing to delete";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "Error: Assignment with ID %1$s not found!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Address: ")
                .append(student.getAddress())
                .append("; Subjects: ");
        student.getSubjects().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Lesson} for display to the user.
     */
    public static String format(Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append(lesson.getSubject())
                .append(" lesson; Student Name: ")
                .append(lesson.getStudentName())
                .append("; Date: ")
                .append(lesson.getDate())
                .append("; Time: ")
                .append(lesson.getTime());

        return builder.toString();
    }


    /**
     * Formats the {@code Assignment} for display to the user.
     */
    public static String format(Student student, Assignment assignment) {
        final StringBuilder builder = new StringBuilder();
        builder.append(assignment.getAssignmentName())
                .append(" for student: ")
                .append(student.getName());
        return builder.toString();
    }
}
