package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

public class ReminderStartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderStartDate(null));
    }

    @Test
    public void constructor_invalidReminderStartDate_throwsIllegalArgumentException() {
        SimpleDateFormat invalidReminderStartDate = new SimpleDateFormat("dd/MM/yyyy");
        assertThrows(ParseException.class, () -> new ReminderStartDate(invalidReminderStartDate.parse("")));
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
}
