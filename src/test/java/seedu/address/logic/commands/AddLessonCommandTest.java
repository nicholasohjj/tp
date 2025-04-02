package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MATH_LEARNING;
import static seedu.address.testutil.TypicalLessons.SCIENCE_TEACHING;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddLessonCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addStudent(ALICE);
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null));
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lesson = new LessonBuilder().withName(ALICE.getName().toString()).build();
        model.addLesson(lesson);
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        assertThrows(CommandException.class,
                AddLessonCommand.MESSAGE_DUPLICATE_LESSON, () ->
                        addLessonCommand.execute(model));
    }

    @Test
    public void execute_lessonConflict_throwsCommandException() {
        // Add a lesson that conflicts with the new lesson
        Lesson existingLesson = new LessonBuilder()
                .withName(ALICE.getName().toString())
                .withDate("17-09-2027")
                .withTime("14:00")
                .build();
        model.addLesson(existingLesson);

        Lesson conflictingLesson = new LessonBuilder()
                .withName(BOB.getName().toString())
                .withDate("17-09-2027")
                .withTime("14:00")
                .build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(conflictingLesson);

        assertThrows(CommandException.class,
                AddLessonCommand.MESSAGE_LESSON_CONFLICT, () ->
                        addLessonCommand.execute(model));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        Lesson lesson = new LessonBuilder().withName("Nonexistent Student").build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        assertThrows(CommandException.class,
                AddLessonCommand.MESSAGE_STUDENT_NOT_FOUND, () ->
                        addLessonCommand.execute(model));
    }

    @Test
    public void execute_subjectMismatch_throwsCommandException() {
        // Create a student without the lesson's subject
        Student studentWithoutSubject = new StudentBuilder(ALICE)
                .withSubjects("Math")
                .build();
        model.setStudent(ALICE, studentWithoutSubject);

        Lesson lesson = new LessonBuilder()
                .withName(ALICE.getName().toString())
                .withSubject("Science")
                .build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        assertThrows(CommandException.class,
                AddLessonCommand.MESSAGE_SUBJECT_MISMATCH, () ->
                        addLessonCommand.execute(model));
    }

    @Test
    public void execute_validLesson_success() throws Exception {
        Lesson validLesson = new LessonBuilder()
                .withName(ALICE.getName().toString())
                .withSubject("CS2109S")
                .build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);

        CommandResult commandResult = addLessonCommand.execute(model);

        assertTrue(model.hasLesson(validLesson));
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, Messages.format(validLesson)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AddLessonCommand addMathCommand = new AddLessonCommand(MATH_LEARNING);
        AddLessonCommand addScienceCommand = new AddLessonCommand(SCIENCE_TEACHING);

        // same object -> returns true
        assertTrue(addMathCommand.equals(addMathCommand));

        // same values -> returns true
        AddLessonCommand addMathCommandCopy = new AddLessonCommand(MATH_LEARNING);
        assertTrue(addMathCommand.equals(addMathCommandCopy));

        // different types -> returns false
        assertFalse(addMathCommand.equals(1));

        // null -> returns false
        assertFalse(addMathCommand.equals(null));

        // different lesson -> returns false
        assertFalse(addMathCommand.equals(addScienceCommand));
    }

    @Test
    public void toStringMethod() {
        AddLessonCommand addLessonCommand = new AddLessonCommand(MATH_LEARNING);
        String expected = new ToStringBuilder(addLessonCommand)
                .add("toAdd", MATH_LEARNING)
                .toString();
        assertEquals(expected, addLessonCommand.toString());
    }
}
