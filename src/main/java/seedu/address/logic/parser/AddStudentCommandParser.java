package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class AddStudentCommandParser implements Parser<AddStudentCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddStudentCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the AddStudentCommand
     * and returns an AddStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments string cannot be null";
        logger.info("Parsing AddStudentCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_SUBJECT);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format for AddStudentCommand: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            Set<Subject> subjectList = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

            assert name != null : "Name cannot be null";
            assert phone != null : "Phone cannot be null";
            assert email != null : "Email cannot be null";
            assert address != null : "Address cannot be null";
            assert subjectList != null : "Subject list cannot be null";

            Student student = new Student(name, phone, email, address,
                    subjectList,
                    new UniqueAssignmentList());
            logger.info("Successfully parsed AddStudentCommand for student: " + name);
            return new AddStudentCommand(student);
        } catch (ParseException pe) {
            logger.warning("Error parsing AddStudentCommand: " + pe.getMessage());
            throw pe;
        } catch (NullPointerException npe) {
            logger.severe("Null value encountered while parsing AddStudentCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }
    }

}
