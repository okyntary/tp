package seedu.address.logic.parser.reminder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminder.ReminderFindCommand;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;

public class ReminderFindCommandParserTest {

    private ReminderFindCommandParser parser = new ReminderFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReminderFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ReminderFindCommand expectedReminderFindCommand =
                new ReminderFindCommand(new ReminderNameContainsKeywordsPredicate(Arrays.asList("Meeting", "Class")));
        assertParseSuccess(parser, "Meeting Class", expectedReminderFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Meeting \n \t Class  \t", expectedReminderFindCommand);
    }
}
