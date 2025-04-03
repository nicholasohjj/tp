package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.commands.UnmarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.AssignmentUtil;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.LessonUtil;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addStudent() throws Exception {
        Student student = new StudentBuilder().build();
        AddStudentCommand command = (AddStudentCommand) parser.parseCommand(StudentUtil.getAddStudentCommand(student));
        assertEquals(new AddStudentCommand(student), command);
    }

    @Test
    public void parseCommand_addLesson() throws Exception {
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand command = (AddLessonCommand) parser.parseCommand(LessonUtil.getAddLessonCommand(lesson));
        assertEquals(new AddLessonCommand(lesson), command);
    }

    @Test
    public void parseCommand_addAssignment() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        AddAssignmentCommand command = (AddAssignmentCommand) parser
                .parseCommand(AssignmentUtil.getAddAssignmentCommand(assignment));
        assertEquals(new AddAssignmentCommand(Index.fromOneBased(1), assignment), command);
    }

    @Test
    public void parseCommand_deleteStudent() throws Exception {
        DeleteStudentCommand command = (DeleteStudentCommand) parser.parseCommand(
                DeleteStudentCommand.COMMAND_WORD + " 1");
        assertEquals(new DeleteStudentCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_deleteLesson() throws Exception {
        DeleteLessonCommand command = (DeleteLessonCommand) parser.parseCommand(
                DeleteLessonCommand.COMMAND_WORD + " 1");
        assertEquals(new DeleteLessonCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parseCommand_deleteAssignment() throws Exception {
        DeleteAssignmentCommand command = (DeleteAssignmentCommand) parser.parseCommand(
                DeleteAssignmentCommand.COMMAND_WORD + " 1" + ASSIGNMENT_NAME_DESC_AMY);
        assertEquals(new DeleteAssignmentCommand(Index.fromOneBased(1), VALID_ASSIGNMENT_NAME_AMY.trim()),
                command);
    }

    @Test
    public void parseCommand_editStudent() throws Exception {
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName("New Name")
                .withPhone("12345678")
                .withEmail("new@email.com")
                .withAddress("New Address")
                .build();
        EditStudentCommand command = (EditStudentCommand) parser.parseCommand(
                EditStudentCommand.COMMAND_WORD + " 1 "
                        + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditStudentCommand(Index.fromOneBased(1), descriptor), command);
    }

    @Test
    public void parseCommand_editLesson() throws Exception {
        Lesson lesson = new LessonBuilder().build();
        EditLessonCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson).build();
        EditLessonCommand command = (EditLessonCommand) parser.parseCommand(EditLessonCommand.COMMAND_WORD
                + " 1 "
                + LessonUtil.getEditLessonDescriptorDetails(descriptor));
        assertEquals(new EditLessonCommand(Index.fromOneBased(1), descriptor), command);
    }

    @Test
    public void parseCommand_editAssignment() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(assignment).build();
        EditAssignmentCommand command = (EditAssignmentCommand) parser.parseCommand(EditAssignmentCommand.COMMAND_WORD
                + " 1 "
                + ASSIGNMENT_NAME_DESC_AMY
                + AssignmentUtil.getEditAssignmentDescriptorDetails(descriptor));
        assertEquals(new EditAssignmentCommand(Index.fromOneBased(1), VALID_ASSIGNMENT_NAME_AMY.trim(), descriptor),
                command);
    }

    @Test
    public void parseCommand_markAssignment() throws Exception {
        MarkAssignmentCommand command = (MarkAssignmentCommand) parser.parseCommand(
                MarkAssignmentCommand.COMMAND_WORD + " 1 " + ASSIGNMENT_NAME_DESC_AMY);
        assertEquals(new MarkAssignmentCommand(Index.fromOneBased(1), VALID_ASSIGNMENT_NAME_AMY.trim()), command);
    }

    @Test
    public void parseCommand_unmarkAssignment() throws Exception {
        UnmarkAssignmentCommand command = (UnmarkAssignmentCommand) parser.parseCommand(
                UnmarkAssignmentCommand.COMMAND_WORD + " 1 " + ASSIGNMENT_NAME_DESC_AMY);
        assertEquals(new UnmarkAssignmentCommand(Index.fromOneBased(1), VALID_ASSIGNMENT_NAME_AMY.trim()), command);
    }

    @Test
    public void parseCommand_listLessons() throws Exception {
        assertTrue(parser.parseCommand(ListLessonsCommand.COMMAND_WORD) instanceof ListLessonsCommand);
        assertTrue(parser.parseCommand(ListLessonsCommand.COMMAND_WORD + NAME_DESC_AMY) instanceof ListLessonsCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindStudentCommand command = (FindStudentCommand) parser.parseCommand(
                FindStudentCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindStudentCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listStudent() throws Exception {
        assertTrue(parser.parseCommand(ListStudentsCommand.COMMAND_WORD) instanceof ListStudentsCommand);
        assertTrue(parser.parseCommand(ListStudentsCommand.COMMAND_WORD + " 3") instanceof ListStudentsCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
