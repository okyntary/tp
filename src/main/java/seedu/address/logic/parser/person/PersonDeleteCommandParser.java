package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IndexExceedsCapacityException;
import seedu.address.logic.commands.person.PersonDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PersonDeleteCommand object
 */
public class PersonDeleteCommandParser implements Parser<PersonDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PersonDeleteCommand
     * and returns a PersonDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PersonDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PersonDeleteCommand(index);
        } catch (IndexExceedsCapacityException iece) {
            throw new ParseException(iece.getMessage());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
