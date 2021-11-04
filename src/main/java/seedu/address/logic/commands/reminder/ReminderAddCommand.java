package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MAXIMUM_REMINDERS_CAPACITY_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
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
import seedu.address.model.reminder.Reminder;


/**
 * Adds a Reminder to ePoch.
 */
public class ReminderAddCommand extends Command {

    public static final String COMMAND_WORD = "addr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to ePoch. "
            + "Parameters: "
            + PREFIX_CCA_ID + "CCA_ID "
            + PREFIX_NAME + "REMINDER_NAME "
            + PREFIX_START_DATE + "START_DATE "
            + "[" + PREFIX_FREQUENCY + "FREQUENCY] "
            + "[" + PREFIX_OCCURRENCES + "OCCURRENCES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_ID + "1 "
            + PREFIX_NAME + "NUSSO rehearsal "
            + PREFIX_START_DATE + "2021-10-31 "
            + PREFIX_FREQUENCY + "1w "
            + PREFIX_OCCURRENCES + "5";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";
    public static final String MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE =
            "Please input either both frequency and occurrence or neither of them.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This Reminder already exists in ePoch";
    private static final String MESSAGE_MISSING_CCA = "This CCA does not exist in ePoch";
    private static final String MESSAGE_PRESENT_REMINDER = "This reminder (%1$s) is already part of this CCA (%2$s)";

    private final Reminder toAdd;
    private Cca ccaToAddInto;
    private final Index targetCcaIndex;

    /**
     * Creates an ReminderAddCommand to add the specified {@code Reminder}
     */
    public ReminderAddCommand(Reminder reminder, Index targetCcaIndex) {
        requireNonNull(reminder);
        this.toAdd = reminder;
        this.targetCcaIndex = targetCcaIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownCcaList = model.getFilteredCcaList();
        if (targetCcaIndex.getZeroBased() >= lastShownCcaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        this.ccaToAddInto = lastShownCcaList.get(targetCcaIndex.getZeroBased());
        if (ccaToAddInto == null) {
            throw new CommandException(MESSAGE_MISSING_CCA);
        }

        if (model.hasReminder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        if (model.getNumberOfReminders() == model.MAXIMUM_CAPACITY_REMINDERS) {
            throw new CommandException(MESSAGE_MAXIMUM_REMINDERS_CAPACITY_REACHED);
        }

        boolean success = model.addReminder(toAdd, ccaToAddInto);

        if (success) {
            toAdd.setCcaName(ccaToAddInto.getName().fullName);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
            model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName(), ccaToAddInto.getName()));
        } else {
            throw new CommandException(
                    String.format(MESSAGE_PRESENT_REMINDER, toAdd.getName(), ccaToAddInto.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.reminder.ReminderAddCommand
                && toAdd.equals(((seedu.address.logic.commands.reminder.ReminderAddCommand) other).toAdd));
    }
}
