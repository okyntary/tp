package seedu.address.logic.parser.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IndexExceedsCapacityException;
import seedu.address.logic.commands.reminder.ReminderEditCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;



public class ReminderEditCommandParser implements Parser<ReminderEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderEditCommand
     * and returns an PersonEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ReminderEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_FREQUENCY, PREFIX_OCCURRENCES);

        Index index;

        try {
            index = ParserUtil.parseIndex((argMultimap.getPreamble()));
        } catch (IndexExceedsCapacityException iece) {
            throw new ParseException(iece.getMessage());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderEditCommand.MESSAGE_USAGE), pe);
        }

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editReminderDescriptor.setReminderName(ParserUtil
                    .parseReminderName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editReminderDescriptor.setReminderStartDate(ParserUtil
                    .parseReminderStartDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }

        if ((argMultimap.getValue(PREFIX_FREQUENCY).isPresent()
                && !argMultimap.getValue(PREFIX_OCCURRENCES).isPresent())
                || (!argMultimap.getValue(PREFIX_FREQUENCY).isPresent()
                && argMultimap.getValue(PREFIX_OCCURRENCES).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderEditCommand.MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE));
        }

        if (argMultimap.getValue(PREFIX_FREQUENCY).isPresent()) {
            editReminderDescriptor.setReminderFrequency(ParserUtil
                    .parseReminderFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get()));
        }
        if (argMultimap.getValue(PREFIX_OCCURRENCES).isPresent()) {
            editReminderDescriptor.setReminderOccurrence(ParserUtil
                    .parseReminderOccurrence(argMultimap.getValue(PREFIX_OCCURRENCES).get()));
        }

        return new ReminderEditCommand(index, editReminderDescriptor);
    }
}

