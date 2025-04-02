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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;

public class UnmarkAssignmentCommandTest {
    private Model model;
    private TestLogHandler testLogHandler;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        testLogHandler = new TestLogHandler();
        Logger logger = LogsCenter.getLogger(UnmarkAssignmentCommand.class);
        logger.setUseParentHandlers(false); // Prevent default handlers from interfering
        logger.addHandler(testLogHandler);
        logger.setLevel(java.util.logging.Level.ALL); // Capture all log levels
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(outOfBoundIndex, "Assignment");

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // Verify logging
        assertTrue(testLogHandler.containsMessage(
                String.format("Invalid student index: %d (list size: %d)",
                        outOfBoundIndex.getOneBased(), model.getFilteredStudentList().size())));
    }

    @Test
    public void execute_emptyStudentList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(INDEX_FIRST, "Assignment");

        assertCommandFailure(unmarkCommand, emptyModel, UnmarkAssignmentCommand.MESSAGE_EMPTY_STUDENT_LIST);

        // Verify logging
        assertTrue(testLogHandler.containsMessage("Attempted to unmark assignment in empty student list"));
    }

    @Test
    public void execute_assignmentNotFound_throwsCommandException() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        String nonExistentAssignment = "Nonexistent Assignment";
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(INDEX_FIRST, nonExistentAssignment);

        String expectedMessage = String.format(UnmarkAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT,
                nonExistentAssignment);
        assertCommandFailure(unmarkCommand, model, expectedMessage);

        // Verify logging
        assertTrue(testLogHandler.containsMessage(
                String.format("Assignment not found: %s for student %s",
                        nonExistentAssignment, student.getName())));
    }

    @Test
    public void equals() {
        UnmarkAssignmentCommand unmarkFirstCommand = new UnmarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT1.getAssignmentName());
        UnmarkAssignmentCommand unmarkSecondCommand = new UnmarkAssignmentCommand(INDEX_SECOND,
                ASSIGNMENT1.getAssignmentName());
        UnmarkAssignmentCommand unmarkDifferentAssignmentCommand = new UnmarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT2.getAssignmentName());

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkAssignmentCommand unmarkFirstCommandCopy = new UnmarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT1.getAssignmentName());
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different student index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));

        // different assignment name -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkDifferentAssignmentCommand));
    }

    @Test
    public void toStringMethod() {
        UnmarkAssignmentCommand unmarkCommand = new UnmarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT1.getAssignmentName());
        String expected = UnmarkAssignmentCommand.class.getCanonicalName() + "{studentIndex=" + INDEX_FIRST
                + ", assignmentName=" + ASSIGNMENT1.getAssignmentName() + "}";
        assertEquals(expected, unmarkCommand.toString());
    }

    // Helper class to test logging
    private static class TestLogHandler extends Handler {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            messages.add(record.getLevel() + ": " + record.getMessage());
        }

        @Override
        public void flush() {}

        public boolean containsMessage(String expectedMessagePart) {
            return messages.stream().anyMatch(message -> message.contains(expectedMessagePart));
        }

        @Override
        public void close() throws SecurityException {}

        public void printMessages() {
            messages.forEach(System.out::println); // debugging purpose
        }
    }
}
