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

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentDescriptor();
        descriptor.setNewAssignmentName(assignment.getAssignmentName()); // Assuming Assignment has a getName() method
        descriptor.setDate(assignment.getDueDate()); // Assuming Assignment has a getDate() method
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
