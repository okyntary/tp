package seedu.address.logic.parser.cca;

import seedu.address.logic.commands.cca.CcaEnrolCommand;
import seedu.address.logic.commands.cca.CcaExpelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;

public class CcaExpelCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the CcaEnrolCommand
     * and returns an CcaEnrolCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CcaExpelCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CCA_ID, PREFIX_PERSON_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_CCA_ID, PREFIX_PERSON_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaExpelCommand.MESSAGE_USAGE));
        }

        int cid = ParserUtil.parseCid(argMultimap.getValue(PREFIX_CCA_ID).get());
        int pid = ParserUtil.parsePid(argMultimap.getValue(PREFIX_PERSON_ID).get());
        return new CcaExpelCommand(cid, pid);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
