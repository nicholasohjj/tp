package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Assignment;

public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "add_assignment";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Assignment: %2$s";

    private final Index index;
    private final Assignment assignment;

    public AddAssignmentCommand(Index index, Assignment assignment) {
        requireAllNonNull(index, assignment);

        this.index = index;
        this.assignment = assignment;
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment to a student. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Math Exercise 1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Assign command has not been implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), assignment)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAssignmentCommand)) {
            return false;
        }

        AddAssignmentCommand e = (AddAssignmentCommand) other;
        return index.equals(e.index) && assignment.equals(e.assignment);
    }
}
