package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Adds a Reminder to the address book.
 */
public class ReminderAddCommand extends Command {

    public static final String COMMAND_WORD = "addr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to the address book. "
            + "Parameters: "
            + PREFIX_CCA_ID + "CCA_ID "
            + PREFIX_NAME + "REMINDER_NAME "
            + PREFIX_START_DATE + "START_DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_ID + "1 "
            + PREFIX_NAME + "NUSSO rehearsal "
            + PREFIX_START_DATE + "2021-10-31";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This Reminder already exists in the address book";

    private final Reminder toAdd;

    /**
     * Creates an ReminderAddCommand to add the specified {@code Reminder}
     */
    public ReminderAddCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasReminder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.addReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.reminder.ReminderAddCommand
                && toAdd.equals(((seedu.address.logic.commands.reminder.ReminderAddCommand) other).toAdd));
    }
}
