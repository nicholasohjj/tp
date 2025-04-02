package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAssignmentCommand
     * and returns an AddAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_DATE);

        // Check if the prefixes are present
        if (!argMultimap.arePrefixesPresent(PREFIX_ASSIGNMENT, PREFIX_DATE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
        }

        // Check if there are duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT, PREFIX_DATE);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        if (argMultimap.getValue(PREFIX_ASSIGNMENT).filter(String::isEmpty).isPresent()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_FIELD, PREFIX_ASSIGNMENT));
        }

        if (argMultimap.getValue(PREFIX_DATE).filter(String::isEmpty).isPresent()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_FIELD, PREFIX_DATE));
        }

        String assignmentName = ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).orElse(""));
        Date dueDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(""));

        return new AddAssignmentCommand(index, new Assignment(assignmentName, dueDate));
    }
}
