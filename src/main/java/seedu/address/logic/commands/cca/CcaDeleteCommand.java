package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.Cid;

public class CcaDeleteCommand extends Command {
    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the CCA identified by the index number used in the displayed CCA list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CCA_SUCCESS = "Deleted CCA: %1$s";

    private final Cid targetCid;

    public CcaDeleteCommand(Cid targetCid) {
        this.targetCid = targetCid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getFilteredCcaList();

        Optional<Cca> ccaToDelete = lastShownList.stream().filter(cca -> cca.cidEquals(targetCid)).findFirst();

        if (!ccaToDelete.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        model.deleteCca(ccaToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaDeleteCommand // instanceof handles nulls
                && targetCid.equals(((CcaDeleteCommand) other).targetCid)); // state check
    }
}
