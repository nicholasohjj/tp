package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

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
    private final Index targetIndex;
    private final String assignmentName;

    /**
     * Creates a DeleteAssignmentCommand to delete the specified {@code Assignment}
     */
    public DeleteAssignmentCommand(Index targetIndex, String assignmentName) {
        requireAllNonNull(targetIndex, assignmentName);

        this.targetIndex = targetIndex;
        this.assignmentName = assignmentName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToDeleteAssignment = lastShownList.get(targetIndex.getZeroBased());
        Assignment assignmentToDelete = new Assignment(assignmentName, new Date("31-12-9999"));
        if (!studentToDeleteAssignment.getAssignments().contains(assignmentToDelete)) {
            throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_DISPLAYED);
        }

        model.deleteAssignment(studentToDeleteAssignment, assignmentName);
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
