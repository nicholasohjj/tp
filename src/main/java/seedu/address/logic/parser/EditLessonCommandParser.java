package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser<EditLessonCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditLessonCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing EditLessonCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME);

        if (argMultimap.getPreamble().isEmpty()) {
            logger.warning("Empty preamble in EditLessonCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLessonCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.fine("Parsed lesson index: " + index.getOneBased());

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_SUBJECT);

            EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
            parseFields(argMultimap, editLessonDescriptor);

            if (!editLessonDescriptor.isAnyFieldEdited()) {
                logger.warning("No fields edited in EditLessonCommand");
                throw new ParseException(EditLessonCommand.MESSAGE_NOT_EDITED);
            }

            logger.info("Successfully parsed EditLessonCommand for lesson index: " + index.getOneBased());
            return new EditLessonCommand(index, editLessonDescriptor);
        } catch (ParseException pe) {
            logger.warning("Error parsing EditLessonCommand: " + pe.getMessage());
            throw pe;
        } catch (Exception e) {
            logger.severe("Unexpected error parsing EditLessonCommand: " + e.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLessonCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses individual fields from the argument multimap into the edit descriptor.
     */
    private void parseFields(ArgumentMultimap argMultimap, EditLessonDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            logger.fine("Parsed name field");
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            descriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            logger.fine("Parsed date field");
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            descriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
            logger.fine("Parsed time field");
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            descriptor.setSubject(ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
            logger.fine("Parsed subject field");
        }
    }
}
