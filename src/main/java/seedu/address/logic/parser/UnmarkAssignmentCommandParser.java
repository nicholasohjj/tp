package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkAssignmentCommand object
 */
public class UnmarkAssignmentCommandParser implements Parser<UnmarkAssignmentCommand> {
    private static final Logger logger = LogsCenter.getLogger(UnmarkAssignmentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAssignmentCommand
     * and returns a UnmarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkAssignmentCommand parse(String args) throws ParseException {
        logger.info("Parsing UnmarkAssignmentCommand with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT);

        if (!argMultimap.arePrefixesPresent(PREFIX_ASSIGNMENT) || argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for UnmarkAssignmentCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkAssignmentCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT);

            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String assignmentName = ParserUtil.parseAssignmentName(
                    argMultimap.getValue(PREFIX_ASSIGNMENT).orElseThrow(() -> {
                        logger.warning("Missing assignment name prefix");
                        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                UnmarkAssignmentCommand.MESSAGE_USAGE));
                    }));

            logger.info("Successfully parsed UnmarkAssignmentCommand for student " + index.getOneBased()
                    + ", assignment: " + assignmentName);
            return new UnmarkAssignmentCommand(index, assignmentName);
        } catch (ParseException pe) {
            logger.warning("Parse error in UnmarkAssignmentCommand: " + pe.getMessage());
            throw pe;
        } catch (Exception e) {
            logger.severe("Unexpected error parsing UnmarkAssignmentCommand: " + e.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkAssignmentCommand.MESSAGE_USAGE));
        }
    }
}
