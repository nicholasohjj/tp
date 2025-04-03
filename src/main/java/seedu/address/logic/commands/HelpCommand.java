package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String GENERAL_HELP_MESSAGE =
            "Available commands (non-exhaustive):\n"
                    + "1. add_student - Add a new student\n"
                    + "2. list_students - List all students\n"
                    + "3. delete_student - Delete a student\n"
                    + "4. edit_student - Edit a student\n"
                    + "5. add_assignment - Add an assignment\n"
                    + "6. mark_assignment - Mark assignment as complete\n"
                    + "7. clear - Clear all data\n"
                    + "8. exit - Exit the program\n"
                    + "9. help - Show this help\n";
    private static final Logger logger = LogsCenter.getLogger(HelpCommand.class);

    /**
     * Creates a HelpCommand for general help information.
     */
    public HelpCommand() {
        logger.info("HelpCommand created for general help");
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing HelpCommand");

        logger.info("Showing general help");
        return new CommandResult(GENERAL_HELP_MESSAGE, true, false);
    }
}
