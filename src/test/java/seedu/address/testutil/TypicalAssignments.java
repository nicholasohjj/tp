package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT1 = new Assignment("Assignment 1", new Date("10-04-2025"));
    public static final Assignment ASSIGNMENT2 = new Assignment("Assignment 2", new Date("12-04-2025"));

    private TypicalAssignments() {} // prevents instantiation
}
