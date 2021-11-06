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

public class CcaEnrolCommand extends Command {

    public static final String COMMAND_WORD = "enrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrols a person into a CCA. "
            + "Parameters: "
            + PREFIX_CCA_ID + "CCA_ID "
            + PREFIX_PERSON_ID + "CCA_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_ID + "1 "
            + PREFIX_PERSON_ID + "1 ";

    public static final String MESSAGE_SUCCESS = "Successfully enrolled %1$s into %2$s";
    public static final String MESSAGE_MISSING_CCA = "This CCA does not exist in ePoch";
    public static final String MESSAGE_MISSING_PERSON = "This person does not exist in ePoch";
    public static final String MESSAGE_PRESENT_PERSON = "This person(%1$s) is already part of that CCA(%2$s)";

    private Cca ccaToEnrolInto;
    private final Index targetCcaIndex;
    private Person personToEnrol;
    private final Index targetPersonIndex;

    /**
     * Creates an CcaAddCommand to add the specified {@code Cca}
     */
    public CcaEnrolCommand(Index targetCcaIndex, Index targetPersonIndex) {
        this.targetCcaIndex = targetCcaIndex;
        this.targetPersonIndex = targetPersonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Cca> lastShownCcaList = model.getFilteredCcaList();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (targetCcaIndex.getZeroBased() >= lastShownCcaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }
        if (targetPersonIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        this.ccaToEnrolInto = lastShownCcaList.get(targetCcaIndex.getZeroBased());
        this.personToEnrol = lastShownPersonList.get(targetPersonIndex.getZeroBased());

        if (ccaToEnrolInto == null) {
            throw new CommandException(MESSAGE_MISSING_CCA);
        }

        if (personToEnrol == null) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }

        boolean success = model.enrolPersonIntoCca(ccaToEnrolInto, personToEnrol);
        if (success) {
            model.setCca(ccaToEnrolInto, ccaToEnrolInto);
            model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToEnrol.getName(), ccaToEnrolInto.getName()));
        } else {
            throw new CommandException(
                    String.format(MESSAGE_PRESENT_PERSON, personToEnrol.getName(), ccaToEnrolInto.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaEnrolCommand // instanceof handles nulls
                && targetCcaIndex.equals(((CcaEnrolCommand) other).targetCcaIndex)
                && targetPersonIndex.equals(((CcaEnrolCommand) other).targetPersonIndex));
    }
}
