package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.datetimeutil.Date;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";

    private final String assignmentName;
    private final String dueDate;
    private final int isCompleted;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given {@code assignmentName}.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignment name") String assignmentName,
                                 @JsonProperty("due date") String dueDate,
                                 @JsonProperty("is done") boolean isDone) {
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.isCompleted = isDone ? 1 : 0;
    }

    /**
     * Converts a given {@code assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.assignmentName;
        dueDate = source.dueDate.date;
        isCompleted = source.isDone ? 1 : 0;
    }

    /**
     * Converts this Json-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Assignment.class.getSimpleName()));
        }
        if (!Assignment.isValidAssignmentValue(assignmentName)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Assignment.isValidAssignmentDate(dueDate)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(dueDate);

        return new Assignment(assignmentName, modelDate);
    }

}
