package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import java.util.List;

import seedu.address.commons.core.index.Index;
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

    public static final String MESSAGE_MARK_ASSIGNMENT_SUCCESS = "Assignment %1$s\nmarked successfully.";
    public static final String MESSAGE_INVALID_ASSIGNMENT_DISPLAYED = "Error: The assignment name provided cannot be "
            + "found";

    private final Index index;
    private final String assignmentName;

    /**
     * Creates a MarkAssignmentCommand to mark the specified {@code Assignment}
     */
    public MarkAssignmentCommand(Index index, String assignmentName) {
        requireAllNonNull(index, assignmentName);

        this.index = index;
        this.assignmentName = assignmentName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        // Check if the index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        // Get the student to edit
        Student studentToEdit = lastShownList.get(index.getZeroBased());

        // Check if the assignment exists
        if (!studentToEdit.getAssignments().contains(new Assignment(assignmentName, new Date("31-12-9999")))) {
            throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_DISPLAYED);
        }

        // Mark the assignment
        studentToEdit.markAssignment(assignmentName);

        return new CommandResult(generateSuccessMessage(), true);
    }

    private String generateSuccessMessage() {
        return String.format(MESSAGE_MARK_ASSIGNMENT_SUCCESS, assignmentName);
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

        MarkAssignmentCommand otherMarkAssignmentCommand = (MarkAssignmentCommand) other;
        return index.equals(otherMarkAssignmentCommand.index)
                && assignmentName.equals(otherMarkAssignmentCommand.assignmentName);
    }
}
