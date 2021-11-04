package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MAXIMUM_CCAS_CAPACITY_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;

/**
 * Adds a CCA to ePoch.
 */
public class CcaAddCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a CCA to ePoch. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NUS Symphony Orchestra "
            + PREFIX_TAG + "Music "
            + PREFIX_TAG + "InterviewDependent";

    public static final String MESSAGE_SUCCESS = "New CCA added: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This CCA already exists in ePoch";

    private final Logger logger = LogsCenter.getLogger(CcaAddCommand.class);

    private final Cca toAdd;

    /**
     * Creates an CcaAddCommand to add the specified {@code Cca}
     */
    public CcaAddCommand(Cca cca) {
        requireNonNull(cca);
        toAdd = cca;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCca(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        if (model.getNumberOfCcas() == model.MAXIMUM_CAPACITY_CCAS) {
            throw new CommandException(MESSAGE_MAXIMUM_CCAS_CAPACITY_REACHED);
        }

        model.addCca(toAdd);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        logger.log(Level.FINE, "CCA successfully added");
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaAddCommand // instanceof handles nulls
                && toAdd.equals(((CcaAddCommand) other).toAdd));
    }
}
