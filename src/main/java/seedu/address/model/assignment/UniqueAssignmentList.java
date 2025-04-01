package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.expections.AssignmentNotFoundException;
import seedu.address.model.assignment.expections.DuplicateAssignmentException;

/**
 * A list of assignments that enforces uniqueness between its elements and does not allow nulls.
 * An assignment is considered unique by comparing using {@code Assignment#isSameAssignment(Assignment)}.
 * As such, adding and updating of assignments uses Assignment
 * #isSameAssignment(Assignment) for equality to ensure that the assignment being added or updated is
 * unique in terms of identity in the UniqueAssignmentList.
 *
 **/
public class UniqueAssignmentList implements Iterable<Assignment>, Comparator<Assignment> {

    public static final String MESSAGE_ALREADY_MARKED = "Error: Assignment is already marked";
    public static final String MESSAGE_ALREADY_UNMARKED = "Error: Assignment is already unmarked";


    private final ObservableList<Assignment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Assignment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    /**
     * Returns true if the list contains an equivalent assignment as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Adds an assignment to the list.
     * The assignment must not already exist in the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAssignmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the assignment {@code target} in the list with {@code editedAssignment}.
     * {@code target} must exist in the list.
     * The assignment identity of {@code editedAssignment} must not be the same as another
     * existing assignment in the list.
     */
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireNonNull(editedAssignment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }

        if (!target.isSameAssignment(editedAssignment) && contains(editedAssignment)) {
            throw new DuplicateAssignmentException();
        }

        internalList.set(index, editedAssignment);
    }

    public ObservableList<Assignment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Assignment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueAssignmentList)) {
            return false;
        }

        UniqueAssignmentList otherUniqueAssignmentList = (UniqueAssignmentList) other;
        return internalList.equals(otherUniqueAssignmentList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean studentAreUnique(List<Assignment> assignments) {
        for (int i = 0; i < assignments.size(); i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (assignments.get(i).isSameAssignment(assignments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int compare(Assignment o1, Assignment o2) {
        return o1.compareTo(o2);
    }

    /**
     * Marks an assignment as done.
     */
    public void markAssignment(String assignmentName) throws CommandException {
        for (Assignment assignment : internalList) {
            if (assignment.getAssignmentName().equals(assignmentName)) {
                if (assignment.isDone()) {
                    throw new CommandException(MESSAGE_ALREADY_MARKED);
                }
                assignment.setDone();
            }
        }
    }

    /**
     * Marks an assignment as undone.
     */
    public void unmarkAssignment(String assignmentName) throws CommandException {
        for (Assignment assignment : internalList) {
            if (assignment.getAssignmentName().equals(assignmentName)) {
                if (!assignment.isDone()) {
                    throw new CommandException(MESSAGE_ALREADY_UNMARKED);
                }
                assignment.setUndone();
            }
        }
    }

    /**
     * Deletes an assignment from the list.
     */
    public void deleteAssignment(String assignmentName) {
        Iterator<Assignment> iterator = internalList.iterator();
        while (iterator.hasNext()) {
            Assignment assignment = iterator.next();
            if (assignment.getAssignmentName().equals(assignmentName)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Returns a specified assignment from the list.
     * @param assignmentName
     */
    public Assignment getAssignment(String assignmentName) {
        for (Assignment assignment : internalList) {
            if (assignment.getAssignmentName().equals(assignmentName)) {
                return assignment;
            }
        }
        return null;
    }
}
