package seedu.address.logic.parser.cca;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;

import java.util.stream.Stream;

import seedu.address.logic.commands.cca.CcaColourCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagColour;

/**
 * Parses input arguments and creates a new CcaColourCommand object
 */
public class CcaColourCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CcaColourCommand
     * and returns a CcaColourCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CcaColourCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COLOUR);

        if (!arePrefixesPresent(argMultimap, PREFIX_COLOUR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaColourCommand.MESSAGE_USAGE));
        }

        TagColour tagColour = ParserUtil.parseTagColour(argMultimap.getValue(PREFIX_COLOUR).get());

        return new CcaColourCommand(tagColour);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
