package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddAssignmentCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Assignment;

public class AddAssignmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Assignment assignment = new Assignment("some assignment");

        assertCommandFailure(new AddAssignmentCommand(INDEX_FIRST_STUDENT, assignment), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_STUDENT.getOneBased(), assignment));
    }

    @Test
    public void equals() {
        final AddAssignmentCommand standardCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                new Assignment(VALID_ASSIGNMENT_AMY));

        // same values -> returns true
        AddAssignmentCommand commandWithSameValues = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                new Assignment(VALID_ASSIGNMENT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                new Assignment(VALID_ASSIGNMENT_AMY))));

        // different assignment -> returns false
        assertFalse(standardCommand.equals(new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                new Assignment(VALID_ASSIGNMENT_BOB))));
    }
}
