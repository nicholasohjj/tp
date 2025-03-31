package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Panel containing the list of lessons/students.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Object> listView;

    /**
     * Creates a {@code LessonListPanel} with the given {@code ObservableList}.
     */
    @SuppressWarnings("unchecked")
    // This is to allow for the method to accept both ObservableList<Student> and ObservableList<Lesson>
    // and since both are generated from UniqueListStudent and UniqueListLesson respectively, there is no
    // risk of data pollution
    public ListPanel(ObservableList<?> list) {
        super(FXML);
        listView.setItems((ObservableList<Object>) list);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} or {@code Student} using a {@code ListCard}.
     */
    class ListViewCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object obj, boolean empty) {
            if (obj instanceof Lesson curr) {
                super.updateItem(curr, empty);
                if (empty || curr == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new ListCard(curr, getIndex() + 1).getRoot());
                }
            } else if (obj instanceof Student curr) {
                super.updateItem(curr, empty);
                if (empty || curr == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new ListCard(curr, getIndex() + 1).getRoot());
                }
            }


        }
    }

}
