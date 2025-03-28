package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAssignmentCommand object
 */
public class MarkAssignmentCommandParser implements Parser<MarkAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAssignmentCommand
     * and returns a MarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAssignmentCommand.MESSAGE_USAGE), ive);
        }

        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("");

        return new MarkAssignmentCommand(index, assignmentName);
    }
}
