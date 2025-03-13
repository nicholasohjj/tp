package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class AddAssignmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToAddAssignment = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignment = new Assignment("assignment");
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT, assignment);
        Student studentWithAssignment = new StudentBuilder()
                .setAssignment(studentToAddAssignment, "assignment").build();

        String expectedMessage = String.format(Messages.MESSAGE_ADD_ASSIGNMENT_SUCCESS,
                studentWithAssignment.getName(), assignment);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddAssignment, studentWithAssignment);

        assertCommandSuccess(addAssignmentCommand, model, expectedMessage, expectedModel);
    }
}
