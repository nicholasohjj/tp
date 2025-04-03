package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.datetimeutil.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddLessonCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing AddLessonCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_SUBJECT);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_SUBJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for AddLessonCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_SUBJECT);
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());

            assert name != null : "Name cannot be null";
            assert date != null : "Date cannot be null";
            assert time != null : "Time cannot be null";
            assert subject != null : "Subject cannot be null";

            Lesson lesson = new Lesson(subject, name, date, time);
            logger.info("Successfully parsed AddLessonCommand: " + lesson);
            return new AddLessonCommand(lesson);
        } catch (ParseException pe) {
            logger.warning("Error parsing AddLessonCommand: " + pe.getMessage());
            throw pe;
        }
    }

}
