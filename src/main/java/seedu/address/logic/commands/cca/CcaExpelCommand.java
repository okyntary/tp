package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.Cid;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pid;

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
    public static final String MESSAGE_MISSING_CCA = "This CCA does not exist in the address book";
    public static final String MESSAGE_MISSING_PERSON = "This person does not exist in the address book";
    public static final String MESSAGE_MISSING_PERSON_FROM_CCA = "This person(%1$s) is not enrolled in this CCA(%2$s)";

    private Cca ccaToEnrolInto;
    private final int cid;
    private Person personToExpel;
    private final int pid;

    /**
     * Creates an CcaAddCommand to add the specified {@code Cca}
     */
    public CcaExpelCommand(Cid cid, Pid pid) {
        this.cid = Integer.parseInt(cid.toString());
        this.pid = Integer.parseInt(pid.toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        this.ccaToEnrolInto = model.findCcaFromCid(cid);
        this.personToExpel = model.findPersonFromPid(pid);

        if (ccaToEnrolInto == null) {
            throw new CommandException(MESSAGE_MISSING_CCA);
        }

        if (personToExpel == null) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }

        boolean success = model.expelPersonFromCca(ccaToEnrolInto, personToExpel);
        if (success) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToExpel.getName(), ccaToEnrolInto.getName()));
        } else {
            throw new CommandException(
                    String.format(MESSAGE_MISSING_PERSON_FROM_CCA, personToExpel.getName(), ccaToEnrolInto.getName()));
        }
    }

    public Cca getCcaToEnrolInto() {
        return this.ccaToEnrolInto;
    }

    public Person getPersonToExpel() {
        return this.personToExpel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaExpelCommand // instanceof handles nulls
                && ccaToEnrolInto.equals(((CcaExpelCommand) other).getCcaToEnrolInto())
                && personToExpel.equals(((CcaExpelCommand) other).getPersonToExpel()));
    }
}
