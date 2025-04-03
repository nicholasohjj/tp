package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAssignmentCommand object
 */
public class MarkAssignmentCommandParser implements Parser<MarkAssignmentCommand> {
    private static final Logger logger = LogsCenter.getLogger(MarkAssignmentCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAssignmentCommand
     * and returns a MarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAssignmentCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing MarkAssignmentCommand with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT);
        if (!argMultimap.arePrefixesPresent(PREFIX_ASSIGNMENT)
                || argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for MarkAssignmentCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAssignmentCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT);
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String assignmentName = ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).get());

            assert assignmentName != null && !assignmentName.isEmpty() : "Assignment name cannot be null or empty";

            logger.info("Successfully parsed MarkAssignmentCommand for index: "
                    + index.getOneBased() + " and assignment: " + assignmentName);
            return new MarkAssignmentCommand(index, assignmentName);
        } catch (ParseException pe) {
            logger.warning("Error parsing MarkAssignmentCommand: " + pe.getMessage());
            throw pe;
        } catch (NullPointerException npe) {
            logger.severe("Null value encountered while parsing MarkAssignmentCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAssignmentCommand.MESSAGE_USAGE));
        }
    }

}
