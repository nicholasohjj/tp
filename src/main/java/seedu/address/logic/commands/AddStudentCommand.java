package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Adds a student to the address book.
 */
public class AddStudentCommand extends Command {
    public static final String COMMAND_WORD = "add_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + "n/ John Doe "
            + "p/ 98765432 "
            + "e/ johndoe@gmail.com "
            + "a/ 311, Clementi Ave 2, #02-25 "
            + "s/ Math";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";

    private static final Logger logger = LogsCenter.getLogger(AddStudentCommand.class);

    private final Student toAdd;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        this.toAdd = student;
        logger.info("AddStudentCommand created for student: " + student.getName());

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AddStudentCommand for student: " + toAdd.getName());


        if (model.hasStudent(toAdd)) {
            logger.warning("Duplicate student detected: " + toAdd.getName());
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addStudent(toAdd);
        logger.info("Student successfully added: " + toAdd.getName());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand otherAddStudentCommand = (AddStudentCommand) other;
        return toAdd.equals(otherAddStudentCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
