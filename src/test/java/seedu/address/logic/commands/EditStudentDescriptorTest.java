package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.address.testutil.EditStudentDescriptorBuilder;

public class EditStudentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStudentDescriptor descriptorWithSameValues = new EditStudentDescriptor(STUDENT_DESC_AMY);
        assertTrue(STUDENT_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(STUDENT_DESC_AMY.equals(STUDENT_DESC_AMY));

        // null -> returns false
        assertFalse(STUDENT_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(STUDENT_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(STUDENT_DESC_AMY.equals(STUDENT_DESC_BOB));

        // different name -> returns false
        EditStudentDescriptor editedAmy = new EditStudentDescriptorBuilder(STUDENT_DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(STUDENT_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditStudentDescriptorBuilder(STUDENT_DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(STUDENT_DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditStudentDescriptorBuilder(STUDENT_DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(STUDENT_DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditStudentDescriptorBuilder(STUDENT_DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(STUDENT_DESC_AMY.equals(editedAmy));

        // different subjects -> returns false
        editedAmy = new EditStudentDescriptorBuilder(STUDENT_DESC_AMY).withSubjects(VALID_SUBJECT_HUSBAND).build();
        assertFalse(STUDENT_DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        String expected = EditStudentDescriptor.class.getCanonicalName() + "{name="
                + editStudentDescriptor.getName().orElse(null) + ", phone="
                + editStudentDescriptor.getPhone().orElse(null) + ", email="
                + editStudentDescriptor.getEmail().orElse(null) + ", address="
                + editStudentDescriptor.getAddress().orElse(null) + ", subjects="
                + editStudentDescriptor.getSubjects().orElse(null) + ", assignments="
                + editStudentDescriptor.getAssignments().orElse(null) + "}";
        assertEquals(expected, editStudentDescriptor.toString());
    }
}
