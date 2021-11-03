package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.address.model.person.PersonIsInCcaPredicate;
import seedu.address.model.reminder.ReminderContainsCcaNamePredicate;


/**
 * Finds and lists all CCAs in address book whose name contains any of the argument keywords.
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
        model.resetAllFilteredLists();

        model.updateFilteredCcaList(predicate);

        PersonIsInCcaPredicate personPredicate = getFilterForPersonList(model.getFilteredCcaList());
        model.updateFilteredPersonList(personPredicate);

        ReminderContainsCcaNamePredicate reminderPredicate = getFilterForRemainderList(model.getFilteredCcaList());
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

    private PersonIsInCcaPredicate getFilterForPersonList(ObservableList<Cca> ccaList) {
        return new PersonIsInCcaPredicate(ccaList);
    }

    private ReminderContainsCcaNamePredicate getFilterForRemainderList(ObservableList<Cca> ccaList) {
        List<String> ccaNameList = ccaList.parallelStream()
                .map(cca -> cca.getName().toString()).collect(Collectors.toList());
        return new ReminderContainsCcaNamePredicate(ccaNameList);
    }
}
