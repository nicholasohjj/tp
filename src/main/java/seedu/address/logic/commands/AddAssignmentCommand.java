package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

/**
 * Adds an assignment to a student.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "add_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to a student.\n"
            + "Parameters: STUDENT INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "[ASSIGNMENT_NAME] "
            + PREFIX_DATE + "[DUE_DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Math Exercise 1 "
            + PREFIX_DATE + "31-12-2025";

    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Error: This assignment already exists in the student.\n"
            + "Assignments of the same students cannot have the same name.\n"
            + "Please use the edit_assignment command to edit the assignment.";
    public static final String MESSAGE_EMPTY_STUDENT_LIST = "Error: Student list is empty";

    private static final Logger logger = LogsCenter.getLogger(AddAssignmentCommand.class);

    private final Index index;
    private final Assignment assignment;
    private final Date dueDate;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment} to the student at the specified index.
     */
    public AddAssignmentCommand(Index index, Assignment assignment) {
        requireAllNonNull(index, assignment, assignment.getDueDate());
        this.index = index;
        this.assignment = assignment;
        this.dueDate = assignment.getDueDate();
        logger.info("AddAssignmentCommand created for student index: " + index.getOneBased()
                + " with assignment: " + assignment);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AddAssignmentCommand for student index: " + index.getOneBased());

        List<Student> lastShownList = model.getFilteredStudentList();

        if (lastShownList.isEmpty()) {
            logger.warning("Attempted to add assignment to empty student list");
            throw new CommandException(MESSAGE_EMPTY_STUDENT_LIST);
        }

        // Check if the index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid student index: " + index.getOneBased()
                    + " (list size: " + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        assert studentToEdit != null : "Student should not be null";

        // Check if the assignment already exists
        if (studentToEdit.getAssignments().contains(assignment)) {
            logger.warning("Duplicate assignment detected: " + assignment + " for student: "
                    + studentToEdit.getName());
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        // Add the new assignment to the student
        if (studentToEdit.hasAssignment(assignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        Student editedStudent = studentToEdit.addAssignment(assignment);
        logger.info("Added assignment: " + assignment + " to student: " + studentToEdit.getName());

        // Update the model with the edited student
        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        String successMessage = generateSuccessMessage(editedStudent);
        logger.info("Success: " + successMessage);
        return new CommandResult(successMessage, true);
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
        return index.equals(e.index)
                && assignment.equals(e.assignment)
                && dueDate.equals(e.dueDate);
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{index=" + index
                + ", assignment=" + assignment
                + ", dueDate=" + assignment.getDueDate() + "}";
    }

}
