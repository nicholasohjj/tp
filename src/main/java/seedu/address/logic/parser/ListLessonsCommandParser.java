package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.StudentNameLessonPredicate;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new ListLessonsCommand object
 */
public class ListLessonsCommandParser implements Parser<ListLessonsCommand> {
    private static final Logger logger = LogsCenter.getLogger(ListLessonsCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the ListLessonsCommand
     * and returns a ListLessonsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListLessonsCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing ListLessonsCommand with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for ListLessonsCommand - preamble not empty: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListLessonsCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME)) {
            logger.info("No name prefix found - returning all lessons");
            return new ListLessonsCommand(PREDICATE_SHOW_ALL_LESSONS);
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            assert name != null : "Parsed name cannot be null";

            logger.info("Successfully parsed ListLessonsCommand for student: " + name);
            return new ListLessonsCommand(new StudentNameLessonPredicate(name), name);
        } catch (ParseException pe) {
            logger.warning("Error parsing name in ListLessonsCommand: " + pe.getMessage());
            throw pe;
        } catch (NullPointerException npe) {
            logger.severe("Null value encountered while parsing ListLessonsCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListLessonsCommand.MESSAGE_USAGE));
        }
    }
}
