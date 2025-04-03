package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.commands.UnmarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        assert userInput != null : "User input cannot be null";
        logger.info("Starting to parse command: " + userInput);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            logger.warning("Failed to parse command - invalid format: " + userInput);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Parsed command word: " + commandWord + " with arguments: " + arguments);

        try {
            Command command = parseCommandByWord(commandWord, arguments);
            logger.info("Successfully parsed command: " + commandWord);
            return command;
        } catch (ParseException pe) {
            logger.warning("Failed to parse command '" + commandWord + "': " + pe.getMessage());
            throw pe;
        } catch (NullPointerException npe) {
            logger.severe("Null pointer encountered while parsing command '" + commandWord + "'");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the command based on the command word.
     */
    private Command parseCommandByWord(String commandWord, String arguments) throws ParseException {
        assert commandWord != null : "Command word cannot be null";
        assert arguments != null : "Arguments cannot be null";

        switch (commandWord) {
        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case AddAssignmentCommand.COMMAND_WORD:
            return new AddAssignmentCommandParser().parse(arguments);

        case EditLessonCommand.COMMAND_WORD:
            return new EditLessonCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case DeleteAssignmentCommand.COMMAND_WORD:
            return new DeleteAssignmentCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);

        case ListStudentsCommand.COMMAND_WORD:
            return new ListStudentsCommand();

        case ListLessonsCommand.COMMAND_WORD:
            return new ListLessonsCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MarkAssignmentCommand.COMMAND_WORD:
            return new MarkAssignmentCommandParser().parse(arguments);

        case UnmarkAssignmentCommand.COMMAND_WORD:
            return new UnmarkAssignmentCommandParser().parse(arguments);

        case EditAssignmentCommand.COMMAND_WORD:
            return new EditAssignmentCommandParser().parse(arguments);

        default:
            logger.warning("Unknown command encountered: " + commandWord);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
