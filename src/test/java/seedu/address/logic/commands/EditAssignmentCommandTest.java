package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT2;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noFieldSpecified_throwsCommandException() {
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignmentToEdit = ASSIGNMENT1;
        studentToEdit.clearAssignments();
        studentToEdit.addAssignment(assignmentToEdit);


        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor();
        EditAssignmentCommand editAssignmentCommand =
                new EditAssignmentCommand(INDEX_FIRST, assignmentToEdit.getAssignmentName(), descriptor);

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignment1 = ASSIGNMENT1;
        Assignment assignment2 = ASSIGNMENT2;
        studentToEdit.clearAssignments();
        studentToEdit.addAssignment(assignment1);
        studentToEdit.addAssignment(assignment2);


        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(assignment2).build();
        EditAssignmentCommand editAssignmentCommand =
                new EditAssignmentCommand(INDEX_FIRST, assignment1.getAssignmentName(), descriptor);

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build();
        EditAssignmentCommand editAssignmentCommand =
                new EditAssignmentCommand(outOfBoundIndex, "Assignment", descriptor);

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_assignmentNotFound_throwsCommandException() {
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignmentToEdit = ASSIGNMENT1;
        studentToEdit.clearAssignments();
        studentToEdit.addAssignment(assignmentToEdit);

        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build();
        EditAssignmentCommand editAssignmentCommand =
                new EditAssignmentCommand(INDEX_FIRST, "Non-existent Assignment", descriptor);

        assertCommandFailure(editAssignmentCommand, model,
                String.format(Messages.MESSAGE_ASSIGNMENT_NOT_FOUND, "Non-existent Assignment"));
    }

    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand =
                new EditAssignmentCommand(INDEX_FIRST, "Assignment",
                        new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build());

        // same values -> returns true
        EditAssignmentCommand.EditAssignmentDescriptor copyDescriptor =
                new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build();
        EditAssignmentCommand commandWithSameValues =
                new EditAssignmentCommand(INDEX_FIRST, "Assignment", copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new EditAssignmentCommand(INDEX_SECOND, "Assignment", copyDescriptor)));

        // different assignment name -> returns false
        assertFalse(standardCommand.equals(
                new EditAssignmentCommand(INDEX_FIRST, "Different Assignment", copyDescriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditAssignmentCommand(INDEX_FIRST, "Assignment",
                        new EditAssignmentDescriptorBuilder().withNewAssignmentName("Different Name").build())));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor =
                new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build();
        EditAssignmentCommand editAssignmentCommand =
                new EditAssignmentCommand(index, "Assignment", editAssignmentDescriptor);
        String expected = EditAssignmentCommand.class.getCanonicalName() + "{index=" + index
                + ", assignmentName=Assignment"
                + ", editAssignmentDescriptor=" + editAssignmentDescriptor + "}";
        assertEquals(expected, editAssignmentCommand.toString());
    }

    @Test
    public void createEditedAssignment_allFieldsEdited() {
        Assignment original = new AssignmentBuilder().withAssignmentName("Original")
                .withDueDate(new Date("25-01-2026")).build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder()
                        .withNewAssignmentName("Edited")
                        .withDate("20-12-2026")
                        .build();
        Assignment edited = new EditAssignmentCommand(INDEX_FIRST, "name", descriptor)
                .createEditedAssignment(original, descriptor);

        assertEquals("Edited", edited.getAssignmentName());
        assertEquals(new Date("20-12-2026"), edited.getDueDate());
    }

    @Test
    public void editAssignmentDescriptor_isAnyFieldEdited() {
        // No fields edited
        assertFalse(new EditAssignmentCommand.EditAssignmentDescriptor().isAnyFieldEdited());

        // Name edited
        EditAssignmentCommand.EditAssignmentDescriptor nameDescriptor =
                new EditAssignmentDescriptorBuilder().withNewAssignmentName("New Name").build();
        assertTrue(nameDescriptor.isAnyFieldEdited());

        // Date edited
        EditAssignmentCommand.EditAssignmentDescriptor dateDescriptor =
                new EditAssignmentDescriptorBuilder().withDate("31-12-2026").build();
        assertTrue(dateDescriptor.isAnyFieldEdited());

        // Both fields edited
        EditAssignmentCommand.EditAssignmentDescriptor bothDescriptor =
                new EditAssignmentDescriptorBuilder()
                        .withNewAssignmentName("New Name")
                        .withDate("31-12-2026")
                        .build();
        assertTrue(bothDescriptor.isAnyFieldEdited());
    }
}
