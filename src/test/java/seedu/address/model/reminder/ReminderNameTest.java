package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderName(null));
    }

    @Test
    public void constructor_invalidReminderName_throwsIllegalArgumentException() {
        String invalidReminderName = "";
        assertThrows(IllegalArgumentException.class, () -> new ReminderName(invalidReminderName));
    }

    @Test
    public void isValidReminderName() {
        // null name
        assertThrows(NullPointerException.class, () -> ReminderName.isValidName(null));

        // invalid name
        assertFalse(ReminderName.isValidName("")); // empty string
        assertFalse(ReminderName.isValidName(" ")); // spaces only
        assertFalse(ReminderName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ReminderName.isValidName("nusso*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ReminderName.isValidName("nus symphony orchestra")); // alphabets only
        assertTrue(ReminderName.isValidName("12345")); // numbers only
        assertTrue(ReminderName.isValidName("nusso the 2nd")); // alphanumeric characters
        assertTrue(ReminderName.isValidName("Capital NUSSO")); // with capital letters
        assertTrue(ReminderName.isValidName("NUS Symphony Orchestra NUS Symphony Orchestra")); // long names
    }
}
