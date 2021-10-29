package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;

/**
 * Changes a tag's colour.
 */
public class TagColourCommand extends Command {

    public static final String COMMAND_WORD = "colourt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the colour of a tag. "
            + "Parameters: "
            + PREFIX_NAME + "TAG NAME "
            + PREFIX_COLOUR + "RED GREEN BLUE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "music "
            + PREFIX_COLOUR + "255 90 10";

    public static final String MESSAGE_SUCCESS = "Tag colour changed: %1$s";
    public static final String MESSAGE_MISSING_TAG = "This tag does not exist in ePoch";
    public static final String MESSAGE_RGB_CONSTRAINTS =
            "RGB values should be 3 space-separated integers from 0 to 255 inclusive.";

    private final String tagName;
    private final TagColour tagColour;

    /**
     * Creates a TagColourCommand to change the {@code TagColour} of the {@Tag}
     */
    public TagColourCommand(String tagName, TagColour tagColour) {
        this.tagName = tagName;
        this.tagColour = tagColour;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!Tag.hasTagBeenCreated(tagName)) {
            throw new CommandException(MESSAGE_MISSING_TAG);
        }

        Tag.setTagColour(tagName, tagColour);

        ObservableList<Cca> ccaList = model.getAddressBook().getCcaList();
        for (int i = 0; i < ccaList.size(); i++) {
            Cca cca = ccaList.get(i);
            model.setCca(cca, cca); //refresh ALL ccas (should be faster than running through all cca and checking tags)
        }
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            model.setPerson(person, person);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColourCommand // instanceof handles nulls
                && tagName.equals(((TagColourCommand) other).tagName)
                && tagColour.equals(((TagColourCommand) other).tagColour));
    }
}
