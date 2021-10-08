package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;

public class CcaDeleteCommand extends Command {
    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the CCA identified by the index number used in the displayed CCA list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CCA_SUCCESS = "Deleted CCA: %1$s";

    private final Index targetIndex;

    public CcaDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getFilteredCcaList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        Cca ccaToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCca(ccaToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((CcaDeleteCommand) other).targetIndex)); // state check
    }
}
