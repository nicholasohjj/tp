package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser implements Parser<DeleteStudentCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteStudentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * and returns a DeleteStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        logger.info("Attempting to parse DeleteStudentCommand with arguments: " + args);

        if (args == null || args.trim().isEmpty()) {
            logger.warning("Empty input received for DeleteStudentCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(args.trim());
            logger.info("Successfully parsed DeleteStudentCommand for student index: " + index.getOneBased());
            return new DeleteStudentCommand(index);
        } catch (ParseException pe) {
            logger.warning("Failed to parse index in DeleteStudentCommand: " + pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE), pe);
        } catch (Exception e) {
            logger.severe("Unexpected error parsing DeleteStudentCommand: " + e.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
        }
    }
}
