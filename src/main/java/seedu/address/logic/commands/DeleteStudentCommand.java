package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteStudentCommand extends Command {

    public static final String COMMAND_WORD = "delete_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS =
            "Student %1$s\ndeleted successfully, along with all associated lessons and assignments.";

    private static final Logger logger = LogsCenter.getLogger(DeleteStudentCommand.class);

    private final Index targetIndex;

    /**
     * Creates a DeleteStudentCommand to delete the specified {@code Student}.
     *
     * @param targetIndex of the student in the filtered student list to delete
     */
    public DeleteStudentCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        logger.info("DeleteStudentCommand created for index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeleteStudentCommand for index: " + targetIndex.getOneBased());

        List<Student> lastShownList = model.getFilteredStudentList();

        if (lastShownList.isEmpty()) {
            logger.warning("Attempted to delete from empty student list");
            throw new CommandException(Messages.MESSAGE_EMPTY_STUDENT_LIST);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid student index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToDelete = lastShownList.get(targetIndex.getZeroBased());
        assert studentToDelete != null : "Student to delete should not be null";

        // Delete all lessons associated with the student
        List<Lesson> lessonsToDelete = model.getFilteredLessonList()
                .stream()
                .filter(lesson -> lesson.getStudentName().equals(studentToDelete.getName()))
                .toList();

        logger.info("Deleting " + lessonsToDelete.size() + " associated lessons");
        lessonsToDelete.forEach(lesson -> {
            model.deleteLesson(lesson);
            logger.fine("Deleted lesson: " + lesson);
        });

        // Finally, delete the student
        model.deleteStudent(studentToDelete);
        logger.info("Successfully deleted student: " + studentToDelete.getName());

        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, Messages.format(studentToDelete)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return targetIndex.equals(otherDeleteStudentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
