package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.UniqueAssignmentList;

/**
 * Jackson-friendly version of {@link seedu.address.model.assignment.UniqueAssignmentList}.
 */
public class JsonAdaptedUniqueAssignmentList {

    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignment list contains duplicate assignment(s).";

    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedUniqueAssignmentList} with the given {@code assignments}.
     */
    @JsonCreator
    public JsonAdaptedUniqueAssignmentList(@JsonProperty List<JsonAdaptedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code UniqueAssignmentList} into this class for Jackson use.
     */
    public JsonAdaptedUniqueAssignmentList(UniqueAssignmentList source) {
        assignments.addAll(source.asUnmodifiableObservableList().stream()
                .map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted assignment list object into the model's
     * {@code UniqueAssignmentList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment list.
     */
    public UniqueAssignmentList toModelType() throws IllegalValueException {
        UniqueAssignmentList assignmentList = new UniqueAssignmentList();
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            assignmentList.add(jsonAdaptedAssignment.toModelType());
            if (assignmentList.contains(jsonAdaptedAssignment.toModelType())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
        }
        return assignmentList;
    }
}
