package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.reminder.Reminder;

public class ReminderDeleteCommand extends Command {
    public static final String COMMAND_WORD = "deleter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Reminder identified by the index number used in the displayed Reminder list.\n"
            + "Parameters: INDEX (must be a positive integer less than 1,000,000,000)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private final Index targetReminderIndex;

    public ReminderDeleteCommand(Index targetReminderIndex) {
        this.targetReminderIndex = targetReminderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        if (targetReminderIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }
        Reminder reminderToDelete = lastShownList.get(targetReminderIndex.getZeroBased());
        for (Cca cca : model.getAddressBook().getCcaList()) {
            if (cca.getName().fullName == reminderToDelete.getCcaName()) {
                cca.removeReminder(reminderToDelete);
            }
        }

        model.deleteReminder(reminderToDelete);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDeleteCommand // instanceof handles nulls
                && targetReminderIndex.equals(((ReminderDeleteCommand) other).targetReminderIndex)); // state check
    }
}
