package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cca.CcaDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.Cid;

/**
 * Parses input arguments and creates a new CcaDeleteCommand object
 */
public class CcaDeleteCommandParser implements Parser<CcaDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PersonDeleteCommand
     * and returns a PersonDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CcaDeleteCommand parse(String args) throws ParseException {
        try {
            Cid cid = ParserUtil.parseCid(args);
            return new CcaDeleteCommand(cid);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
