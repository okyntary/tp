package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.parser.exceptions.ParseException;

public class ReminderStartDate implements Comparable<ReminderStartDate> {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be entered in YYYY-MM-DD format, e.g. 2021-5-23";
    public static final String PARSE_DATE_CONSTRAINTS = "This date could not be parsed. Is it a valid date?";
    public static final String DATE_YEAR_TOO_LARGE_CONSTRAINTS = "It looks like you've made a typo - "
            + "this date is way too far in the future! The year should be less than 3000.";

    /*
     * The date must be in YYYY-MM-DD format.
     */
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{1,2}-\\d{1,2}";
    public static final SimpleDateFormat PARSE_INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat PARSE_DATE_TO_STRING_FORMAT = new SimpleDateFormat("E, dd MMM yyyy");

    public final Date startDate;

    /**
     * Constructs a {@code ReminderStartDate}.
     *
     * @param date A valid date in YYYY-MM-DD format.
     */
    public ReminderStartDate(Date date) {
        requireNonNull(date);
        startDate = date;
    }

    public Date getDate() {
        return this.startDate;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks that the parsed date matches with the given string representation.
     *
     * @param dateString The string representation of the date in yyyy-MM-dd format
     * @throws ParseException if the given {@code date} is invalid.
     */
    public void validate(String dateString) throws ParseException {
        int year;
        int month;
        int day;
        String[] fields = dateString.split("-");
        if (fields.length != 3) {
            throw new ParseException(ReminderStartDate.PARSE_DATE_CONSTRAINTS);
        }
        try {
            year = Integer.parseInt(fields[0]);
            month = Integer.parseInt(fields[1]);
            day = Integer.parseInt(fields[2]);
        } catch (NumberFormatException e) {
            throw new ParseException(ReminderStartDate.PARSE_DATE_CONSTRAINTS);
        }
        if (year >= 3000) {
            throw new ParseException(ReminderStartDate.DATE_YEAR_TOO_LARGE_CONSTRAINTS);
        }
        if (year != getYear() || month != getMonth() || day != startDate.getDate()) {
            throw new ParseException(ReminderStartDate.PARSE_DATE_CONSTRAINTS);
        }
    }

    /**
     * A getter for the year of the startDate.
     * This is implemented as the getYear() method of the Date object starts counting from 1900.
     * @return an integer representing the year of the startDate.
     */
    public int getYear() {
        int padding = 1900;
        return startDate.getYear() + padding;
    }

    /**
     * A getter for the month of the startDate.
     * This is implemented as the getMonth() method of the Date object is zero-indexed.
     * @return an integer representing the month of the startDate.
     */
    public int getMonth() {
        int padding = 1;
        return startDate.getMonth() + padding;
    }

    /**
     * Compares start dates.
     * @param otherStartDate The start date to be compared with
     * @return 1, 0 or -1 if the 1st date is earlier, on the same day, or later than the 2nd date respectively.
     */
    @Override
    public int compareTo(ReminderStartDate otherStartDate) {
        return this.startDate.compareTo(otherStartDate.startDate);
    }

    @Override
    public String toString() {
        return PARSE_DATE_TO_STRING_FORMAT.format(startDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderStartDate // instanceof handles nulls
                && startDate.toString().equals(((ReminderStartDate) other).startDate.toString())); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }
}
