package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.expections.AssignmentNotFoundException;
import seedu.address.model.assignment.expections.DuplicateAssignmentException;
import seedu.address.model.datetimeutil.Date;
import seedu.address.testutil.AssignmentBuilder;

public class UniqueAssignmentListTest {

    private final UniqueAssignmentList uniqueAssignmentList = new UniqueAssignmentList();

    @Test
    public void contains_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.contains(null));
    }

    @Test
    public void contains_assignmentNotInList_returnsFalse() {
        Assignment assignment = new AssignmentBuilder().build();
        assertFalse(uniqueAssignmentList.contains(assignment));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        assertTrue(uniqueAssignmentList.contains(assignment));
    }

    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.add(null));
    }

    @Test
    public void add_duplicateAssignment_throwsDuplicateAssignmentException() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        assertThrows(DuplicateAssignmentException.class, () -> uniqueAssignmentList.add(assignment));
    }

    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.setAssignment(
                new AssignmentBuilder().build(), null));
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.setAssignment(
                new AssignmentBuilder().build(), null));
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> uniqueAssignmentList.setAssignment(
                new AssignmentBuilder().build(), new AssignmentBuilder().build()));
    }

    @Test
    public void setAssignment_editedAssignmentIsSameAssignment_success() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        uniqueAssignmentList.setAssignment(assignment, assignment);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(assignment);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasSameIdentity_success() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        Assignment editedAssignment = new AssignmentBuilder(assignment).withDueDate(new Date("11-04-2025")).build();
        uniqueAssignmentList.setAssignment(assignment, editedAssignment);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(editedAssignment);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasDifferentIdentity_success() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        Assignment differentAssignment = new AssignmentBuilder().withAssignmentName("Different Assignment").build();
        uniqueAssignmentList.setAssignment(assignment, differentAssignment);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(differentAssignment);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasNonUniqueIdentity_throwsDuplicateAssignmentException() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        Assignment duplicateAssignment = new AssignmentBuilder().withAssignmentName("Duplicate Assignment").build();
        uniqueAssignmentList.add(duplicateAssignment);
        assertThrows(DuplicateAssignmentException.class, () ->
                uniqueAssignmentList.setAssignment(assignment, duplicateAssignment));
    }

    @Test
    public void remove_existingAssignment_removesAssignment() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        uniqueAssignmentList.deleteAssignment(assignment.getAssignmentName());
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueAssignmentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void markAssignment_alreadyMarked_throwsCommandException() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        assertDoesNotThrow(() -> uniqueAssignmentList.markAssignment(assignment.getAssignmentName()));
        assertThrows(CommandException.class, () -> uniqueAssignmentList.markAssignment(assignment.getAssignmentName()));
    }

    @Test
    public void unmarkAssignment_alreadyUnmarked_throwsCommandException() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        assertThrows(CommandException.class, () ->
                uniqueAssignmentList.unmarkAssignment(assignment.getAssignmentName()));
    }

    @Test
    public void deleteAssignment_existingAssignment_deletesAssignment() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        uniqueAssignmentList.deleteAssignment(assignment.getAssignmentName());
        assertFalse(uniqueAssignmentList.contains(assignment));
    }

    @Test
    public void getAssignment_existingAssignment_returnsAssignment() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        assertEquals(assignment, uniqueAssignmentList.getAssignment(assignment.getAssignmentName()));
    }

    @Test
    public void getAssignment_nonExistingAssignment_returnsNull() {
        assertEquals(null, uniqueAssignmentList.getAssignment("Non-existing Assignment"));
    }

    @Test
    public void compare_assignmentsByDueDate_worksCorrectly() {
        Assignment a1 = new AssignmentBuilder().withDueDate(new Date("01-01-2030")).build();
        Assignment a2 = new AssignmentBuilder().withDueDate(new Date("01-02-2030")).build();

        assertTrue(uniqueAssignmentList.compare(a1, a2) < 0);
        assertTrue(uniqueAssignmentList.compare(a2, a1) > 0);
        assertEquals(0, uniqueAssignmentList.compare(a1, a1));
    }

    @Test
    public void clear_assignmentListCleared_successfully() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        uniqueAssignmentList.clear();
        assertEquals(0, uniqueAssignmentList.asUnmodifiableObservableList().size());
    }

    @Test
    public void iterator_iteratesAssignmentsCorrectly() {
        Assignment assignment = new AssignmentBuilder().build();
        uniqueAssignmentList.add(assignment);
        Iterator<Assignment> iterator = uniqueAssignmentList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(assignment, iterator.next());
    }


}
