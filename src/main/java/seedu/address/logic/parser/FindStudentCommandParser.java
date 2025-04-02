package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindStudentCommand object
 */
public class FindStudentCommandParser implements Parser<FindStudentCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindStudentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindStudentCommand
     * and returns a FindStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStudentCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing FindStudentCommand with arguments: " + args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Empty arguments provided to FindStudentCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStudentCommand.MESSAGE_USAGE));
        }

        try {
            String[] nameKeywords = trimmedArgs.split("\\s+");
            assert nameKeywords != null && nameKeywords.length > 0 : "Keywords array cannot be null or empty";

            logger.info("Successfully parsed FindStudentCommand with keywords: "
                    + Arrays.toString(nameKeywords));
            return new FindStudentCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } catch (NullPointerException npe) {
            logger.severe("Null value encountered while parsing FindStudentCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindStudentCommand.MESSAGE_USAGE));
        } catch (Exception e) {
            logger.warning("Unexpected error parsing FindStudentCommand: " + e.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindStudentCommand.MESSAGE_USAGE));
        }
    }

}
