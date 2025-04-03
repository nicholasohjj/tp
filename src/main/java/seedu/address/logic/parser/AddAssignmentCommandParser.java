package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddAssignmentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddAssignmentCommand
     * and returns an AddAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing AddAssignmentCommand with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_DATE);

        if (!argMultimap.arePrefixesPresent(PREFIX_ASSIGNMENT, PREFIX_DATE)
                || argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for AddAssignmentCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT, PREFIX_DATE);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String assignmentName = ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).orElse(""));
            Date dueDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(""));

            assert assignmentName != null && !assignmentName.isEmpty() : "Assignment name cannot be null or empty";
            assert dueDate != null : "Due date cannot be null";

            Assignment assignment = new Assignment(assignmentName, dueDate);
            logger.info("Successfully parsed AddAssignmentCommand with index: " + index.getOneBased()
                    + ", assignment: " + assignment);
            return new AddAssignmentCommand(index, assignment);
        } catch (ParseException pe) {
            logger.warning("Error parsing AddAssignmentCommand: " + pe.getMessage());
            throw pe;
        }
    }
}
