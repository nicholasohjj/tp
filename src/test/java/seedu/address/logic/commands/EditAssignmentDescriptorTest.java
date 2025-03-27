package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withNewAssignmentName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).build();
        EditAssignmentDescriptor descriptorWithSameValues = new EditAssignmentDescriptor(descriptor);
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        EditAssignmentDescriptor differentDescriptor = new EditAssignmentDescriptorBuilder()
                .withNewAssignmentName("DifferentName")
                .withDate(VALID_DATE_BOB).build();
        assertFalse(descriptor.equals(differentDescriptor));

        // different new assignment name -> returns false
        EditAssignmentDescriptor editedDescriptor = new EditAssignmentDescriptorBuilder(descriptor)
                .withNewAssignmentName("NewName").build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different date -> returns false
        editedDescriptor = new EditAssignmentDescriptorBuilder(descriptor).withDate("09-09-2025").build();
        assertFalse(descriptor.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditAssignmentDescriptor editAssignmentDescriptor = new EditAssignmentDescriptor();
        String expected = EditAssignmentDescriptor.class.getCanonicalName() + "{assignmentName="
                + editAssignmentDescriptor.getNewAssignmentName().orElse(null) + ", date="
                + editAssignmentDescriptor.getDate().orElse(null) + "}";
        assertEquals(expected, editAssignmentDescriptor.toString());
    }
}
