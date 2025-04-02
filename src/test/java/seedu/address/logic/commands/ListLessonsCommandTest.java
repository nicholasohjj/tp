package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.StudentNameLessonPredicate;
import seedu.address.testutil.TypicalStudents;

public class ListLessonsCommandTest {
    private Model model;
    private Model expectedModel;
    private TestLogHandler testLogHandler;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        testLogHandler = new TestLogHandler();
        Logger logger = LogsCenter.getLogger(ListLessonsCommand.class);
        logger.setUseParentHandlers(false); // Prevent default handlers from interfering
        logger.addHandler(testLogHandler);
        logger.setLevel(java.util.logging.Level.ALL); // Capture all log levels
    }

    @Test
    public void equals() {
        StudentNameLessonPredicate firstPredicate = new StudentNameLessonPredicate(ALICE.getName());
        StudentNameLessonPredicate secondPredicate = new StudentNameLessonPredicate(BENSON.getName());

        ListLessonsCommand listAliceLessonsCommand = new ListLessonsCommand(firstPredicate);
        ListLessonsCommand listBensonLessonsCommand = new ListLessonsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listAliceLessonsCommand.equals(listAliceLessonsCommand));

        // same values -> returns true
        ListLessonsCommand listAliceLessonsCommandCopy = new ListLessonsCommand(firstPredicate);
        assertTrue(listAliceLessonsCommand.equals(listAliceLessonsCommandCopy));

        // different types -> returns false
        assertFalse(listAliceLessonsCommand.equals(1));

        // null -> returns false
        assertFalse(listAliceLessonsCommand.equals(null));

        // different predicate -> returns false
        assertFalse(listAliceLessonsCommand.equals(listBensonLessonsCommand));
    }

    @Test
    public void equals_withStudentName() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(ALICE.getName());

        ListLessonsCommand commandWithName = new ListLessonsCommand(predicate, ALICE.getName());
        ListLessonsCommand commandWithoutName = new ListLessonsCommand(predicate);

        // Different because one has student name and other doesn't
        assertFalse(commandWithName.equals(commandWithoutName));
    }

    @Test
    public void toStringMethod() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(ALICE.getName());
        ListLessonsCommand command = new ListLessonsCommand(predicate);

        String expected = ListLessonsCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void toStringMethod_withStudentName() {
        StudentNameLessonPredicate predicate = new StudentNameLessonPredicate(ALICE.getName());
        ListLessonsCommand command = new ListLessonsCommand(predicate, ALICE.getName());

        String expected = ListLessonsCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
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
