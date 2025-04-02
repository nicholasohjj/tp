package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits the application.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    private static final Logger logger = LogsCenter.getLogger(ExitCommand.class);

    /**
     * Creates an ExitCommand.
     */
    public ExitCommand() {
        logger.info("ExitCommand created");
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing ExitCommand");

        logger.info("Application exit confirmed");
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
