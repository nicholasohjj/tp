package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.model.Model;

/**
 * Lists all students in the address book to the user.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "list_students";

    public static final String MESSAGE_SUCCESS = "Listed all students";
    public static final String MESSAGE_EMPTY_LIST = "No students found";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(
                model.getFilteredStudentList().isEmpty()
                ? MESSAGE_EMPTY_LIST
                : MESSAGE_SUCCESS, true);
    }
}
