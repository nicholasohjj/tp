package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessWithUpdate;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

public class EditStudentCommandTest {

    private Model model;
    private TestLogHandler testLogHandler;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        testLogHandler = new TestLogHandler();
        Logger logger = LogsCenter.getLogger(EditStudentCommand.class);
        logger.setUseParentHandlers(false); // Prevent default handlers from interfering
        logger.addHandler(testLogHandler);
        logger.setLevel(java.util.logging.Level.ALL); // Capture all log levels
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccessWithUpdate(editStudentCommand, model, expectedMessage, expectedModel);

        // Verify logging
        assertTrue(testLogHandler.containsMessage("Executing EditStudentCommand for student index: 1"));
        assertTrue(testLogHandler.containsMessage("Student successfully edited: " + editedStudent.getName()));
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedStudent = studentInList.withName("New Name").withPhone("12345678").build();

        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName("New Name")
                .withPhone("12345678")
                .build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(indexLastStudent, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(lastStudent, editedStudent);

        assertCommandSuccessWithUpdate(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_throwsCommandException() {
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST,
                new EditStudentCommand.EditStudentDescriptor());

        assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(INDEX_FIRST,
                new EditStudentDescriptorBuilder().withName("Alice").build());

        // same values -> returns true
        EditStudentCommand.EditStudentDescriptor copyDescriptor =
                new EditStudentDescriptorBuilder().withName("Alice").build();
        EditStudentCommand commandWithSameValues = new EditStudentCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_SECOND, copyDescriptor)));

        // different descriptor -> returns false
        EditStudentCommand.EditStudentDescriptor differentDescriptor =
                new EditStudentDescriptorBuilder().withName("Bob").build();
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_FIRST, differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditStudentCommand.EditStudentDescriptor editStudentDescriptor =
                new EditStudentDescriptorBuilder().withName("Alice").build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(index, editStudentDescriptor);
        String expected = EditStudentCommand.class.getCanonicalName() + "{index=" + index
                + ", editStudentDescriptor=" + editStudentDescriptor + "}";
        assertEquals(expected, editStudentCommand.toString());
    }

    @Test
    public void createEditedStudent_allFieldsEdited() throws CommandException {
        Student original = ALICE;
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(BENSON).build();
        Student edited = EditStudentCommand.createEditedStudent(original, descriptor);

        assertEquals(BENSON.getName(), edited.getName());
        assertEquals(BENSON.getPhone(), edited.getPhone());
        assertEquals(BENSON.getEmail(), edited.getEmail());
        assertEquals(BENSON.getAddress(), edited.getAddress());
        assertEquals(BENSON.getSubjects(), edited.getSubjects());
    }

    @Test
    public void editStudentDescriptor_isAnyFieldEdited() {
        // No fields edited
        assertFalse(new EditStudentCommand.EditStudentDescriptor().isAnyFieldEdited());

        // Name edited
        EditStudentCommand.EditStudentDescriptor nameDescriptor =
                new EditStudentDescriptorBuilder().withName("New Name").build();
        assertTrue(nameDescriptor.isAnyFieldEdited());

        // Phone edited
        EditStudentCommand.EditStudentDescriptor phoneDescriptor =
                new EditStudentDescriptorBuilder().withPhone("12345678").build();
        assertTrue(phoneDescriptor.isAnyFieldEdited());

        // Email edited
        EditStudentCommand.EditStudentDescriptor emailDescriptor =
                new EditStudentDescriptorBuilder().withEmail("new@email.com").build();
        assertTrue(emailDescriptor.isAnyFieldEdited());

        // Address edited
        EditStudentCommand.EditStudentDescriptor addressDescriptor =
                new EditStudentDescriptorBuilder().withAddress("New Address").build();
        assertTrue(addressDescriptor.isAnyFieldEdited());

        EditStudentCommand.EditStudentDescriptor subjectsDescriptor =
                new EditStudentDescriptorBuilder().withSubjects("Math").build();
        assertTrue(subjectsDescriptor.isAnyFieldEdited());
    }

    @Test
    public void editStudentDescriptor_getters() {
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentCommand.EditStudentDescriptor();

        // Test all getters with empty descriptor
        assertTrue(descriptor.getName().isEmpty());
        assertTrue(descriptor.getPhone().isEmpty());
        assertTrue(descriptor.getEmail().isEmpty());
        assertTrue(descriptor.getAddress().isEmpty());
        assertTrue(descriptor.getSubjects().isEmpty());
        assertTrue(descriptor.getAssignments().isEmpty());

        // Test with populated descriptor
        Student student = ALICE;
        descriptor = new EditStudentDescriptorBuilder(student).build();
        assertEquals(Optional.of(student.getName()), descriptor.getName());
        assertEquals(Optional.of(student.getPhone()), descriptor.getPhone());
        assertEquals(Optional.of(student.getEmail()), descriptor.getEmail());
        assertEquals(Optional.of(student.getAddress()), descriptor.getAddress());
        assertEquals(Optional.of(student.getSubjects()), descriptor.getSubjects());
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
