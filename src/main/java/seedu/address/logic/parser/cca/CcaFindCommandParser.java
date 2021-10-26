package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.cca.CcaFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new CcaFindCommand object
 */
public class CcaFindCommandParser implements Parser<CcaFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CcaFindCommand
     * and returns a CcaFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CcaFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new CcaFindCommand(new CcaNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
