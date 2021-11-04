package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ReminderStartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderStartDate(null));
    }

    @Test
    public void constructor_invalidReminderStartDate_throwsIllegalArgumentException() {
        SimpleDateFormat invalidReminderStartDate = new SimpleDateFormat("dd/MM/yyyy");
        assertThrows(java.text.ParseException.class, () -> new ReminderStartDate(invalidReminderStartDate.parse("")));
    }

    @Test
    public void isValidReminderStartDate() {
        // null name
        assertThrows(NullPointerException.class, () -> ReminderStartDate.isValidDate(null));

        // invalid name
        assertFalse(ReminderStartDate.isValidDate("")); // empty string
        assertFalse(ReminderStartDate.isValidDate(" ")); // spaces only
        assertFalse(ReminderStartDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(ReminderStartDate.isValidDate("nusso*")); // contains non-alphanumeric characters
        assertFalse(ReminderStartDate.isValidDate("2020-2-")); // incomplete date
        assertFalse(ReminderStartDate.isValidDate("20202-12-32")); // year does not exist

        // valid name
        assertTrue(ReminderStartDate.isValidDate("2020-1-1")); // in the past
        assertTrue(ReminderStartDate.isValidDate("2023-12-15")); // in the future
    }

    // null, invalid string, year too big, wrong, correct
    @Test
    public void validate_nullDateString_throwsNullPointerException() {
        ReminderStartDate reminderStartDate = new ReminderStartDate(new Date());
        assertThrows(NullPointerException.class, () -> reminderStartDate.validate(null));
    }

    @Test
    public void validate_invalidDateStringFormat_throwsParseException() {
        ReminderStartDate reminderStartDate = new ReminderStartDate(new Date());
        assertThrows(ParseException.class, () -> reminderStartDate.validate(""));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("  "));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2020-02-nusso"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2020-02"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2020-02-"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2020-02-0"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("20202-12-32"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2021-02-29"));
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2021-04-31"));
    }

    @Test
    public void validate_yearInDateStringTooBig_throwsParseException() {
        ReminderStartDate reminderStartDate = new ReminderStartDate(new Date());
        assertThrows(ParseException.class, () -> reminderStartDate.validate("3000-02-02"));
    }

    @Test
    public void validate_dateStringWrong_throwsParseException() {
        ReminderStartDate reminderStartDate = new ReminderStartDate(new Date());
        assertThrows(ParseException.class, () -> reminderStartDate.validate("2020-10-31"));
    }

    @Test
    public void validate_dateStringCorrect_nothingThrown() {
        Date date = new Date();
        ReminderStartDate reminderStartDate = new ReminderStartDate(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        assertDoesNotThrow(() -> reminderStartDate.validate(year + "-" + month + "-" + day));
    }
}
