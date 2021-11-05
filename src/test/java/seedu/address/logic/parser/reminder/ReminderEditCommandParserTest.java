package seedu.address.logic.parser.reminder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OCCURRENCES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_FREQUENCY_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_FREQUENCY_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_NAME_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_OCCURRENCES_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_OCCURRENCES_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_START_DATE_DESC_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_START_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_TIME_PERIOD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_TIME_PERIOD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURENCES_30;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURRENCES_50;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_PERIOD_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_PERIOD_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_REMINDER;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminder.ReminderEditCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.testutil.EditReminderDescriptorBuilder;

public class ReminderEditCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderEditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_MISSING_ONE_OF_FREQUENCY_OCCURRENCE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderEditCommand.MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE);

    private ReminderEditCommandParser parser = new ReminderEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_REMINDER_NAME_PROJECT, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_REMINDER_NAME_PROJECT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_REMINDER_NAME_PROJECT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_REMINDER_NAME_DESC, ReminderName.MESSAGE_CONSTRAINTS);
        // invalid start date
        assertParseFailure(parser, "1" + INVALID_START_DATE_DESC, ReminderStartDate.PARSE_DATE_CONSTRAINTS);
        // invalid frequency with valid occurrence
        assertParseFailure(parser, "1" + INVALID_FREQUENCY_DESC + REMINDER_OCCURRENCES_DESC_CLASS,
                ReminderFrequency.MESSAGE_CONSTRAINTS);
        // invalid occurrence with valid frequency
        assertParseFailure(parser, "1" + REMINDER_FREQUENCY_DESC_CLASS + INVALID_OCCURRENCES_DESC,
                ReminderOccurrence.MESSAGE_CONSTRAINTS);

        // invalid name followed by valid start date
        assertParseFailure(parser, "1" + INVALID_REMINDER_NAME_DESC + VALID_START_DATE_CLASS,
                ReminderName.MESSAGE_CONSTRAINTS);

        // valid start date followed by invalid start date. The test case for invalid start date followed by valid
        // start date is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + REMINDER_START_DATE_DESC_CLASS + INVALID_START_DATE_DESC,
                ReminderStartDate.PARSE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_REMINDER_NAME_DESC + INVALID_START_DATE_DESC
                + INVALID_FREQUENCY_DESC + INVALID_OCCURRENCES_DESC, ReminderName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parser_oneOfFrequencyOrOccurrence_failure() {
        // frequency without occurrence
        assertParseFailure(parser, "1" + REMINDER_FREQUENCY_DESC_CLASS,
                MESSAGE_MISSING_ONE_OF_FREQUENCY_OCCURRENCE);

        // occurrence without frequency
        assertParseFailure(parser, "1" + REMINDER_OCCURRENCES_DESC_CLASS,
                MESSAGE_MISSING_ONE_OF_FREQUENCY_OCCURRENCE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + REMINDER_NAME_DESC_CLASS + REMINDER_START_DATE_DESC_CLASS
                + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_CLASS)
                .withStartDate(getReminderStartDate(VALID_START_DATE_CLASS))
                .withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_2).withOccurrence(VALID_OCCURRENCES_50)
                .build();
        ReminderEditCommand expectedCommand = new ReminderEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // name and start date
        Index targetIndex = INDEX_FIRST_REMINDER;
        String userInput = targetIndex.getOneBased() + REMINDER_NAME_DESC_MEETING + REMINDER_START_DATE_DESC_MEETING;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_MEETING)
                .withStartDate(getReminderStartDate(VALID_START_DATE_MEETING)).build();
        ReminderEditCommand expectedCommand = new ReminderEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // frequency and occurrence
        userInput = targetIndex.getOneBased() + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS;

        descriptor = new EditReminderDescriptorBuilder().withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_2)
                .withOccurrence(VALID_OCCURRENCES_50).build();
        expectedCommand = new ReminderEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_REMINDER;
        String userInput = targetIndex.getOneBased() + REMINDER_NAME_DESC_MEETING;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withName(VALID_REMINDER_NAME_MEETING).build();
        ReminderEditCommand expectedCommand = new ReminderEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + REMINDER_START_DATE_DESC_MEETING;
        descriptor = new EditReminderDescriptorBuilder().withStartDate(getReminderStartDate(VALID_START_DATE_MEETING))
                .build();
        expectedCommand = new ReminderEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_REMINDER;
        String userInput = targetIndex.getOneBased() + REMINDER_NAME_DESC_CLASS + REMINDER_START_DATE_DESC_CLASS
                + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS + REMINDER_NAME_DESC_CLASS
                + REMINDER_START_DATE_DESC_CLASS + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS
                + REMINDER_NAME_DESC_MEETING + REMINDER_START_DATE_DESC_MEETING + REMINDER_FREQUENCY_DESC_MEETING
                + REMINDER_OCCURRENCES_DESC_MEETING;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_MEETING)
                .withStartDate(getReminderStartDate(VALID_START_DATE_MEETING))
                .withFrequency(VALID_TIME_PERIOD_WEEK, VALID_NUM_TIME_PERIOD_1).withOccurrence(VALID_OCCURENCES_30)
                .build();
        ReminderEditCommand expectedCommand = new ReminderEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_REMINDER;
        String userInput = targetIndex.getOneBased() + INVALID_REMINDER_NAME_DESC + REMINDER_NAME_DESC_CLASS;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_CLASS)
                .build();
        ReminderEditCommand expectedCommand = new ReminderEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + REMINDER_NAME_DESC_CLASS + INVALID_START_DATE_DESC
                + REMINDER_FREQUENCY_DESC_CLASS + REMINDER_OCCURRENCES_DESC_CLASS + REMINDER_START_DATE_DESC_CLASS;
        descriptor = new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_CLASS)
                .withStartDate(getReminderStartDate(VALID_START_DATE_CLASS))
                .withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_2).withOccurrence(VALID_OCCURRENCES_50)
                .build();
        expectedCommand = new ReminderEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    private Date getReminderStartDate(String dateString) {
        Date startDate = new Date();
        try {
            startDate = ReminderStartDate.PARSE_INPUT_DATE_FORMAT.parse(dateString);
        } catch (java.text.ParseException e) {
            assertTrue(false);
        }
        return startDate;
    }
}
