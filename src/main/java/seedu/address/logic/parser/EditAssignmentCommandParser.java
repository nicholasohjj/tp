package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ASSIGNMENT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAssignmentCommand object
 */
public class EditAssignmentCommandParser implements Parser<EditAssignmentCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditAssignmentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditAssignmentCommand
     * and returns an EditAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAssignmentCommand parse(String args) throws ParseException {
        logger.info("Parsing EditAssignmentCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_NEW_ASSIGNMENT, PREFIX_DATE);

        if (argMultimap.getPreamble().isEmpty()) {
            logger.warning("Empty preamble in EditAssignmentCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.fine("Parsed student index: " + index.getOneBased());

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT, PREFIX_NEW_ASSIGNMENT, PREFIX_DATE);

            String assignmentName = ParserUtil.parseAssignmentName(
                    argMultimap.getValue(PREFIX_ASSIGNMENT).orElseThrow(() -> {
                        logger.warning("Missing required assignment name");
                        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                EditAssignmentCommand.MESSAGE_USAGE));
                    }));
            logger.fine("Parsed original assignment name: " + assignmentName);

            EditAssignmentDescriptor editAssignmentDescriptor = new EditAssignmentDescriptor();
            parseEditableFields(argMultimap, editAssignmentDescriptor);

            if (!editAssignmentDescriptor.isAnyFieldEdited()) {
                logger.warning("No editable fields provided in EditAssignmentCommand");
                throw new ParseException(EditAssignmentCommand.MESSAGE_NOT_EDITED);
            }

            logger.info("Successfully parsed EditAssignmentCommand for student " + index.getOneBased()
                    + ", assignment: " + assignmentName);
            return new EditAssignmentCommand(index, assignmentName, editAssignmentDescriptor);
        } catch (ParseException pe) {
            logger.warning("Parse error in EditAssignmentCommand: " + pe.getMessage());
            throw pe;
        } catch (Exception e) {
            logger.severe("Unexpected error parsing EditAssignmentCommand: " + e.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the editable fields from the argument multimap into the edit descriptor.
     */
    private void parseEditableFields(ArgumentMultimap argMultimap,
                                     EditAssignmentDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_NEW_ASSIGNMENT).isPresent()) {
            String newName = ParserUtil.parseAssignmentName(
                    argMultimap.getValue(PREFIX_NEW_ASSIGNMENT).get());
            descriptor.setNewAssignmentName(newName);
            logger.fine("Parsed new assignment name: " + newName);
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String newDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()).toString();
            descriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            logger.fine("Parsed new due date: " + newDate);
        }
    }
}
