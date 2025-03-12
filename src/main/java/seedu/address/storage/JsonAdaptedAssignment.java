package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Assignment;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    private final String assignmentName;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    /**
     * Converts a given {@code assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.value;
    }

    @JsonValue
    public String getAssignmentName() {
        return assignmentName;
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
        return new Assignment(assignmentName);
    }

}
