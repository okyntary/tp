package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminder.ReminderAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderStartDate;

public class ReminderAddCommandParser implements Parser<ReminderAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReminderAddCommand
     * and returns an ReminderAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CCA_ID, PREFIX_NAME, PREFIX_START_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE));
        }

        ReminderName reminderName = ParserUtil.parseReminderName(argMultimap.getValue(PREFIX_NAME).get());
        ReminderStartDate reminderStartDate = ParserUtil.parseReminderStartDate(
                argMultimap.getValue(PREFIX_START_DATE).get());
        Index ccaIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CCA_ID).get());

        // TODO: check date is valid?
        // Create a new reminder
        Reminder reminder = new Reminder(reminderName, reminderStartDate);
        assert(reminder.isSameReminder(reminder));
        return new ReminderAddCommand(reminder, ccaIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
