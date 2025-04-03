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
    private static final String DONE_STYLE = "-fx-background-color: #4CAF50;";
    private static final String NOT_DONE_STYLE = "-fx-background-color: #f44336;";

    @FXML
    private Label assignmentName;
    @FXML
    private Label dueDate;
    @FXML
    private Label statusLabel;
    @FXML
    private HBox cardPane;

    /**
     * Creates a {@code AssignmentCard} with the given {@code Assignment}.
     */
    public AssignmentCard(Assignment assignment) {
        super(FXML);
        assignmentName.setText(assignment.getAssignmentName().toString());
        dueDate.setText("Due: " + assignment.getDueDate().toString());
        updateCardStyle(assignment);
    }

    /**
     * Updates the card style based on the assignment status.
     */
    private void updateCardStyle(Assignment assignment) {
        if (assignment.isDone()) {
            cardPane.setStyle(DONE_STYLE);
            statusLabel.setText("Completed");
        } else {
            cardPane.setStyle(NOT_DONE_STYLE);
            statusLabel.setText("Pending");
        }
    }

    // Getters for testing purposes
    public String getAssignmentName() {
        return assignmentName.getText();
    }

    public String getDueDateText() {
        return dueDate.getText();
    }

    public String getStatusText() {
        return statusLabel.getText();
    }

    public String getCardStyle() {
        return cardPane.getStyle();
    }
}
