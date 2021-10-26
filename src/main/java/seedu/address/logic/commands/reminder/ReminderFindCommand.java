package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
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

        CcaNameContainsKeywordsPredicate filteredCcaPredicate = getFilterForCcaList((model.getFilteredReminderList()));
        model.updateFilteredCcaList(filteredCcaPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW, model.getFilteredReminderList().size()));
    }

    private CcaNameContainsKeywordsPredicate getFilterForCcaList(ObservableList<Reminder> reminderList) {
        Set<Cca> allValidCcas = new HashSet<>();
        for (Reminder validReminder: reminderList) {
            allValidCcas.add(validReminder.getCca());
        }
        List<String> ccaNames = allValidCcas
                .stream().map(Cca::getName).map(CcaName::toString).collect(Collectors.toList());
        return new CcaNameContainsKeywordsPredicate(ccaNames);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderFindCommand // instanceof handles nulls
                && predicate.equals(((ReminderFindCommand) other).predicate)); // state check
    }
}
