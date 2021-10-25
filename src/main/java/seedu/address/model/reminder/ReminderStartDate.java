package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderStartDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be entered in YYYY-MM-DD format, e.g. 2021-5-23";
    public static final String PARSE_DATE_CONSTRAINTS = "This date could not be parsed. Is it a valid date?";

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
