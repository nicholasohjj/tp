package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

public class DeleteAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAssignmentCommand(null, "Assignment 1"));
        assertThrows(NullPointerException.class, () -> new DeleteAssignmentCommand(INDEX_FIRST, null));
    }

    @Test
    public void execute_emptyStudentList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        DeleteAssignmentCommand command = new DeleteAssignmentCommand(INDEX_FIRST, "Assignment 1");

        assertThrows(CommandException.class,
                DeleteAssignmentCommand.MESSAGE_EMPTY_STUDENT_LIST, () ->
                        command.execute(emptyModel));
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteAssignmentCommand command = new DeleteAssignmentCommand(outOfBoundIndex, "Assignment 1");

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () ->
                        command.execute(model));
    }

    @Test
    public void execute_assignmentNotFound_throwsCommandException() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        String nonExistentAssignment = "Nonexistent Assignment";
        DeleteAssignmentCommand command = new DeleteAssignmentCommand(INDEX_FIRST, nonExistentAssignment);

        assertThrows(CommandException.class,
                DeleteAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED, () ->
                        command.execute(model));
    }

    @Test
    public void execute_validAssignment_success() throws Exception {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignmentToDelete = ASSIGNMENT1;
        student.clearAssignments();
        student.addAssignment(assignmentToDelete);

        DeleteAssignmentCommand command = new DeleteAssignmentCommand(INDEX_FIRST,
                assignmentToDelete.getAssignmentName());
        CommandResult result = command.execute(model);

        assertFalse(student.getAssignments().contains(assignmentToDelete));
        assertEquals(String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                        Messages.format(student, assignmentToDelete)),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        DeleteAssignmentCommand deleteFirstCommand = new DeleteAssignmentCommand(INDEX_FIRST, "Assignment 1");
        DeleteAssignmentCommand deleteSecondCommand = new DeleteAssignmentCommand(INDEX_SECOND, "Assignment 1");
        DeleteAssignmentCommand deleteDifferentAssignmentCommand =
                new DeleteAssignmentCommand(INDEX_FIRST, "Assignment 2");

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAssignmentCommand deleteFirstCommandCopy = new DeleteAssignmentCommand(INDEX_FIRST, "Assignment 1");
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different assignment name -> returns false
        assertFalse(deleteFirstCommand.equals(deleteDifferentAssignmentCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteAssignmentCommand command = new DeleteAssignmentCommand(INDEX_FIRST, "Assignment 1");
        String expected = DeleteAssignmentCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST
                + ", assignmentName=Assignment 1}";
        assertEquals(expected, command.toString());
    }
}
