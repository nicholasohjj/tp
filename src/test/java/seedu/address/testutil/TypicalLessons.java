package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson ALICE = new LessonBuilder().withName("Alice Johnson")
            .withDate("14-03-2025").withTime("10:00")
            .withSubject("Calculus").build();
    public static final Lesson BOB = new LessonBuilder().withName("Bob Lee")
            .withDate("20-03-2025").withTime("14:30")
            .withSubject("Quantum Mechanics").build();
    public static final Lesson CAROL = new LessonBuilder().withName("Carol Smith")
            .withDate("25-03-2025").withTime("09:15")
            .withSubject("Organic Chemistry").build();
    public static final Lesson DANIEL = new LessonBuilder().withName("Daniel Kim")
            .withDate("30-03-2025").withTime("13:00")
            .withSubject("Modern History").build();
    public static final Lesson EMILY = new LessonBuilder().withName("Emily Wong")
            .withDate("05-04-2025").withTime("11:45")
            .withSubject("Algorithms").build();
    public static final Lesson FRANK = new LessonBuilder().withName("Frank Garcia")
            .withDate("12-04-2025").withTime("16:20")
            .withSubject("Literature").build();
    public static final Lesson GRACE = new LessonBuilder().withName("Grace Miller")
            .withDate("18-04-2025").withTime("08:30")
            .withSubject("Genetics").build();
    public static final Lesson HARRY = new LessonBuilder().withName("Harry Thompson")
            .withDate("22-04-2025").withTime("12:15")
            .withSubject("Macroeconomics").build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical lessons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Lesson lesson : getTypicalLessons()) {
            ab.addLesson(lesson);
        }
        return ab;
    }

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CAROL, DANIEL, EMILY, FRANK, GRACE, HARRY));
    }
}
