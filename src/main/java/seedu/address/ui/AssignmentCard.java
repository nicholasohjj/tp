package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.assignment.Assignment;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class AssignmentCard extends UiPart<HBox> {

    private static final String FXML = "AssignmentCard.fxml";

    @FXML
    private Label assignmentName;

    @FXML
    private Label dueDate;

    @FXML
    private HBox cardPane;

    /**
     * Creates a {@code AssignmentCard} with the given {@code Assignment}.
     */
    public AssignmentCard(Assignment assignment) {
        super(FXML);
        assignmentName.setText(assignment.getAssignmentName());
        dueDate.setText("Due: " + assignment.getDueDate().toString());

        if (assignment.isDone()) {
            cardPane.setStyle("-fx-background-color: #4CAF50;");
        } else {
            cardPane.setStyle("-fx-background-color: #f44336;");
        }
    }
}
