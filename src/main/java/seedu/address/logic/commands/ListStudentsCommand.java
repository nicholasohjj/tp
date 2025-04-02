package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Lists all students in the address book to the user.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "list_students";

    public static final String MESSAGE_SUCCESS = "Listed all students";
    public static final String MESSAGE_EMPTY_LIST = "No students found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all students in the address book.\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ListStudentsCommand.class);
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing ListStudentsCommand");

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        int studentCount = model.getFilteredStudentList().size();

        if (studentCount == 0) {
            logger.info("Address book is empty - no students found");
            return new CommandResult(MESSAGE_EMPTY_LIST, true);
        }

        logger.info("Listed " + studentCount + " students");
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, studentCount),
                true);
    }
}
