package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

/**
 * Unmarks an assignment of a student identified using it's displayed index from the address book.
 */
public class UnmarkAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "unmark_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: STUDENT INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "Assignment Name\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Assignment 1";

    public static final String MESSAGE_SUCCESS = "Assignment \"%1$s\" unmarked as incomplete for %2$s";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "There are no students in the address book";

    private static final Logger logger = LogsCenter.getLogger(UnmarkAssignmentCommand.class);

    private final Index studentIndex;
    private final String assignmentName;

    /**
     * Creates an UnmarkAssignmentCommand to unmark the specified assignment.
     *
     * @param studentIndex Index of the student in the filtered student list
     * @param assignmentName Name of the assignment to unmark
     */
    public UnmarkAssignmentCommand(Index studentIndex, String assignmentName) {
        requireAllNonNull(studentIndex, assignmentName);
        this.studentIndex = studentIndex;
        this.assignmentName = Arrays.stream(assignmentName.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" ")).trim();
        logger.info(String.format("Created UnmarkAssignmentCommand for student index %d, assignment: %s",
                studentIndex.getOneBased(), assignmentName));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing UnmarkAssignmentCommand");

        List<Student> studentList = model.getFilteredStudentList();

        // Validate student list
        if (studentList.isEmpty()) {
            logger.warning("Attempted to unmark assignment in empty student list");
            throw new CommandException(MESSAGE_EMPTY_STUDENT_LIST);
        }

        // Validate student index
        if (studentIndex.getZeroBased() >= studentList.size()) {
            logger.warning(String.format("Invalid student index: %d (list size: %d)",
                    studentIndex.getOneBased(), studentList.size()));
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student student = studentList.get(studentIndex.getZeroBased());
        assert student != null : "Student should not be null";

        // Check if the assignment exists
        Assignment targetAssignment = new Assignment(assignmentName, new Date("31-12-9999"));
        if (!student.getAssignments().contains(targetAssignment)) {
            logger.warning(String.format("Assignment not found: %s for student %s",
                    assignmentName, student.getName()));
            throw new CommandException(String.format(Messages.MESSAGE_ASSIGNMENT_NOT_FOUND, assignmentName));
        }

        // Unmark the assignment
        Student updatedStudent = student.unmarkAssignment(assignmentName);
        model.setStudent(student, updatedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        logger.info(String.format("Successfully unmarked assignment %s for student %s",
                assignmentName, student.getName()));

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, assignmentName, student.getName()),
                true);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmarkAssignmentCommand)) {
            return false;
        }

        UnmarkAssignmentCommand otherCommand = (UnmarkAssignmentCommand) other;
        return studentIndex.equals(otherCommand.studentIndex)
                && assignmentName.equals(otherCommand.assignmentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("assignmentName", assignmentName)
                .toString();
    }
}
