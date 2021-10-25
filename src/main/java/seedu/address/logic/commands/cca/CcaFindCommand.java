package seedu.address.logic.commands.cca;

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
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CcaFindCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all CCAs whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " string choir orchestra";

    private final CcaNameContainsKeywordsPredicate predicate;

    public CcaFindCommand(CcaNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCcaList(predicate);

        NameContainsKeywordsPredicate personPredicate = getFilterForPersonList(model.getFilteredCcaList());
        model.updateFilteredPersonList(personPredicate);

        ReminderNameContainsKeywordsPredicate reminderPredicate = getFilterForRemainderList(model.getFilteredCcaList());
        model.updateFilteredReminderList(reminderPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_CCAS_LISTED_OVERVIEW, model.getFilteredCcaList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaFindCommand // instanceof handles nulls
                && predicate.equals(((CcaFindCommand) other).predicate)); // state check
    }

    private NameContainsKeywordsPredicate getFilterForPersonList(ObservableList<Cca> ccaList) {
        Set<Person> allPersons = new HashSet<>();
        for (Cca validCca: ccaList) {
            allPersons.addAll(validCca.getPersonArrayList());
        }
        List<String> allPersonNames = allPersons
                .stream().map(Person::getName).map(Name::toString).collect(Collectors.toList());
        return new NameContainsKeywordsPredicate(allPersonNames);
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
}
