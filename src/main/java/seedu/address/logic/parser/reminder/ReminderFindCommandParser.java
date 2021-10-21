package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.reminder.ReminderFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ReminderFindCommand object
 */
public class ReminderFindCommandParser implements Parser<ReminderFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderFindCommand
     * and returns a ReminderFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ReminderFindCommand(new ReminderNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
