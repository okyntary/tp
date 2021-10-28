package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IndexExceedsCapacityException;
import seedu.address.logic.commands.reminder.ReminderSnoozeCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReminderSnoozeCommand object
 */
public class ReminderSnoozeCommandParser implements Parser<ReminderSnoozeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReminderSnoozeCommand
     * and returns a ReminderSnoozeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderSnoozeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReminderSnoozeCommand(index);
        } catch (IndexExceedsCapacityException iece) {
            throw new ParseException(iece.getMessage());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderSnoozeCommand.MESSAGE_USAGE), pe);
        }
    }
}
