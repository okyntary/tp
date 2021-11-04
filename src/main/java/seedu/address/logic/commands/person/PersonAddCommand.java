package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MAXIMUM_PERSONS_CAPACITY_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to ePoch.
 */
public class PersonAddCommand extends Command {

    public static final String COMMAND_WORD = "addp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to ePoch. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in ePoch";

    private final Person toAdd;

    /**
     * Creates a PersonAddCommand to add the specified {@code Person}
     */
    public PersonAddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.getNumberOfPersons() == model.MAXIMUM_CAPACITY_PERSONS) {
            throw new CommandException(MESSAGE_MAXIMUM_PERSONS_CAPACITY_REACHED);
        }

        model.addPerson(toAdd);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonAddCommand // instanceof handles nulls
                && toAdd.equals(((PersonAddCommand) other).toAdd));
    }
}
