package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
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
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

public class AddAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignment = new Assignment("Assignment 1", new Date("10-04-2025"));
        student.addAssignment(assignment);
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST, assignment);

        assertThrows(CommandException.class,
                AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () ->
                        addAssignmentCommand.execute(model));
    }

    @Test
    public void execute_emptyStudentList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        Assignment assignment = new Assignment("Assignment 1", new Date("10-04-2025"));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST, assignment);

        assertThrows(CommandException.class,
                AddAssignmentCommand.MESSAGE_EMPTY_STUDENT_LIST, () ->
                        addAssignmentCommand.execute(emptyModel));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Assignment assignment = new Assignment("Assignment 1", new Date("10-04-2025"));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(outOfBoundIndex, assignment);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INDEX_OUT_OF_BOUNDS, () ->
                        addAssignmentCommand.execute(model));
    }

    @Test
    public void execute_validAssignment_success() throws Exception {
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Assignment assignment = new Assignment("New Assignment", new Date("10-04-2025"));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST, assignment);

        CommandResult commandResult = addAssignmentCommand.execute(model);

        assertTrue(studentToEdit.getAssignments().contains(assignment));
        assertEquals(String.format(Messages.MESSAGE_ADD_ASSIGNMENT_SUCCESS,
                        studentToEdit.getName(), assignment),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Assignment assignment1 = new Assignment("Assignment 1", new Date("10-04-2025"));
        Assignment assignment2 = new Assignment("Assignment 2", new Date("11-04-2025"));
        AddAssignmentCommand addFirstCommand = new AddAssignmentCommand(INDEX_FIRST, assignment1);
        AddAssignmentCommand addSecondCommand = new AddAssignmentCommand(INDEX_SECOND, assignment1);
        AddAssignmentCommand addThirdCommand = new AddAssignmentCommand(INDEX_FIRST, assignment2);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        AddAssignmentCommand addFirstCommandCopy = new AddAssignmentCommand(INDEX_FIRST, assignment1);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));

        // different assignment -> returns false
        assertFalse(addFirstCommand.equals(addThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Assignment assignment = new Assignment("Assignment 1", new Date("10-04-2025"));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST, assignment);
        String expected = AddAssignmentCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST
                + ", assignment=" + assignment + ", dueDate=" + assignment.getDueDate() + "}";
        assertEquals(expected, addAssignmentCommand.toString());
    }
}
