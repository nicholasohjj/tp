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
    private boolean isDone;

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder() {
        assignmentName = DEFAULT_ASSIGNMENT_NAME;
        dueDate = new Date(DEFAULT_DUE_DATE);
        isDone = false;
    }

    /**
     * Initializes the AssignmentBuilder with data from {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentName = assignmentToCopy.getAssignmentName();
        dueDate = assignmentToCopy.getDueDate();

        isDone = assignmentToCopy.isDone();
    }

    /**
     * Sets the {@code assignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentName(String name) {
        this.assignmentName = name;
        return this;
    }

    /**
     * Sets the {@code dueDate} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Sets the {@code isDone} status of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDoneStatus(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    /**
     * Builds and returns the {@code Assignment}.
     */
    public Assignment build() {
        return new Assignment(assignmentName, dueDate, isDone);
    }
}
