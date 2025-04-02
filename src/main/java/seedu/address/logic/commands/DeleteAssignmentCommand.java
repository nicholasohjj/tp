package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

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
 * Deletes a assignment identified using it's displayed index from the address book.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "delete_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an assignment of student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Parameters: ASSIGNMENT NAME (must be a string)\n"
            + "Example: " + COMMAND_WORD + " 1 as/Math Exercise 1";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Assignment %1$s deleted successfully.";
    public static final String MESSAGE_INVALID_ASSIGNMENT_DISPLAYED = "The assignment name provided cannot be found";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "Error: Student list is empty";

    private static final Logger logger = LogsCenter.getLogger(DeleteAssignmentCommand.class);

    private final Index targetIndex;
    private final String assignmentName;

    /**
     * Creates a DeleteAssignmentCommand to delete the specified {@code Assignment}.
     *
     * @param targetIndex Index of the student in the filtered student list
     * @param assignmentName Name of the assignment to delete
     */
    public DeleteAssignmentCommand(Index targetIndex, String assignmentName) {
        requireAllNonNull(targetIndex, assignmentName);
        this.targetIndex = targetIndex;
        this.assignmentName = assignmentName;
        logger.info("DeleteAssignmentCommand created for student index: " + targetIndex.getOneBased()
                + " and assignment: " + assignmentName);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeleteAssignmentCommand for student index: " + targetIndex.getOneBased()
                + " and assignment: " + assignmentName);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (lastShownList.isEmpty()) {
            logger.warning("Attempted to delete assignment from empty student list");
            throw new CommandException(MESSAGE_EMPTY_STUDENT_LIST);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid student index: " + targetIndex.getOneBased()
                    + " (list size: " + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToDeleteAssignment = lastShownList.get(targetIndex.getZeroBased());
        assert studentToDeleteAssignment != null : "Student should not be null";

        Assignment assignmentToDelete = new Assignment(assignmentName, new Date("31-12-9999"));
        if (!studentToDeleteAssignment.getAssignments().contains(assignmentToDelete)) {
            logger.warning("Assignment not found: " + assignmentName + " for student: "
                    + studentToDeleteAssignment.getName());
            throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_DISPLAYED);
        }

        model.deleteAssignment(studentToDeleteAssignment, assignmentName);
        logger.info("Successfully deleted assignment: " + assignmentName + " for student: "
                + studentToDeleteAssignment.getName());

        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                Messages.format(studentToDeleteAssignment, assignmentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        DeleteAssignmentCommand otherDeleteAssignmentCommand = (DeleteAssignmentCommand) other;
        return targetIndex.equals(otherDeleteAssignmentCommand.targetIndex)
                && assignmentName.equals(otherDeleteAssignmentCommand.assignmentName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("assignmentName", assignmentName)
                .toString();
    }
}
