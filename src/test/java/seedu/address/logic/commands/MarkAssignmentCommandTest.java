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

public class MarkAssignmentCommandTest {
    private Model model;
    private TestLogHandler testLogHandler;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        testLogHandler = new TestLogHandler();
        Logger logger = LogsCenter.getLogger(MarkAssignmentCommand.class);
        logger.setUseParentHandlers(false); // Prevent default handlers from interfering
        logger.addHandler(testLogHandler);
        logger.setLevel(java.util.logging.Level.ALL); // Capture all log levels
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(outOfBoundIndex, "Assignment");

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // Verify logging
        assertTrue(testLogHandler.containsMessage(
                String.format("Invalid student index: %d (list size: %d)",
                        outOfBoundIndex.getOneBased(), model.getFilteredStudentList().size())));
    }

    @Test
    public void execute_emptyStudentList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(INDEX_FIRST, "Assignment");

        assertCommandFailure(markCommand, emptyModel, MarkAssignmentCommand.MESSAGE_EMPTY_STUDENT_LIST);

        // Verify logging
        assertTrue(testLogHandler.containsMessage("Attempted to mark assignment in empty student list"));
    }

    @Test
    public void execute_assignmentNotFound_throwsCommandException() {
        Student student = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        String nonExistentAssignment = "Nonexistent Assignment";
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(INDEX_FIRST, nonExistentAssignment);

        String expectedMessage = String.format(MarkAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT, nonExistentAssignment);
        assertCommandFailure(markCommand, model, expectedMessage);

        // Verify logging
        assertTrue(testLogHandler.containsMessage(
                String.format("Assignment not found: %s for student %s",
                        nonExistentAssignment, student.getName())));
    }

    @Test
    public void equals() {
        MarkAssignmentCommand markFirstCommand = new MarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT1.getAssignmentName());
        MarkAssignmentCommand markSecondCommand = new MarkAssignmentCommand(INDEX_SECOND,
                ASSIGNMENT1.getAssignmentName());
        MarkAssignmentCommand markDifferentAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT2.getAssignmentName());

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkAssignmentCommand markFirstCommandCopy = new MarkAssignmentCommand(INDEX_FIRST,
                ASSIGNMENT1.getAssignmentName());
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different student index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));

        // different assignment name -> returns false
        assertFalse(markFirstCommand.equals(markDifferentAssignmentCommand));
    }

    @Test
    public void toStringMethod() {
        MarkAssignmentCommand markCommand = new MarkAssignmentCommand(INDEX_FIRST, ASSIGNMENT1.getAssignmentName());
        String expected = MarkAssignmentCommand.class.getCanonicalName() + "{studentIndex=" + INDEX_FIRST
                + ", assignmentName=" + ASSIGNMENT1.getAssignmentName() + "}";
        assertEquals(expected, markCommand.toString());
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
