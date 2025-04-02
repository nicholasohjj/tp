package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindStudentCommand extends Command {

    public static final String COMMAND_WORD = "find_student";
    private static final Logger logger = Logger.getLogger(FindStudentCommand.class.getName());


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    /**
     * Constructs new FindStudentCommand with given predicate
     */
    public FindStudentCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        logger.log(Level.FINE, "FindStudentCommand created with predicate: {0}", predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing FindStudentCommand with predicate: {0}", predicate);

        model.updateFilteredStudentList(predicate);
        int filteredListSize = model.getFilteredStudentList().size();

        logger.log(Level.INFO, "FindStudentCommand found {0} students matching the criteria", filteredListSize);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindStudentCommand)) {
            return false;
        }

        FindStudentCommand otherFindStudentCommand = (FindStudentCommand) other;
        return predicate.equals(otherFindStudentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
