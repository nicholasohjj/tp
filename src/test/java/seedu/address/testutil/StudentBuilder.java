package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SUBJECT = "CS2103T";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Subject> subjects;
    private UniqueAssignmentList assignments;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        subjects = new HashSet<>();
        assignments = new UniqueAssignmentList();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        subjects = new HashSet<>(studentToCopy.getSubjects());
        assignments = studentToCopy.getAssignments();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withSubjects(String ... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code assignments} into a {@code Set<Assignment>} and
     * set it to the {@code Student} that we are building.
     */
    public StudentBuilder withAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        return this;
    }



    /**
     * Sets the {@code UniqueAssignmentList} of the {@code Student} that we are building.
     */
    public StudentBuilder setAssignment(Student student, String assignment, String dueDate) {
        return new StudentBuilder(student.addAssignment(new Assignment(assignment, new Date(dueDate))));
    }

    /**
     * Builds the {@code Student} object with the provided details.
     */
    public Student build() {
        return new Student(name, phone, email, address, subjects, assignments);
    }
}
