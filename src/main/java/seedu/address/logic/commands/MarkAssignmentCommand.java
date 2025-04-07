package seedu.address.logic.commands;

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
 * Marks an assignment of a student identified using it's displayed index from the address book.
 */
public class MarkAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "mark_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "Assignment Name\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Assignment 1";

    public static final String MESSAGE_MARK_ASSIGNMENT_SUCCESS = "Assignment \"%1$s\" marked as completed for %2$s";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "There are no students in the address book";

    private static final Logger logger = LogsCenter.getLogger(MarkAssignmentCommand.class);
    private final Index studentIndex;
    private final String assignmentName;

    /**
     * Creates a MarkAssignmentCommand to mark the specified assignment as completed.
     *
     * @param studentIndex Index of the student in the filtered student list
     * @param assignmentName Name of the assignment to mark
     */
    public MarkAssignmentCommand(Index studentIndex, String assignmentName) {
        requireAllNonNull(studentIndex, assignmentName);
        this.studentIndex = studentIndex;
        this.assignmentName = Arrays.stream(assignmentName.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" ")).trim();
        logger.info(String.format("Created MarkAssignmentCommand for student index %d, assignment: %s",
                studentIndex.getOneBased(), assignmentName));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();
        logger.info("Executing MarkAssignmentCommand");

        List<Student> studentList = model.getFilteredStudentList();

        if (studentList.isEmpty()) {
            logger.warning("Attempted to mark assignment in empty student list");
            throw new CommandException(MESSAGE_EMPTY_STUDENT_LIST);
        }

        if (studentIndex.getZeroBased() >= studentList.size()) {
            logger.warning(String.format("Invalid student index: %d (list size: %d)",
                    studentIndex.getOneBased(), studentList.size()));
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student student = studentList.get(studentIndex.getZeroBased());
        assert student != null : "Student should not be null";

        Assignment targetAssignment = new Assignment(assignmentName, new Date("31-12-9999"));
        if (!student.getAssignments().contains(targetAssignment)) {
            logger.warning(String.format("Assignment not found: %s for student %s",
                    assignmentName, student.getName()));
            throw new CommandException(String.format(Messages.MESSAGE_ASSIGNMENT_NOT_FOUND, assignmentName));
        }

        // Mark the assignment
        Student updatedStudent = student.markAssignment(assignmentName);
        model.setStudent(student, updatedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        logger.info(String.format("Successfully marked assignment %s for student %s",
                assignmentName, student.getName()));
        return new CommandResult(
                String.format(MESSAGE_MARK_ASSIGNMENT_SUCCESS, assignmentName, student.getName()),
                true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAssignmentCommand)) {
            return false;
        }

        MarkAssignmentCommand otherCommand = (MarkAssignmentCommand) other;
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
