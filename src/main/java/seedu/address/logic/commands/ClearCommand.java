package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;



/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    /**
     * Creates a ClearCommand.
     *
     */
    public ClearCommand() {
        logger.info("ClearCommand created ");
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing ClearCommand");

        int previousSize = model.getAddressBook().getStudentList().size();
        model.setAddressBook(new AddressBook());
        logger.info("Address book cleared. Previous size: " + previousSize);

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
