package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.subject.Subject;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    // Data fields
    private final Set<Subject> subjects = new HashSet<>();
    private final UniqueAssignmentList assignments;


    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.assignments = new UniqueAssignmentList();
    }

    /**
     * Every field must be present and not null.
     * Additional constructor for student to include subject, tags and assignments.
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   Set<Subject> subjects) {
        requireAllNonNull(name, phone, email, address, subjects);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.subjects.addAll(subjects);
        this.assignments = new UniqueAssignmentList();
    }

    /**
     * Every field must be present and not null.
     * Additional constructor for student to include assignments.
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   Set<Subject> subjects,
                   UniqueAssignmentList assignments) {
        requireAllNonNull(name, phone, email, address, subjects, assignments);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.subjects.addAll(subjects);
        this.assignments = assignments;
    }



    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    /**
     * Returns true if the student has the subject.
     */
    public boolean hasSubject(Subject targetSubject) {
        return subjects.contains(targetSubject);
    }

    public UniqueAssignmentList getAssignments() {
        return assignments;
    }

    /**
     * Returns a new student with the assignment added.
     */
    public Student addAssignment(Assignment assignment) {
        requireNonNull(assignment);
        UniqueAssignmentList newAssignments = this.assignments;
        newAssignments.add(assignment);
        return new Student(name, phone, email, address, subjects, newAssignments);
    }

    /**
     * Returns boolean on whether assignment already exists in student.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return assignments.contains(assignment);
    }

    /**
     * Returns true if both students have the same name or phone or email.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null && (otherStudent.getName().equals(this.getName())
                || otherStudent.getPhone().equals(this.getPhone()) || otherStudent.getEmail().equals(this.getEmail()));
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name)
                && phone.equals(otherStudent.phone)
                && email.equals(otherStudent.email)
                && address.equals(otherStudent.address)
                && subjects.equals(otherStudent.subjects);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, subjects, assignments);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("subjects", subjects)
                .add("assignments", assignments)
                .toString();
    }

    /**
     * Marks an assignment identified by its name as completed for the student.
     *
     * @param assignmentName The name of the assignment to mark as completed.
     * @return A new Student instance with the updated assignment status.
     * @throws CommandException if no assignment with the specified name exists for this student.
     */
    public Student markAssignment(String assignmentName) throws CommandException {
        assignments.markAssignment(assignmentName);
        return new Student(name, phone, email, address, subjects, assignments);
    }

    /**
     * Unmarks an assignment identified by its name as uncompleted for the student.
     *
     * @param assignmentName The name of the assignment to mark as uncompleted.
     * @return A new Student instance with the updated assignment status.
     * @throws CommandException if no assignment with the specified name exists for this student.
     */
    public Student unmarkAssignment(String assignmentName) throws CommandException {
        assignments.unmarkAssignment(assignmentName);
        return new Student(name, phone, email, address, subjects, assignments);
    }

    public void deleteAssignment(String assignmentName) {
        assignments.deleteAssignment(assignmentName);
    }

    public Student copy() {
        return new Student(name, phone, email, address, subjects, assignments);
    }

    public void clearAssignments() {
        assignments.clear();
    }
}
