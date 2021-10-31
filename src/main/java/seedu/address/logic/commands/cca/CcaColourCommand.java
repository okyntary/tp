package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;

/**
 * Changes a CCA's colour.
 */
public class CcaColourCommand extends Command {

    public static final String COMMAND_WORD = "colourc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the colour of all CCA tags. "
            + "Parameters: "
            + PREFIX_COLOUR + "RED GREEN BLUE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COLOUR + "255 90 10";

    public static final String MESSAGE_SUCCESS = "CCA tags colour changed";
    public static final String MESSAGE_RGB_CONSTRAINTS =
            "RGB values should be 3 space-separated integers from 0 to 255 inclusive.";

    private final TagColour tagColour;

    /**
     * Creates a CcaColourCommand to change the {@code TagColour} of all {@Cca}s
     */
    public CcaColourCommand(TagColour tagColour) {
        this.tagColour = tagColour;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: reload all tags
        requireNonNull(model);
        Tag.setCcaTagColour(tagColour);

        ObservableList<Reminder> reminderList = model.getAddressBook().getReminderList();
        for (int i = 0; i < reminderList.size(); i++) {
            Reminder reminder = reminderList.get(i);
            model.setReminder(reminder, reminder);
        }
        // add personList here if we're including cca tags for persons

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaColourCommand // instanceof handles nulls
                && tagColour.equals(((CcaColourCommand) other).tagColour));
    }
}
