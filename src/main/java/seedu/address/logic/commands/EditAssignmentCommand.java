package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ASSIGNMENT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "edit_assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the assignment identified "
            + "by the index number of the student and the assignment name. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT_NAME "
            + "[" + PREFIX_NEW_ASSIGNMENT + "NEW_ASSIGNMENT_NAME] "
            + "[" + PREFIX_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Assignment 1 "
            + PREFIX_NEW_ASSIGNMENT + "Assignment 1a "
            + PREFIX_DATE + "30-09-2025";

    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book.";

    private final Index index;
    private final String assignmentName;
    private final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index of the student in the filtered student list to edit
     * @param editAssignmentDescriptor details to edit the assignment with
     */
    public EditAssignmentCommand(Index index, String assignmentName,EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(assignmentName);
        requireNonNull(editAssignmentDescriptor);

        this.index = index;
        this.assignmentName = assignmentName;
        this.editAssignmentDescriptor = new EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        UniqueAssignmentList assignmentsToEdit = studentToEdit.getAssignments();
        Assignment assignmentToEdit = assignmentsToEdit.getAssignment(assignmentName);
        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, editAssignmentDescriptor);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && assignmentsToEdit.contains(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        UniqueAssignmentList updatedAssignments = assignmentsToEdit;
        updatedAssignments.setAssignment(assignmentToEdit, editedAssignment);

        Student editedStudent = new Student(studentToEdit.getName(), studentToEdit.getPhone(), studentToEdit.getEmail(),
                studentToEdit.getAddress(), studentToEdit.getSubjects(), updatedAssignments);

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS,
                editedAssignment), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }

        // state check
        EditAssignmentCommand e = (EditAssignmentCommand) other;
        return index.equals(e.index)
                && assignmentName.equals(e.assignmentName)
                && editAssignmentDescriptor.equals(e.editAssignmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("assignmentName", assignmentName)
                .add("editAssignmentDescriptor", editAssignmentDescriptor)
                .toString();
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code assignmentToEdit}
     * edited with {@code editAssignmentDescriptor}.
     */
    private Assignment createEditedAssignment(Assignment assignmentToEdit,
                                              EditAssignmentDescriptor editAssignmentDescriptor) {
        assert assignmentToEdit != null;

        String updatedAssignmentName = editAssignmentDescriptor.getNewAssignmentName()
                .orElse(assignmentToEdit.getAssignmentName());
        Date updatedDate = editAssignmentDescriptor.getDate().orElse(assignmentToEdit.getDueDate());

        return new Assignment(updatedAssignmentName, updatedDate);
    }

    public static class EditAssignmentDescriptor {
        private String newAssignmentName;
        private Date date;

        public EditAssignmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
            setNewAssignmentName(toCopy.newAssignmentName);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return newAssignmentName != null || date != null;
        }

        public void setNewAssignmentName(String assignmentName) {
            this.newAssignmentName = assignmentName;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<String> getNewAssignmentName() {
            return Optional.ofNullable(newAssignmentName);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssignmentDescriptor)) {
                return false;
            }

            EditAssignmentDescriptor otherEditAssignmentDescriptor = (EditAssignmentDescriptor) other;
            return Objects.equals(newAssignmentName, otherEditAssignmentDescriptor.newAssignmentName)
                    && Objects.equals(date, otherEditAssignmentDescriptor.date);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("assignmentName", newAssignmentName)
                    .add("date", date)
                    .toString();
        }

    }
}
