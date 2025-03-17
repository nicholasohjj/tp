package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class ListCard extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Lesson lesson;
    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label subject;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane assignments;

    /**
     * Creates a {@code LessonCode} with the given {@code Lesson} and index to display.
     */
    public ListCard(Lesson lesson, int displayedIndex) {
        super("LessonListCard.fxml");
        this.lesson = lesson;
        this.student = null;
        id.setText(displayedIndex + ". ");
        name.setText(lesson.getName().fullName);
        date.setText(lesson.getDate().value);
        time.setText(lesson.getTime().time);
        subject.setText(lesson.getSubject().subject);
    }

    public ListCard(Student student, int displayedIndex) {
        super("StudentListCard.fxml");
        this.student = student;
        this.lesson = null;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        address.setText(student.getAddress().value);
        email.setText(student.getEmail().value);
        subject.setText(student.getSubject().subject);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        student.getAssignments().stream()
                .sorted(Comparator.comparing(assignment -> assignment.value))
                .forEach(assignment -> assignments.getChildren().add(new Label(assignment.value)));
    }
}
