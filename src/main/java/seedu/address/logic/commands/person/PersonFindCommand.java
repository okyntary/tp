package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaContainsPersonPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.ReminderContainsCcaNamePredicate;

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
        model.resetAllFilteredLists();

        model.updateFilteredPersonList(predicate);

        CcaContainsPersonPredicate ccaPredicate = getFilterForCcaList(model.getFilteredPersonList());
        model.updateFilteredCcaList(ccaPredicate);

        ReminderContainsCcaNamePredicate reminderPredicate = getFilterForRemainderList(model.getFilteredCcaList());
        model.updateFilteredReminderList(reminderPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    private CcaContainsPersonPredicate getFilterForCcaList(List<Person> personList) {
        return new CcaContainsPersonPredicate(personList);
    }

    private ReminderContainsCcaNamePredicate getFilterForRemainderList(List<Cca> ccaList) {
        List<String> ccaNameList = ccaList.parallelStream()
                .map(cca -> cca.getName().toString()).collect(Collectors.toList());
        return new ReminderContainsCcaNamePredicate(ccaNameList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonFindCommand // instanceof handles nulls
                && predicate.equals(((PersonFindCommand) other).predicate)); // state check
    }
}
