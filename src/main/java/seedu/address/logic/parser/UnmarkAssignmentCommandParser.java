package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnmarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkAssignmentCommand object
 */
public class UnmarkAssignmentCommandParser implements Parser<UnmarkAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAssignmentCommand
     * and returns a UnmarkAssignmentCommand object for execution.
     */
    public UnmarkAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAssignmentCommand.MESSAGE_USAGE), ive);
        }

        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("");

        return new UnmarkAssignmentCommand(index, assignmentName);
    }
}
