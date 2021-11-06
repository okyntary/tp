package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cca.CcaAddCommand;
import seedu.address.logic.commands.cca.CcaEditCommand.EditCcaDescriptor;
import seedu.address.logic.commands.cca.CcaEnrolCommand;
import seedu.address.logic.commands.cca.CcaExpelCommand;
import seedu.address.model.cca.Cca;
import seedu.address.model.tag.Tag;

/**
 * A utility class for CCA.
 */
public class CcaUtil {

    /**
     * Returns an add command string for adding the {@code CCA}.
     */
    public static String getAddCommand(Cca cca) {
        return CcaAddCommand.COMMAND_WORD + " " + getCcaDetails(cca);
    }

    /**
     * Returns an enrol command string for enrolling the {@code person} into the {@code CCA}.
     */
    public static String getEnrolCommand(Index ccaIndex, Index personIndex) {
        return CcaEnrolCommand.COMMAND_WORD + " " + PREFIX_CCA_ID + ccaIndex.getOneBased() + " "
                + PREFIX_PERSON_ID + personIndex.getOneBased();
    }

    /**
     * Returns an expel command string for expelling the {@code person} from the {@code CCA}.
     */
    public static String getExpelCommand(Index ccaIndex, Index personIndex) {
        return CcaExpelCommand.COMMAND_WORD + " " + PREFIX_CCA_ID + ccaIndex.getOneBased() + " "
                + PREFIX_PERSON_ID + personIndex.getOneBased();
    }

    /**
     * Returns the part of command string for the given {@code cca}'s details.
     */
    public static String getCcaDetails(Cca cca) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + cca.getName().fullName + " ");
        cca.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCcaDescriptor}'s details.
     */
    public static String getEditCcaDescriptorDetails(EditCcaDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
