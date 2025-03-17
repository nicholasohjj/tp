package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.NameContainsKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLessonsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ListLessonsCommand(new NameContainsKeywordsPredicate<Lesson>(Arrays.asList(nameKeywords)));
    }
}
