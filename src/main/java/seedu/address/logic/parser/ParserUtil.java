package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INDEX_EXCEEDS_MAXIMUM_SIZE;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IndexExceedsCapacityException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Frequency;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     * @throws IndexExceedsCapacityException if the specified index is exceeds ePoch's capacity of 999999999.
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException, IndexExceedsCapacityException {
        String trimmedIndex = oneBasedIndex.trim();
        if (trimmedIndex.length() > 9 && Pattern.compile("\\d+").matcher(trimmedIndex).matches()) {
            throw new IndexExceedsCapacityException(MESSAGE_INDEX_EXCEEDS_MAXIMUM_SIZE);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    // we be kinda repeating code here

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CcaName parseCcaName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new CcaName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code ReminderName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ReminderName parseReminderName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ReminderName(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code ReminderStartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static ReminderStartDate parseReminderStartDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDateText = date.trim();
        if (!ReminderStartDate.isValidDate(trimmedDateText)) {
            throw new ParseException(ReminderStartDate.MESSAGE_CONSTRAINTS);
        }
        Date startDate;
        try {
            startDate = ReminderStartDate.PARSE_INPUT_DATE_FORMAT.parse(trimmedDateText);
        } catch (java.text.ParseException e) {
            throw new ParseException(ReminderStartDate.PARSE_DATE_CONSTRAINTS);
        }
        ReminderStartDate reminderStartDate = new ReminderStartDate(startDate);
        reminderStartDate.validate(trimmedDateText);
        return reminderStartDate;
    }

    /**
     * Parses a {@code String frequency} into a {@code ReminderFrequency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code frequency} is invalid.
     */
    public static ReminderFrequency parseReminderFrequency(String frequency) throws ParseException {
        requireNonNull(frequency);
        String trimmedFrequency = frequency.trim();
        boolean empty = false;
        if (trimmedFrequency.length() == 0) {
            trimmedFrequency = "1d"; // to pass the check
            empty = true;
        }
        if (!ReminderFrequency.isValidFrequency(trimmedFrequency)) {
            throw new ParseException(ReminderFrequency.MESSAGE_CONSTRAINTS);
        }
        if (empty) {
            trimmedFrequency = "1o";
        }
        String timePeriodString = trimmedFrequency.substring(trimmedFrequency.length() - 1);
        String numTimePeriodString = trimmedFrequency.substring(0, trimmedFrequency.length() - 1);
        int numTimePeriod = 0;
        try {
            numTimePeriod = Integer.parseInt(numTimePeriodString);
        } catch (NumberFormatException e) {
            throw new ParseException(ReminderFrequency.MESSAGE_CONSTRAINTS);
        }
        if (numTimePeriod <= 0 || numTimePeriod > 100) {
            throw new ParseException(ReminderFrequency.MESSAGE_CONSTRAINTS);
        }
        Frequency timePeriod = Frequency.getFrequency(timePeriodString);
        return new ReminderFrequency(timePeriod, numTimePeriod);
    }

    /**
     * Parses an {@code String occurrence} into a {@code ReminderOccurrence}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occurrence} is invalid.
     */
    public static ReminderOccurrence parseReminderOccurrence(String occurrence) throws ParseException {
        requireNonNull(occurrence);
        String trimmedOccurrence = occurrence.trim();
        if (trimmedOccurrence.length() == 0) {
            trimmedOccurrence = "1";
        }
        int numOccurrences = 0;
        try {
            numOccurrences = Integer.parseInt(trimmedOccurrence);
        } catch (NumberFormatException e) {
            throw new ParseException(ReminderOccurrence.MESSAGE_CONSTRAINTS);
        }
        if (numOccurrences <= 0 || numOccurrences > 50) {
            throw new ParseException(ReminderOccurrence.MESSAGE_CONSTRAINTS);
        }
        return new ReminderOccurrence(numOccurrences);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
