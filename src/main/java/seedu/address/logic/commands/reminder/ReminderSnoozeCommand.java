package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

public class ReminderSnoozeCommand extends Command {
    public static final String COMMAND_WORD = "snoozer";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Snoozes the Reminder identified by the index number used in the displayed Reminder list.\n"
            + "Parameters: INDEX (must be a positive integer less than 1,000,000,000)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SNOOZE_LAST_OCCURRENCE_ERROR = "You can't snooze this reminder "
            + "since it has only 1 occurrence left. You can use the deleter command to delete it instead.";
    public static final String MESSAGE_SNOOZE_REMINDER_SUCCESS = "Snoozed Reminder: %1$s";

    private final Index targetReminderIndex;

    public ReminderSnoozeCommand(Index targetReminderIndex) {
        this.targetReminderIndex = targetReminderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        if (targetReminderIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToSnooze = lastShownList.get(targetReminderIndex.getZeroBased());

        if (reminderToSnooze.getOccurrences().getOccurrences() == 1) {
            throw new CommandException(MESSAGE_SNOOZE_LAST_OCCURRENCE_ERROR);
        }

        model.snoozeReminder(reminderToSnooze);
        return new CommandResult(String.format(MESSAGE_SNOOZE_REMINDER_SUCCESS, reminderToSnooze));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderSnoozeCommand // instanceof handles nulls
                && targetReminderIndex.equals(((ReminderSnoozeCommand) other).targetReminderIndex)); // state check
    }
}
