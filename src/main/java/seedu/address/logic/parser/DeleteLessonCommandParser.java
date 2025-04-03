package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteLessonCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        logger.info("Attempting to parse DeleteLessonCommand with arguments: " + args);

        if (args == null || args.trim().isEmpty()) {
            logger.warning("Empty input received for DeleteLessonCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
        }

        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

            if (argMultimap.getPreamble().isEmpty()) {
                logger.warning("Missing lesson index in DeleteLessonCommand");
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.info("Successfully parsed DeleteLessonCommand for lesson index: " + index.getOneBased());

            return new DeleteLessonCommand(index);
        } catch (ParseException pe) {
            logger.warning("Failed to parse index in DeleteLessonCommand: " + pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE), pe);
        } catch (Exception e) {
            logger.severe("Unexpected error parsing DeleteLessonCommand: " + e.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
        }
    }
}
