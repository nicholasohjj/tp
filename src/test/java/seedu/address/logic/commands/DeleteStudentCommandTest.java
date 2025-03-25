package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));
        CommandResult expectedResult = new CommandResult(expectedMessage, true);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        CommandResult expectedResult = new CommandResult(expectedMessage, true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        assertCommandSuccess(deleteStudentCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstStudentCommand = new DeleteStudentCommand(INDEX_FIRST);
        DeleteStudentCommand deleteSecondStudentCommand = new DeleteStudentCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstStudentCommand.equals(deleteFirstStudentCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstStudentCommandCopy = new DeleteStudentCommand(INDEX_FIRST);
        assertTrue(deleteFirstStudentCommand.equals(deleteFirstStudentCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstStudentCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstStudentCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstStudentCommand.equals(deleteSecondStudentCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(targetIndex);
        String expected = DeleteStudentCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteStudentCommand.toString());
    }

    @Test
    public void execute_deleteStudentWithNoLessons_onlyStudentDeleted() {
        // Add a student with no lessons
        Student studentWithoutLessons = new StudentBuilder().withName("Charlie NoLessons").build();
        model.addStudent(studentWithoutLessons);

        int initialLessonCount = model.getFilteredLessonList().size();
        Index index = Index.fromOneBased(model.getFilteredStudentList().size());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(index);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentWithoutLessons);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentWithoutLessons));

        assertCommandSuccess(deleteStudentCommand, model,
                new CommandResult(expectedMessage, true), expectedModel);

        // Verify lesson count remains the same
        assertEquals(initialLessonCount, model.getFilteredLessonList().size());
    }


    @Test
    public void execute_deleteFromEmptyList_throwsCommandException() {
        model.updateFilteredStudentList(p -> false); // Empty the list
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals_sameIndexDifferentObject_returnsTrue() {
        DeleteStudentCommand command1 = new DeleteStudentCommand(INDEX_FIRST);
        DeleteStudentCommand command2 = new DeleteStudentCommand(INDEX_FIRST);

        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentCommandType_returnsFalse() {
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(INDEX_FIRST);
        Object notACommand = new Object();

        assertFalse(deleteCommand.equals(notACommand));
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        DeleteStudentCommand command1 = new DeleteStudentCommand(INDEX_FIRST);
        DeleteStudentCommand command2 = new DeleteStudentCommand(INDEX_SECOND);

        assertFalse(command1.equals(command2));
    }


    @Test
    public void toString_containsCorrectInformation() {
        Index targetIndex = Index.fromOneBased(5);
        DeleteStudentCommand command = new DeleteStudentCommand(targetIndex);

        String expected = new ToStringBuilder(command)
                .add("targetIndex", targetIndex)
                .toString();

        assertEquals(expected, command.toString());
    }

    @Test
    public void execute_commandResultHasCorrectFormat() throws Exception {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);
        CommandResult result = deleteStudentCommand.execute(model);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
