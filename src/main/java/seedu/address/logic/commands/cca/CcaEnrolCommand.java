package seedu.address.logic.commands.cca;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

public class CcaEnrolCommand extends Command {

    public static final String COMMAND_WORD = "enrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrols a person into a CCA. "
            + "Parameters: "
            + PREFIX_CCA_ID + "CCA_ID "
            + PREFIX_PERSON_ID + "CCA_ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_ID + "1 "
            + PREFIX_PERSON_ID + "1 ";

    public static final String MESSAGE_SUCCESS = "Successfully enrolled %1$s into %1$s";
    public static final String MESSAGE_MISSING_CCA = "This CCA does not exist in the address book";
    public static final String MESSAGE_MISSING_PERSON = "This person does not exist in the address book";

    private Cca ccaToEnrolInto;
    private final int cid;
    private Person personToEnrol;
    private final int pid;

    /**
     * Creates an CcaAddCommand to add the specified {@code Cca}
     */
    public CcaEnrolCommand(int cid, int pid) {
        this.cid = cid;
        this.pid = pid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find cca
        // Find person
        this.ccaToEnrolInto = model.findCcaFromCid(cid);
        this.personToEnrol = model.findPersonFromPid(pid);

        // Implement the methods above

        if (!model.hasCca(ccaToEnrolInto)) {
            throw new CommandException(MESSAGE_MISSING_CCA);
        }

        if (!model.hasPerson(personToEnrol)) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }

        model.enrolPersonIntoCca(ccaToEnrolInto, personToEnrol);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEnrol.getName(), ccaToEnrolInto.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaEnrolCommand // instanceof handles nulls
                && ccaToEnrolInto.equals(((CcaEnrolCommand) other).ccaToEnrolInto)
                && personToEnrol.equals(((CcaEnrolCommand) other).personToEnrol));
    }
}
