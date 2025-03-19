package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_LESSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.testutil.TypicalLessons.ALICE;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListLessonsCommand.
 */
public class ListLessonsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ListLessonsCommandParserStub parser = new ListLessonsCommandParserStub();

    @Test
    public void equals() {
        Predicate<Lesson> firstPredicate = parser.parse("first");
        Predicate<Lesson> secondPredicate = parser.parse("second");

        ListLessonsCommand listLessonsFirstCommand = new ListLessonsCommand(firstPredicate);
        ListLessonsCommand listLessonsSecondCommand = new ListLessonsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listLessonsFirstCommand.equals(listLessonsFirstCommand));

        // same values -> returns true
        ListLessonsCommand findFirstCommandCopy = new ListLessonsCommand(firstPredicate);
        assertTrue(listLessonsFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(listLessonsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listLessonsFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(listLessonsFirstCommand.equals(listLessonsSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_showsEverything() {
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 8);
        CommandResult expectedResult = new CommandResult(expectedMessage, true);
        Predicate<Lesson> predicate = parser.parse("");
        ListLessonsCommand command = new ListLessonsCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_oneKeyword_lessonFound() {
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 1);
        CommandResult expectedResult = new CommandResult(expectedMessage, true);
        Predicate<Lesson> predicate = parser.parse("Alice");
        ListLessonsCommand command = new ListLessonsCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredLessonList());
    }

    @Test
    public void toStringMethod() {
        Predicate<Lesson> predicate = parser.parse("keyword");
        ListLessonsCommand listLessonsCommand = new ListLessonsCommand(predicate);
        String expected = ListLessonsCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, listLessonsCommand.toString());
    }

    /**
     * A ListLessonsCommandParser stub to help with testing of methods
     */
    private class ListLessonsCommandParserStub {
        public Predicate<Lesson> parse(String input) {
            if (input.isEmpty()) {
                return PREDICATE_SHOW_ALL_LESSONS;
            } else {
                return lesson -> StringUtil.containsWordIgnoreCase(lesson.getName().fullName, input);
            }
        }
    }
}
