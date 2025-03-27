package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.StudentNameLessonPredicate;

/**
 * Parses input arguments and creates a new ListLessonsCommand object
 */
public class ListLessonsCommandParser implements Parser<ListLessonsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListLessonsCommand
     * and returns a ListLessonsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListLessonsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListLessonsCommand.MESSAGE_USAGE));
            }
            return new ListLessonsCommand(PREDICATE_SHOW_ALL_LESSONS);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
        String keyword = argMultimap.getValue(PREFIX_NAME).get();
        if (keyword.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListLessonsCommand.MESSAGE_USAGE));
        }

        return new ListLessonsCommand(new StudentNameLessonPredicate(keyword));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
