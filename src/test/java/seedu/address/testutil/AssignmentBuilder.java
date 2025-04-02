package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {

    public static final String DEFAULT_ASSIGNMENT_NAME = "Homework 1";
    public static final String DEFAULT_DUE_DATE = "10-04-2025";

    private String assignmentName;
    private Date dueDate;

    /**
     * Creates a {@code AssignmentBuilder} with the default details.
     */
    public AssignmentBuilder() {
        assignmentName = DEFAULT_ASSIGNMENT_NAME;
        dueDate = new Date(DEFAULT_DUE_DATE);
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentName = assignmentToCopy.getAssignmentName();
        dueDate = assignmentToCopy.getDueDate();
    }

    /**
     * Sets the {@code assignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
        return this;
    }

    /**
     * Sets the {@code dueDate} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Assignment build() {
        return new Assignment(assignmentName, dueDate);
    }
}
