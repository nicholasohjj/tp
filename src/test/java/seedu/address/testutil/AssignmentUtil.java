package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ASSIGNMENT;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.assignment.Assignment;

/**
 * A utility class for Assignment.
 */
public class AssignmentUtil {

    /**
     * Returns an add command string for adding the {@code assignment}.
     */
    public static String getAddAssignmentCommand(Assignment assignment) {
        return AddAssignmentCommand.COMMAND_WORD + " 1 " + getAssignmentDetails(assignment);
    }

    /**
     * Returns the part of command string for the given {@code assignment}'s details.
     */
    private static String getAssignmentDetails(Assignment assignment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ASSIGNMENT + assignment.getAssignmentName() + " ");
        sb.append(PREFIX_DATE + assignment.getDueDate().date + " ");
        return sb.toString();
    }

    public static String getEditAssignmentDescriptorDetails(EditAssignmentCommand.EditAssignmentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getNewAssignmentName().ifPresent(name -> sb.append(PREFIX_NEW_ASSIGNMENT).append(name).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.date).append(" "));
        return sb.toString();
    }
}
