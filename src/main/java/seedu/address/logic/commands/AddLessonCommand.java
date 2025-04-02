package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.HashSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;

/**
 * Adds lesson to the addressbook
 */
public class AddLessonCommand extends Command {
    public static final String COMMAND_WORD = "add_lesson";
    public static final Address VALID_ADDRESS = new Address("123 Main Street");
    public static final Phone VALID_PHONE = new Phone("12345678");
    public static final Email VALID_EMAIL = new Email("john@example.com");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alex Yeoh "
            + PREFIX_DATE + "17-09-2027 "
            + PREFIX_TIME + "14:00 "
            + PREFIX_SUBJECT + "Science";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s.";
    public static final String MESSAGE_DUPLICATE_LESSON = "Error: This lesson already exists in the address book.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Error: "
            + "The specified student does not exist in the address book.";
    public static final String MESSAGE_LESSON_CONFLICT = "Error: The lesson clashes with an existing lesson "
            + "in the address book.";
    public static final String MESSAGE_SUBJECT_MISMATCH = "Error: The specified "
            + "subject does not exist for the student.";

    private static final Logger logger = LogsCenter.getLogger(AddLessonCommand.class);

    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        this.toAdd = lesson;
        logger.info("AddLessonCommand created for lesson: " + lesson);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AddLessonCommand for lesson: " + toAdd);

        if (model.hasLesson(toAdd)) {
            logger.warning("Duplicate lesson detected: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        if (model.hasLessonConflict(toAdd)) {
            logger.warning("Lesson conflict detected: " + toAdd);
            throw new CommandException(MESSAGE_LESSON_CONFLICT);
        }

        Student dummyStudent = new Student(toAdd.getStudentName(), VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                new HashSet<>(), new UniqueAssignmentList());

        if (!model.hasStudent(dummyStudent)) {
            logger.warning("Student not found: " + toAdd.getStudentName());
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        if (!model.hasStudentSubject(dummyStudent, toAdd.getSubject())) {
            logger.warning("Subject mismatch for student: " + toAdd.getStudentName()
                    + " with subject: " + toAdd.getSubject());
            throw new CommandException(MESSAGE_SUBJECT_MISMATCH);
        }

        model.addLesson(toAdd);
        logger.info("Lesson successfully added: " + toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand otherAddLessonCommand)) {
            return false;
        }

        return toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
