package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.cca.CcaContainsReminderPredicate;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ReminderFindCommand extends Command {

    public static final String COMMAND_WORD = "findr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting";

    private final ReminderNameContainsKeywordsPredicate predicate;

    public ReminderFindCommand(ReminderNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetFiltersForReminderList();
        model.resetFiltersForCcaList();

        model.updateFilteredReminderList(predicate);

        CcaContainsReminderPredicate filteredCcaPredicate = getFilterForCcaList((model.getFilteredReminderList()));
        model.updateFilteredCcaList(filteredCcaPredicate);
        model.updateFilteredPersonList(PREDICATE_SHOW_NO_PERSONS);

        return new CommandResult(
                String.format(Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW, model.getFilteredReminderList().size()));
    }

    private CcaContainsReminderPredicate getFilterForCcaList(ObservableList<Reminder> reminderList) {
        return new CcaContainsReminderPredicate(reminderList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderFindCommand // instanceof handles nulls
                && predicate.equals(((ReminderFindCommand) other).predicate)); // state check
    }
}
