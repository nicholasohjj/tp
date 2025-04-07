package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getSubjects().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns true
        Student editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_HUSBAND).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Student editedBob = new StudentBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameStudent(editedBob));

        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new StudentBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameStudent(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        //        // different address -> returns false
        //        editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        //        assertFalse(ALICE.equals(editedAlice));
        //
        //        // different subjects -> returns false
        //        editedAlice = new StudentBuilder(ALICE).withSubjects(VALID_SUBJECT_HUSBAND).build();
        //        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", subjects=" + ALICE.getSubjects()
                + ", assignments=" + ALICE.getAssignments() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hasAssignment_assignmentInList_returnsTrue() {
        Assignment assignment = new AssignmentBuilder().build();
        Student student = new StudentBuilder().withAssignment(assignment).build();
        assertTrue(student.hasAssignment(assignment));
    }

    @Test
    public void hasAssignment_assignmentNotInList_returnsFalse() {
        Assignment assignment = new AssignmentBuilder().build();
        Student student = new StudentBuilder().build();
        assertFalse(student.hasAssignment(assignment));
    }

    @Test
    public void markAssignment_validAssignment_marksSuccessfully() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        Student student = new StudentBuilder().withAssignment(assignment).build();
        Student updatedStudent = student.markAssignment(assignment.getAssignmentName());
        assertTrue(updatedStudent.getAssignments().getAssignment(assignment.getAssignmentName()).isDone());
    }

    @Test
    public void unmarkAssignment_validAssignment_unmarksSuccessfully() throws Exception {
        Assignment assignment = new AssignmentBuilder().build();
        assignment.setDone();
        Student student = new StudentBuilder().withAssignment(assignment).build();
        Student updatedStudent = student.unmarkAssignment(assignment.getAssignmentName());
        assertFalse(updatedStudent.getAssignments().getAssignment(assignment.getAssignmentName()).isDone());
    }

    @Test
    public void deleteAssignment_assignmentInList_removesSuccessfully() {
        Assignment assignment = new AssignmentBuilder().build();
        Student student = new StudentBuilder().withAssignment(assignment).build();
        student.deleteAssignment(assignment.getAssignmentName());
        assertFalse(student.getAssignments().contains(assignment));
    }

    @Test
    public void clearAssignments_allAssignmentsRemoved() {
        Assignment assignment = new AssignmentBuilder().build();
        Student student = new StudentBuilder().withAssignment(assignment).build();
        student.clearAssignments();
        assertTrue(student.getAssignments().asUnmodifiableObservableList().isEmpty());
    }

}
