package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IndexExceedsCapacityException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.reminder.ReminderAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;

public class ReminderAddCommandParser implements Parser<ReminderAddCommand> {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderAddCommand
     * and returns an ReminderAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CCA_ID, PREFIX_NAME, PREFIX_START_DATE,
                PREFIX_FREQUENCY, PREFIX_OCCURRENCES);

        if (!arePrefixesPresent(argMultimap, PREFIX_CCA_ID, PREFIX_NAME, PREFIX_START_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE));
        }

        if ((argMultimap.getValue(PREFIX_FREQUENCY).isPresent()
                    && !argMultimap.getValue(PREFIX_OCCURRENCES).isPresent())
                || (!argMultimap.getValue(PREFIX_FREQUENCY).isPresent()
                    && argMultimap.getValue(PREFIX_OCCURRENCES).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderAddCommand.MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE));
        }

        ReminderName reminderName = ParserUtil.parseReminderName(argMultimap.getValue(PREFIX_NAME).get());
        ReminderStartDate reminderStartDate = ParserUtil.parseReminderStartDate(
                argMultimap.getValue(PREFIX_START_DATE).get());
        Index ccaIndex;
        try {
            ccaIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CCA_ID).get());
        } catch (IndexExceedsCapacityException iie) {
            throw new ParseException(MESSAGE_INVALID_CCA_DISPLAYED_INDEX + "\n" + iie.getMessage());
        }
        ReminderFrequency reminderFrequency = argMultimap.getValue(PREFIX_FREQUENCY).isPresent()
                ? ParserUtil.parseReminderFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get())
                : ParserUtil.parseReminderFrequency("");
        ReminderOccurrence reminderOccurrence = argMultimap.getValue(PREFIX_OCCURRENCES).isPresent()
                ? ParserUtil.parseReminderOccurrence(argMultimap.getValue(PREFIX_OCCURRENCES).get())
                : ParserUtil.parseReminderOccurrence("");

        // Create a new reminder
        Reminder reminder = new Reminder(reminderName, reminderStartDate, reminderFrequency, reminderOccurrence);
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
