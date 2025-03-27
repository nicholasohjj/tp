package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditLessonCommand.
 */
public class EditLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.addStudent(AMY);
        model.addStudent(BOB);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = new LessonBuilder().build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));

        CommandResult expectedResult = new CommandResult(expectedMessage, true);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertCommandSuccess(editLessonCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());
        Lesson lastLesson = model.getFilteredLessonList().get(indexLastLesson.getZeroBased());

        LessonBuilder lessonInList = new LessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
                .build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(indexLastLesson, descriptor);

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));
        CommandResult expectedResult = new CommandResult(expectedMessage, true);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lastLesson, editedLesson);

        assertCommandSuccess(editLessonCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST, new EditLessonDescriptor());
        Lesson editedLesson = model.getFilteredLessonList()
                .get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));

        CommandResult expectedResult = new CommandResult(expectedMessage, true);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(editLessonCommand, model, expectedResult, expectedModel);
    }

    //    @Test
    //    public void execute_filteredList_success() {
    //        showLessonAtIndex(model, INDEX_FIRST);
    //
    //        Lesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST.getZeroBased());
    //        Lesson editedLesson = new LessonBuilder(lessonInFilteredList).withName(VALID_NAME_BOB).build();
    //        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST,
    //                new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
    //                Messages.format(editedLesson));
    //        CommandResult expectedResult = new CommandResult(expectedMessage, true);
    //
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);
    //
    //        assertCommandSuccess(editLessonCommand, model, expectedResult, expectedModel);
    //    }

    @Test
    public void execute_duplicateLessonUnfilteredList_failure() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST.getZeroBased());
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(firstLesson).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editLessonCommand, model, EditLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    //    @Test
    //    public void execute_duplicateLessonFilteredList_failure() {
    //        showLessonAtIndex(model, INDEX_FIRST);
    //
    //        // edit lesson in filtered list into a duplicate in address book
    //        Lesson lessonInList = model.getAddressBook().getLessonList().get(INDEX_SECOND.getZeroBased());
    //        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST,
    //                new EditLessonDescriptorBuilder(lessonInList).build());
    //
    //        assertCommandFailure(editLessonCommand, model, EditLessonCommand.MESSAGE_DUPLICATE_LESSON);
    //    }

    @Test
    public void execute_invalidLessonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    //    /**
    //     * Edit filtered list where index is larger than size of filtered list,
    //     * but smaller than size of address book
    //     */
    //    @Test
    //    public void execute_invalidLessonIndexFilteredList_failure() {
    //        showLessonAtIndex(model, INDEX_FIRST);
    //        Index outOfBoundIndex = INDEX_SECOND;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());
    //
    //        EditLessonCommand editCommand = new EditLessonCommand(outOfBoundIndex,
    //                new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        final EditLessonCommand standardCommand = new EditLessonCommand(INDEX_FIRST, LESSON_DESC_AMY);

        // same values -> returns true
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptor(LESSON_DESC_AMY);
        EditLessonCommand commandWithSameValues = new EditLessonCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(INDEX_SECOND, LESSON_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(INDEX_FIRST, LESSON_DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        EditLessonCommand editCommand = new EditLessonCommand(index, editLessonDescriptor);
        String expected = EditLessonCommand.class.getCanonicalName() + "{index=" + index + ", editLessonDescriptor="
                + editLessonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
