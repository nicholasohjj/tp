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

/**
 * Deletes a lesson identified using it's displayed index from the address book.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "delete_lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by the index number used in the displayed lesson list.\n"
            + "Parameters: LESSON INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteLessonCommand.class);

    private final Index targetIndex;

    /**
     * Creates a DeleteLessonCommand to delete the specified {@code Lesson}.
     *
     * @param targetIndex of the lesson in the filtered lesson list to delete
     */
    public DeleteLessonCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        logger.info("DeleteLessonCommand created for index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeleteLessonCommand for index: " + targetIndex.getOneBased());

        List<Lesson> lastShownList = model.getFilteredLessonList();
        if (model.isStudentView()) {
            throw new CommandException(Messages.MESSAGE_LESSON_VIEW_REQUIRED);
        }

        if (lastShownList.isEmpty()) {
            logger.warning("Attempted to delete from empty lesson list");
            throw new CommandException(Messages.MESSAGE_EMPTY_LESSON_LIST);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid lesson index: " + targetIndex.getOneBased()
                    + " (list size: " + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToDelete = lastShownList.get(targetIndex.getZeroBased());
        assert lessonToDelete != null : "Lesson to delete should not be null";

        model.deleteLesson(lessonToDelete);
        logger.info("Successfully deleted lesson: " + lessonToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, Messages.format(lessonToDelete)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        DeleteLessonCommand otherDeleteLessonCommand = (DeleteLessonCommand) other;
        return targetIndex.equals(otherDeleteLessonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
