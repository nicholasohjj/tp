package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditLessonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(LESSON_DESC_AMY);
        assertTrue(LESSON_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(LESSON_DESC_AMY.equals(LESSON_DESC_AMY));

        // null -> returns false
        assertFalse(LESSON_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(LESSON_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(LESSON_DESC_AMY.equals(LESSON_DESC_BOB));

        // different name -> returns false
        EditLessonDescriptor editedAmy = new EditLessonDescriptorBuilder(LESSON_DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(LESSON_DESC_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditLessonDescriptorBuilder(LESSON_DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(LESSON_DESC_AMY.equals(editedAmy));

        // different time -> returns false
        editedAmy = new EditLessonDescriptorBuilder(LESSON_DESC_AMY).withTime(VALID_TIME_BOB).build();
        assertFalse(LESSON_DESC_AMY.equals(editedAmy));

        // different subjects -> returns false
        editedAmy = new EditLessonDescriptorBuilder(LESSON_DESC_AMY).withSubject(VALID_SUBJECT_HUSBAND).build();
        assertFalse(LESSON_DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        String expected = EditLessonDescriptor.class.getCanonicalName() + "{subjects="
                + editLessonDescriptor.getSubject().orElse(null) + ", name="
                + editLessonDescriptor.getName().orElse(null) + ", date="
                + editLessonDescriptor.getDate().orElse(null) + ", time="
                + editLessonDescriptor.getTime().orElse(null) + "}";
        assertEquals(expected, editLessonDescriptor.toString());
    }
}
