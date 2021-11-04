package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CCA_NAME_DESC_NUSSO;
import static seedu.address.logic.commands.CommandTestUtil.CCA_NAME_DESC_USKICK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCcas.NUSSO;
import static seedu.address.testutil.TypicalCcas.USKICK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cca.CcaAddCommand;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CcaBuilder;

public class CcaAddCommandParserTest {
    private CcaAddCommandParser parser = new CcaAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Cca expectedCca = new CcaBuilder(USKICK).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CCA_NAME_DESC_USKICK
                + TAG_DESC_FRIEND, new CcaAddCommand(expectedCca));

        // multiple names - last name accepted
        assertParseSuccess(parser, CCA_NAME_DESC_NUSSO + CCA_NAME_DESC_USKICK
                + TAG_DESC_FRIEND, new CcaAddCommand(expectedCca));

        // multiple tags - all accepted
        Cca expectedCcaMultipleTags = new CcaBuilder(USKICK).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, CCA_NAME_DESC_USKICK
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new CcaAddCommand(expectedCcaMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Cca expectedCca = new CcaBuilder(NUSSO).withTags().build();
        assertParseSuccess(parser, CCA_NAME_DESC_NUSSO,
                new CcaAddCommand(expectedCca));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_CCA_NAME_TRACK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CCA_NAME_TRACK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid CCA name
        assertParseFailure(parser, INVALID_CCA_NAME_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, CCA_NAME_DESC_USKICK
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CCA_NAME_DESC + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CCA_NAME_DESC_USKICK
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaAddCommand.MESSAGE_USAGE));
    }
}
