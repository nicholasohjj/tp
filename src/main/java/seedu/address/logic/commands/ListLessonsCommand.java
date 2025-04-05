package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddLessonCommand.VALID_ADDRESS;
import static seedu.address.logic.commands.AddLessonCommand.VALID_EMAIL;
import static seedu.address.logic.commands.AddLessonCommand.VALID_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Finds and lists all lessons in address book whose student name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListLessonsCommand extends Command {

    public static final String COMMAND_WORD = "list_lessons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons whose student name contains "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "If no keywords are specified, displays all lessons.\n"
            + "Parameters: KEYWORD + " + PREFIX_NAME + "[KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice or "
            + COMMAND_WORD;

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Error: The specified "
            + "student does not exist in the address book";
    public static final String MESSAGE_NO_LESSONS_FOUND = "Error: No lessons found matching the criteria";

    private static final Logger logger = LogsCenter.getLogger(ListLessonsCommand.class);

    private final Predicate<Lesson> predicate;
    private final Optional<Name> studentName;

    /**
     * Constructor for the ListLessonsCommand
     * @param predicate Predicate to filter the UniqueLessonList with
     */
    public ListLessonsCommand(Predicate<Lesson> predicate) {
        this.predicate = predicate;
        this.studentName = Optional.empty();
        logger.info("ListLessonsCommand created with predicate filter");
    }

    /**
     * Constructor for the ListLessonsCommand with student name
     * @param predicate
     * @param studentName
     */
    public ListLessonsCommand(Predicate<Lesson> predicate, Name studentName) {
        this.predicate = predicate;
        this.studentName = Optional.of(studentName);
        logger.info("ListLessonsCommand created for student: " + studentName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing ListLessonsCommand");

        // Validate student exists if name was specified
        if (studentName.isPresent()) {
            Student dummyStudent = new Student(studentName.get(), VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, new HashSet<>(), new UniqueAssignmentList());

            if (!model.hasStudent(dummyStudent)) {
                logger.warning("Student not found: " + studentName.get());
                throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
            }
        }

        model.updateFilteredLessonList(predicate);
        int lessonCount = model.getFilteredLessonList().size();

        if (lessonCount == 0) {
            logger.info("No lessons found matching the criteria");
            return new CommandResult(MESSAGE_NO_LESSONS_FOUND, true);
        }

        logger.info("Found " + lessonCount + " lessons matching the criteria");
        return new CommandResult(
                String.format(Messages.MESSAGE_LESSONS_LISTED_OVERVIEW, lessonCount),
                true);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListLessonsCommand)) {
            return false;
        }

        ListLessonsCommand otherFindStudentCommand = (ListLessonsCommand) other;
        return predicate.equals(otherFindStudentCommand.predicate)
                && studentName.equals(otherFindStudentCommand.studentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
