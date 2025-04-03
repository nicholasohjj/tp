package seedu.address.testutil;

import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * A utility class to help with building EditAssignmentDescriptor objects.
 */
public class EditAssignmentDescriptorBuilder {

    private EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentDescriptor(descriptor);
    }

    /** Constructor that takes an Assignment and initializes the descriptor */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentDescriptor();
        descriptor.setNewAssignmentName(assignment.getAssignmentName());
        descriptor.setDate(assignment.getDueDate());
    }

    /**
     * Sets the {@code newAssignmentName} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withNewAssignmentName(String newAssignmentName) {
        descriptor.setNewAssignmentName(newAssignmentName);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    public EditAssignmentDescriptor build() {
        return descriptor;
    }
}
