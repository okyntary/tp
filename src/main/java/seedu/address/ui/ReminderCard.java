package seedu.address.ui;

import java.util.Hashtable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label frequency;
    @FXML
    private Label occurrences;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ReminderCode} with the given {@code Reminder} and index to display.
     */
    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        this.name.setText(reminder.getName().fullName);
        this.date.setText(reminder.getStartDate().toString());
//        this.frequency.setText(""); // to test if this shows up as an empty line
//        this.occurrences.setText("temp occ");

        // each CCA has a field for color? can do an enum
        // then mapping from enum value to the binding
        // should include a default value in the enum
        // example mapping (with int in place of enum value):
        Hashtable<Integer, ObjectBinding<Background>> backgrounds = new Hashtable<>();
        backgrounds.put(1, Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        })); // so 1 = red
        Label tempLabel = new Label("NUSSO");
        tempLabel.backgroundProperty().bind(backgrounds.get(1));
        tags.getChildren().add(tempLabel);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CcaCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}
