package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javafx.beans.binding.Bindings;
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
        name.setWrapText(true);
        this.date.setText(reminder.getNextDate());
        this.frequency.setText("Frequency: " + reminder.getFrequency().toString());
        this.occurrences.setText("Occurrences Left: " + reminder.getOccurrences().toString());

        tags.getChildren().add(new Label(this.reminder.getCcaName()));

        // if reminder.getNextDate() is today, then change colour
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Singapore")); // check if this is needed
        String currDate = df.format(date);
        if (reminder.getNextDate().equals(currDate)) {
            this.cardPane.backgroundProperty().bind(Bindings.createObjectBinding(() ->
                new Background(new BackgroundFill(Color.rgb(255, 40, 0, 0.7),
                        CornerRadii.EMPTY, Insets.EMPTY))));
        }
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
