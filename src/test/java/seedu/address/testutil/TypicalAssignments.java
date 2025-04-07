package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT1 = new AssignmentBuilder().withAssignmentName("Assignment 1")
            .withDueDate(new Date("11-08-2025")).build();
    public static final Assignment ASSIGNMENT2 = new AssignmentBuilder().withAssignmentName("Assignment 2")
            .withDueDate(new Date("12-08-2025")).build();
    public static final Assignment ASSIGNMENT3 = new AssignmentBuilder().withAssignmentName("Assignment 3")
            .withDueDate(new Date("13-08-2025")).build();
    public static final Assignment ASSIGNMENT_AMY = new AssignmentBuilder().withAssignmentName("some assignment")
            .withDueDate(new Date("28-02-2026")).build();

    private TypicalAssignments() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical assignments added to the first student.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        Student firstStudent = ab.getStudentList().get(0);
        Student firstStudentCopy = firstStudent.copy();
        for (Assignment assignment : getTypicalAssignments()) {
            firstStudentCopy.addAssignment(assignment);
        }
        ab.setStudent(firstStudent, firstStudentCopy);
        return ab;
    }

    private static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, ASSIGNMENT3, ASSIGNMENT_AMY));
    }
}
