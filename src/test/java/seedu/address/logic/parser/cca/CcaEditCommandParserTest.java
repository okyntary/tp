package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CCA_NAME_DESC_NUSSO;
import static seedu.address.logic.commands.CommandTestUtil.CCA_NAME_DESC_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.CCA_NAME_DESC_USKICK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MUSIC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SPORTS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_USP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_NUSSO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_USKICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MUSIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SPORTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_USP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CCA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cca.CcaEditCommand;
import seedu.address.logic.commands.cca.CcaEditCommand.EditCcaDescriptor;
import seedu.address.model.cca.CcaName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCcaDescriptorBuilder;

public class CcaEditCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaEditCommand.MESSAGE_USAGE);

    private CcaEditCommandParser parser = new CcaEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CCA_NAME_NUSSO, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", CcaEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CCA_NAME_DESC_NUSSO, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CCA_NAME_DESC_NUSSO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_CCA_NAME_DESC, CcaName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid name followed by valid tag
        assertParseFailure(parser, "1" + INVALID_CCA_NAME_DESC + TAG_DESC_MUSIC, CcaName.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid name. The test case for invalid name followed by valid name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CCA_NAME_DESC_NUSSO + INVALID_CCA_NAME_DESC,
                CcaName.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Cca} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_MUSIC + TAG_DESC_USP + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_MUSIC + TAG_EMPTY + TAG_DESC_USP, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_MUSIC + TAG_DESC_USP, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CCA_NAME_DESC + INVALID_TAG_DESC,
                CcaName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CCA;
        String userInput = targetIndex.getOneBased() + CCA_NAME_DESC_NUSSO + TAG_DESC_MUSIC;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_NUSSO)
                .withTags(VALID_TAG_MUSIC).build();
        CcaEditCommand expectedCommand = new CcaEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CCA;
        String userInput = targetIndex.getOneBased() + CCA_NAME_DESC_TRACK;
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_TRACK).build();
        CcaEditCommand expectedCommand = new CcaEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // one tag
        userInput = targetIndex.getOneBased() + TAG_DESC_MUSIC;
        descriptor = new EditCcaDescriptorBuilder().withTags(VALID_TAG_MUSIC).build();
        expectedCommand = new CcaEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // multiple tags
        userInput = targetIndex.getOneBased() + TAG_DESC_MUSIC + TAG_DESC_USP + TAG_DESC_SPORTS;
        descriptor = new EditCcaDescriptorBuilder().withTags(VALID_TAG_MUSIC, VALID_TAG_USP, VALID_TAG_SPORTS).build();
        expectedCommand = new CcaEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CCA;
        String userInput = targetIndex.getOneBased() + CCA_NAME_DESC_TRACK + TAG_DESC_SPORTS + CCA_NAME_DESC_TRACK
                + TAG_DESC_SPORTS + CCA_NAME_DESC_USKICK + TAG_DESC_USP;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_USKICK)
                .withTags(VALID_TAG_SPORTS, VALID_TAG_USP).build();
        CcaEditCommand expectedCommand = new CcaEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_CCA;
        String userInput = targetIndex.getOneBased() + INVALID_CCA_NAME_DESC + CCA_NAME_DESC_TRACK;
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_TRACK).build();
        CcaEditCommand expectedCommand = new CcaEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TAG_DESC_SPORTS + INVALID_CCA_NAME_DESC + TAG_DESC_USP
                + CCA_NAME_DESC_TRACK;
        descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_TRACK)
                .withTags(VALID_TAG_SPORTS, VALID_TAG_USP).build();
        expectedCommand = new CcaEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_CCA;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withTags().build();
        CcaEditCommand expectedCommand = new CcaEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
