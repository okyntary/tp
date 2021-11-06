package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cca.CcaFindCommand;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;

public class CcaFindCommandParserTest {

    private CcaFindCommandParser parser = new CcaFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CcaFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        CcaFindCommand expectedCcaFindCommand =
                new CcaFindCommand(new CcaNameContainsKeywordsPredicate(Arrays.asList("NUSSO", "Track")));
        assertParseSuccess(parser, "NUSSO Track", expectedCcaFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n NUSSO \n \t Track  \t", expectedCcaFindCommand);
    }
}
