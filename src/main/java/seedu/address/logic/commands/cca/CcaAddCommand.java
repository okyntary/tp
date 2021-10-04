package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;

/**
 * Adds a CCA to the address book.
 */
public class CcaAddCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a CCA to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NUS String Orchestra ";

    public static final String MESSAGE_SUCCESS = "New CCA added: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This CCA already exists in the address book";

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

        model.addCca(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaAddCommand // instanceof handles nulls
                && toAdd.equals(((CcaAddCommand) other).toAdd));
    }
}
