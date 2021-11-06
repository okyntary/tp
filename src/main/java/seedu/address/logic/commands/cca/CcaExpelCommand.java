package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
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
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

public class CcaExpelCommand extends Command {

    public static final String COMMAND_WORD = "expel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Expels a person from a CCA. "
            + "Parameters: "
            + PREFIX_CCA_ID + "CCA_ID "
            + PREFIX_PERSON_ID + "CCA_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_ID + "1 "
            + PREFIX_PERSON_ID + "1 ";

    public static final String MESSAGE_SUCCESS = "Successfully expelled %1$s from %2$s";
    public static final String MESSAGE_MISSING_CCA = "This CCA does not exist in ePoch";
    public static final String MESSAGE_MISSING_PERSON = "This person does not exist in ePoch";
    public static final String MESSAGE_MISSING_PERSON_FROM_CCA = "This person(%1$s) is not enrolled in this CCA(%2$s)";

    private Cca ccaToExpelFrom;
    private final Index targetCcaIndex;
    private Person personToExpel;
    private final Index targetPersonIndex;

    /**
     * Creates an CcaAddCommand to add the specified {@code Cca}
     */
    public CcaExpelCommand(Index targetCcaIndex, Index targetPersonIndex) {
        this.targetCcaIndex = targetCcaIndex;
        this.targetPersonIndex = targetPersonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Cca> lastShownCcaList = model.getFilteredCcaList();
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Reminder> lastShownReminderList = model.getFilteredReminderList();

        if (targetCcaIndex.getZeroBased() >= lastShownCcaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }
        if (targetPersonIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        this.ccaToExpelFrom = lastShownCcaList.get(targetCcaIndex.getZeroBased());
        this.personToExpel = lastShownPersonList.get(targetPersonIndex.getZeroBased());

        if (ccaToExpelFrom == null) {
            throw new CommandException(MESSAGE_MISSING_CCA);
        }

        if (personToExpel == null) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }

        boolean success = model.expelPersonFromCca(ccaToExpelFrom, personToExpel);
        if (success) {
            model.setCca(ccaToExpelFrom, ccaToExpelFrom);
            model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToExpel.getName(), ccaToExpelFrom.getName()));
        } else {
            throw new CommandException(
                    String.format(MESSAGE_MISSING_PERSON_FROM_CCA, personToExpel.getName(), ccaToExpelFrom.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaExpelCommand // instanceof handles nulls
                && targetCcaIndex.equals(((CcaExpelCommand) other).targetCcaIndex)
                && targetPersonIndex.equals(((CcaExpelCommand) other).targetPersonIndex));
    }
}
