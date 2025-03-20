package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.expections.DuplicateAssignmentException;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

public class UnmarkAssignmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignment = new Assignment("Assignment 1", new Date("31-12-9999"));
        student.addAssignment(assignment);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_STUDENT,
                assignment.getAssignmentName());

        assertThrows(DuplicateAssignmentException.class,
                AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> markAssignmentCommand.execute(model));
    }
}
