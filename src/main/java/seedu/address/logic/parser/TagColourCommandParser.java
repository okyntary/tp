package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.TagColourCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagColour;

/**
 * Parses input arguments and creates a new TagColourCommand object
 */
public class TagColourCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the TagColourCommand
     * and returns a TagColourCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TagColourCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COLOUR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COLOUR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagColourCommand.MESSAGE_USAGE));
        }

        String tagName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
        TagColour tagColour = ParserUtil.parseTagColour(argMultimap.getValue(PREFIX_COLOUR).get());

        return new TagColourCommand(tagName, tagColour);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
