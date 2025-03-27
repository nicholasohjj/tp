package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Finds and lists all lessons in address book whose student name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListLessonsCommand extends Command {

    public static final String COMMAND_WORD = "list_lessons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons whose student name contains"
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "If no keywords are specified, displays all lessons.\n"
            + "Parameters: KEYWORD + " + PREFIX_NAME + "[KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice or "
            + COMMAND_WORD;

    private final Predicate<Lesson> predicate;

    /**
     * Constructor for the ListLessonsCommand
     * @param predicate Predicate to filter the UniqueLessonList with
     */
    public ListLessonsCommand(Predicate<Lesson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLessonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LESSONS_LISTED_OVERVIEW, model.getFilteredLessonList().size()), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListLessonsCommand)) {
            return false;
        }

        ListLessonsCommand otherFindStudentCommand = (ListLessonsCommand) other;
        return predicate.equals(otherFindStudentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
