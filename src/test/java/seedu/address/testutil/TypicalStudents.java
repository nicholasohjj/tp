package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withPhone("94351253")
            .withAddress("123, Jurong West Ave 6, #08-111").withSubjects("CS2109S").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withAddress("456, Orchard Road, #10-20").withSubjects("MA1522").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("789, Bukit Timah Road, #05-10")
            .withSubjects("CS").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("321, Clementi Ave 3, #02-15")
            .withSubjects("CS").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("94823224")
            .withEmail("werner@example.com").withAddress("654, Tampines Street 62, #07-12")
            .withSubjects("BZA").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("94832427")
            .withEmail("lydia@example.com").withAddress("987, Pasir Ris Drive, #03-21")
            .withSubjects("CS2100").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("94820442")
            .withEmail("anna@example.com").withAddress("555, Bedok North Ave 1, #11-22")
            .withSubjects("CS2105").build();
    public static final Student HARRY = new StudentBuilder().withName("Harry Potter").withPhone("94824420")
            .withEmail("harry@gmail.com").withAddress("777, Changi Road, #09-33")
            .withSubjects("CS2103T").build();
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("84823424")
            .withEmail("stefan@example.com").withAddress("888, Serangoon Ave 2, #04-44")
            .withSubjects("MA2104").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("84821310")
            .withEmail("hans@example.com").withAddress("999, Yishun Ring Road, #05-55")
            .withSubjects("CS2030").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubjects(VALID_SUBJECT_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_BOB).build();

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns a list of typical students.
     */
    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HARRY));
    }
}
