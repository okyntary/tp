package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.reminder.Reminder;

public class CcaDeleteCommand extends Command {
    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the CCA identified by the index number used in the displayed CCA list.\n"
            + "Parameters: INDEX (must be a positive integer less than 1,000,000,000)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CCA_SUCCESS = "Deleted CCA: %1$s";

    private final Index targetCcaIndex;

    public CcaDeleteCommand(Index targetCcaIndex) {
        this.targetCcaIndex = targetCcaIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getFilteredCcaList();

        if (targetCcaIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        Cca ccaToDelete = lastShownList.get(targetCcaIndex.getZeroBased());
        Set<Reminder> remindersToDelete = ccaToDelete.getReminders();
        ObservableList<Reminder> reminderList = model.getAddressBook().getReminderList();
        for (int i = 0; i < reminderList.size(); ) {
            Reminder reminder = reminderList.get(i);
            if (remindersToDelete.contains(reminder)) {
                model.deleteReminder(reminder);
            } else {
                i++;
            }
        }
        model.deleteCca(ccaToDelete);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaDeleteCommand // instanceof handles nulls
                && targetCcaIndex.equals(((CcaDeleteCommand) other).targetCcaIndex)); // state check
    }
}
