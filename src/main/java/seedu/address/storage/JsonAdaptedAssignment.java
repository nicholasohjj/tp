package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    private final String assignmentName;
    private final String dueDate;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(String assignmentName, String dueDate) {
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }

    /**
     * Converts a given {@code assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.value;
        dueDate = source.dueDate.date;
    }

    @JsonValue
    public String getAssignmentName() {
        return assignmentName;
    }

    @JsonValue
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Converts this Json-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (!Assignment.isValidAssignmentValue(assignmentName)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        if (!Assignment.isValidAssignmentDate(dueDate)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        return new Assignment(assignmentName, new Date(dueDate));
    }

}
