package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.datetimeutil.Date;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getSubjectSet("Math", "Physics"),
                getAssignmentSet(getAssignment("Assignment 1", "30-05-2025"),
                        getAssignment("Assignment 2", "31-05-2025", true))),

            new Student(new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getSubjectSet("Science", "English"),
                    getAssignmentSet(getAssignment("English Essay", "15-07-2025"))),

            new Student(new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getSubjectSet("Literature"),
                    getAssignmentSet()),

            new Student(new Name("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getSubjectSet("Chemistry"),
                    getAssignmentSet()),

            new Student(new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getSubjectSet("Biology", "Math"),
                    getAssignmentSet()),

            new Student(new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getSubjectSet("Economics"),
                    getAssignmentSet()),

            new Student(new Name("Zoy White"),
                    new Phone("94351253"),
                    new Email("zoyw@gnail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getSubjectSet("History"),
                    getAssignmentSet())
        };
    }


    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

    public static UniqueAssignmentList getAssignmentSet(Assignment... assignments) {
        UniqueAssignmentList assignmentSet = new UniqueAssignmentList();
        for (Assignment assignment : assignments) {
            assignmentSet.add(assignment);
        }
        return assignmentSet;
    }

    public static Assignment getAssignment(String assignment, String dueDate) {
        return new Assignment(assignment, new Date(dueDate));
    }

    public static Assignment getAssignment(String assignment, String dueDate, boolean isDone) {
        return new Assignment(assignment, new Date(dueDate), isDone);
    }

}
