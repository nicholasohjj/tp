package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Adds an assignment to a student.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "add_assignment";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Assignment: %2$s";
    /**
     * Returns a success message for the assignment addition.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to a student. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Math Exercise 1";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";

    private final Index index;
    private final Assignment assignment;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment} to the student at the specified index.
     */
    public AddAssignmentCommand(Index index, Assignment assignment) {
        requireAllNonNull(index, assignment);

        this.index = index;
        this.assignment = assignment;
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

        // Add the new assignment to the student
        Student editedStudent = studentToEdit.addAssignment(assignment);

        // Update the model with the edited student
        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // Return a success message
        return new CommandResult(generateSuccessMessage(editedStudent));
    }

    private String generateSuccessMessage(Student editedStudent) {
        return String.format(Messages.MESSAGE_ADD_ASSIGNMENT_SUCCESS, editedStudent.getName(), assignment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAssignmentCommand)) {
            return false;
        }

        AddAssignmentCommand e = (AddAssignmentCommand) other;
        return index.equals(e.index) && assignment.equals(e.assignment);
    }
}
