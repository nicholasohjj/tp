package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditStudentCommandParser implements Parser<EditStudentCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditStudentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStudentCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing EditStudentCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_SUBJECT);

        if (argMultimap.getPreamble().isEmpty()) {
            logger.warning("Empty preamble in EditStudentCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.fine("Parsed student index: " + index.getOneBased());

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_SUBJECT);

            EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
            parseFields(argMultimap, editStudentDescriptor);

            if (!editStudentDescriptor.isAnyFieldEdited()) {
                logger.warning("No fields edited in EditStudentCommand");
                throw new ParseException(EditStudentCommand.MESSAGE_NOT_EDITED);
            }

            logger.info("Successfully parsed EditStudentCommand for student index: " + index.getOneBased());
            return new EditStudentCommand(index, editStudentDescriptor);
        } catch (ParseException pe) {
            logger.warning("Error parsing EditStudentCommand: " + pe.getMessage());
            throw pe;
        } catch (Exception e) {
            logger.severe("Unexpected error parsing EditStudentCommand: " + e.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses individual fields from the argument multimap into the edit descriptor.
     */
    private void parseFields(ArgumentMultimap argMultimap, EditStudentDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            logger.fine("Parsed name field");
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            logger.fine("Parsed phone field");
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            logger.fine("Parsed email field");
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
            logger.fine("Parsed address field");
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            logger.warning("Attempt to edit subject field detected");
            throw new ParseException(EditStudentCommand.MESSAGE_EDIT_SUBJECT_DISALLOWED);
        }
    }
}
