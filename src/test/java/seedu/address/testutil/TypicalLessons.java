package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson ALICE = new LessonBuilder().withName("Alice Pauline")
            .withDate("14-03-2028").withTime("10:00")
            .withSubjects("CS2109S").build();
    public static final Lesson BENNY = new LessonBuilder().withName("Benson Meier")
            .withDate("20-03-2027").withTime("14:30")
            .withSubjects("MA1522").build();
    public static final Lesson CAROL = new LessonBuilder().withName("Carl Kurz")
            .withDate("25-03-2026").withTime("09:15")
            .withSubjects("CS").build();
    public static final Lesson DANIEL = new LessonBuilder().withName("Daniel Meier")
            .withDate("30-03-2026").withTime("13:00")
            .withSubjects("CS").build();
    public static final Lesson EMILY = new LessonBuilder().withName("Elle Meyer")
            .withDate("05-04-2027").withTime("11:45")
            .withSubjects("BZA").build();
    public static final Lesson FRANK = new LessonBuilder().withName("Fiona Kunz")
            .withDate("12-04-2026").withTime("16:20")
            .withSubjects("CS2100").build();
    public static final Lesson GRACE = new LessonBuilder().withName("George Best")
            .withDate("18-10-2025").withTime("08:30")
            .withSubjects("CS2105").build();
    public static final Lesson HARRY = new LessonBuilder().withName("Harry Potter")
            .withDate("22-9-2025").withTime("12:15")
            .withSubjects("CS2103T").build();

    //manually added - details are in {@code CommandTestUtil}
    public static final Lesson BOB = new LessonBuilder().withSubjects(VALID_SUBJECT_BOB).withName(VALID_NAME_BOB)
            .withDate(VALID_DATE_BOB).withTime(VALID_TIME_BOB).build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical lessons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Lesson lesson : getTypicalLessons()) {
            ab.addLesson(lesson);
        }
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENNY, CAROL, DANIEL, EMILY, FRANK, GRACE, HARRY));
    }
}
