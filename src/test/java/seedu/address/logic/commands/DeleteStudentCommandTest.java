package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
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
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

public class DeleteStudentCommandTest {

    private Model model;
    private TestLogHandler testLogHandler;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        testLogHandler = new TestLogHandler();
        Logger logger = LogsCenter.getLogger(DeleteStudentCommand.class);
        logger.setUseParentHandlers(false); // Prevent default handlers from interfering
        logger.addHandler(testLogHandler);
        logger.setLevel(java.util.logging.Level.ALL); // Capture all log levels
    }


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

        // Verify logging
        assertTrue(testLogHandler.containsMessage("Executing DeleteStudentCommand for index: 1"));
        assertTrue(testLogHandler.containsMessage("Successfully deleted student: " + studentToDelete.getName()));
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
        showStudentAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.deleteStudent(studentToDelete);

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
    public void execute_deleteStudentWithLessons_lessonsAlsoDeleted() throws Exception {
        // Add a lesson to the first student
        Student studentWithLessons = model.getFilteredStudentList()
                .get(INDEX_FIRST.getZeroBased());
        Lesson lesson = new Lesson(new Subject("Math"),
                studentWithLessons.getName(), new Date("01-01-2026"), new Time("12:00"));
        model.addLesson(lesson);

        int initialLessonCount = model.getFilteredLessonList().size();
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);
        deleteStudentCommand.execute(model);

        assertEquals(initialLessonCount - 1, model.getFilteredLessonList().size());

        // Verify logging for lesson deletion
        assertTrue(testLogHandler.containsMessage("Deleting 1 associated lessons"));
    }

    @Test
    public void execute_deleteFromEmptyList_throwsCommandException() {
        model.updateFilteredStudentList(p -> false); // Empty the list
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_EMPTY_STUDENT_LIST);

        // Verify logging
        assertTrue(testLogHandler.containsMessage("Attempted to delete from empty student list"));
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(INDEX_FIRST);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(targetIndex);
        String expected = DeleteStudentCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteStudentCommand.toString());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteStudentCommand(null));
    }

    @Test
    public void execute_commandResultHasCorrectFormat() throws Exception {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST);
        CommandResult result = deleteStudentCommand.execute(model);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isUpdateList());
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
