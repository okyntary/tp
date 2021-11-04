package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OCCURRENCES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_CID_FIRST_CCA;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_CID_SECOND_CCA;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_FREQUENCY_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_FREQUENCY_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_NAME_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_OCCURRENCES_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_OCCURRENCES_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_START_DATE_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_START_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CID_FIRST_CCA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURRENCES_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_CLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalReminders.CLASS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminder.ReminderAddCommand;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.util.Frequency;
import seedu.address.testutil.ReminderBuilder;

public class ReminderAddCommandParserTest {
    private ReminderAddCommandParser parser = new ReminderAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder(CLASS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS,
                new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));

        // multiple cids - last cid accepted
        assertParseSuccess(parser, REMINDER_CID_SECOND_CCA + REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_MEETING
                + REMINDER_NAME_DESC_CLASS + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS
                + REMINDER_OCCURRENCES_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));

        // multiple names - last name accepted
        assertParseSuccess(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_MEETING
                + REMINDER_NAME_DESC_CLASS + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS
                + REMINDER_OCCURRENCES_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));

        // multiple phones - last start date accepted
        assertParseSuccess(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_MEETING + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS
                + REMINDER_OCCURRENCES_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));

        // multiple emails - last frequency accepted
        assertParseSuccess(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_MEETING + REMINDER_FREQUENCY_DESC_CLASS
                + REMINDER_OCCURRENCES_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));

        // multiple addresses - last occurrences accepted
        assertParseSuccess(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_MEETING
                + REMINDER_OCCURRENCES_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no frequency and occurrences
        Reminder expectedReminder = new ReminderBuilder(CLASS).withFrequency(Frequency.ONE_OFF, 1)
                .withOccurrence(1).withCcaName("").build();
        assertParseSuccess(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS, new ReminderAddCommand(expectedReminder, INDEX_FIRST_CCA));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE);

        // missing cid prefix
        assertParseFailure(parser, VALID_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + VALID_REMINDER_NAME_CLASS
                + REMINDER_START_DATE_DESC_CLASS, expectedMessage);

        // missing start date prefix
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + VALID_START_DATE_CLASS, expectedMessage);

        // missing frequency prefix with occurrence prefix present
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + VALID_FREQUENCY_CLASS + REMINDER_FREQUENCY_DESC_CLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE));

        // missing occurrence prefix with frequency prefix present
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + VALID_OCCURRENCES_CLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE));

        // all prefixes missing
        assertParseFailure(parser, VALID_CID_FIRST_CCA + VALID_REMINDER_NAME_CLASS + VALID_START_DATE_CLASS
                + VALID_FREQUENCY_CLASS + VALID_OCCURRENCES_CLASS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + INVALID_REMINDER_NAME_DESC
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS,
                ReminderName.MESSAGE_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + INVALID_START_DATE_DESC + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS,
                ReminderStartDate.PARSE_DATE_CONSTRAINTS);

        // invalid frequency
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + INVALID_FREQUENCY_DESC + REMINDER_OCCURRENCES_DESC_CLASS,
                ReminderFrequency.MESSAGE_CONSTRAINTS);

        // invalid occurrences
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + INVALID_OCCURRENCES_DESC,
                ReminderOccurrence.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, REMINDER_CID_FIRST_CCA + INVALID_REMINDER_NAME_DESC
                + INVALID_START_DATE_DESC + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS,
                ReminderName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + REMINDER_CID_FIRST_CCA + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderAddCommand.MESSAGE_USAGE));
    }
}
