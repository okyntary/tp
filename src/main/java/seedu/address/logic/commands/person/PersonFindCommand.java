package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class PersonFindCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public PersonFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        CcaNameContainsKeywordsPredicate ccaPredicate = getFilterForCcaList(model);
        model.updateFilteredCcaList(ccaPredicate);

        ReminderNameContainsKeywordsPredicate reminderPredicate = getFilterForRemainderList(model.getFilteredCcaList());
        model.updateFilteredReminderList(reminderPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    private CcaNameContainsKeywordsPredicate getFilterForCcaList(Model model) {
        Set<Cca> allValidCcas = new HashSet<>();
        for (Person validPerson: model.getFilteredPersonList()) {
            for (Cca cca: model.getFilteredCcaList()) {
                if (cca.checkPerson(validPerson)) {
                    allValidCcas.add(cca);
                }
            }
        }
        List<String> allCcaNames = allValidCcas
                .stream().map(Cca::getName).map(CcaName::toString).collect(Collectors.toList());
        return new CcaNameContainsKeywordsPredicate(allCcaNames);
    }

    private ReminderNameContainsKeywordsPredicate getFilterForRemainderList(ObservableList<Cca> ccaList) {
        Set<Reminder> allReminders = new HashSet<>();
        for (Cca validCca: ccaList) {
            allReminders.addAll(validCca.getReminders());
        }
        List<String> allReminderNames = allReminders
                .stream().map(Reminder::getName).map(ReminderName::toString).collect(Collectors.toList());
        return new ReminderNameContainsKeywordsPredicate(allReminderNames);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonFindCommand // instanceof handles nulls
                && predicate.equals(((PersonFindCommand) other).predicate)); // state check
    }
}
