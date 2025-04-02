package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

public class AddAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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

        assertThrows(CommandException.class, AddAssignmentCommand
                .MESSAGE_DUPLICATE_ASSIGNMENT, () -> addAssignmentCommand.execute(model));
    }
}
