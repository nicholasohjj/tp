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

public class UnmarkAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "unmark_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "Assignment Name\n"
            + "Example: " + COMMAND_WORD + " 1"
            + PREFIX_ASSIGNMENT + "Assignment 1";

    public static final String MESSAGE_UNMARK_ASSIGNMENT_SUCCESS = "Assignment %1$s\nunmarked successfully.";
    public static final String MESSAGE_INVALID_ASSIGNMENT_DISPLAYED = "The assignment name provided cannot be found";

    private final Index index;
    private final String assignmentName;

    public UnmarkAssignmentCommand(Index index, String assignmentName) {
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

        // Unmark the assignment
        studentToEdit.unmarkAssignment(assignmentName);

        return new CommandResult(generateSuccessMessage(), true);
    }

    private String generateSuccessMessage() {
        return String.format(MESSAGE_UNMARK_ASSIGNMENT_SUCCESS, assignmentName);
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
        return index.equals(otherCommand.index) && assignmentName.equals(otherCommand.assignmentName);
    }
}
