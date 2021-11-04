package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.Frequency;

public class ReminderFrequencyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderFrequency(null, 0));
    }

    @Test
    public void constructor_invalidReminderFrequency_throwsIllegalArgumentException() {
        Frequency validReminderFrequencyPrefix = Frequency.WEEK;
        int invalidReminderFrequencySuffix = 0;
        assertThrows(AssertionError.class, () -> new ReminderFrequency(validReminderFrequencyPrefix,
            invalidReminderFrequencySuffix));
    }

    @Test
    public void isValidFrequency() {
        // null email
        assertThrows(NullPointerException.class, () -> ReminderFrequency.isValidFrequency(null));

        // blank email
        assertFalse(ReminderFrequency.isValidFrequency("")); // empty string
        assertFalse(ReminderFrequency.isValidFrequency(" ")); // spaces only

        // missing parts
        assertFalse(ReminderFrequency.isValidFrequency("d")); // missing period prefix
        assertFalse(ReminderFrequency.isValidFrequency("w")); // missing period prefix
        assertFalse(ReminderFrequency.isValidFrequency("m")); // missing period prefix
        assertFalse(ReminderFrequency.isValidFrequency("y")); // missing period prefix
        assertFalse(ReminderFrequency.isValidFrequency("1")); // missing period suffix
        assertFalse(ReminderFrequency.isValidFrequency("12")); // missing period suffix
        assertFalse(ReminderFrequency.isValidFrequency("13")); // missing period suffix
        assertFalse(ReminderFrequency.isValidFrequency("567")); // missing period suffix

        // invalid parts
        assertFalse(ReminderFrequency.isValidFrequency("3mm")); // invalid suffix
        assertFalse(ReminderFrequency.isValidFrequency("3x")); // invalid suffix
        assertFalse(ReminderFrequency.isValidFrequency("_3d")); // underscore in front
        assertFalse(ReminderFrequency.isValidFrequency("3d1")); // invalid format

        // valid frequency
        assertTrue(ReminderFrequency.isValidFrequency("4d"));
        assertTrue(ReminderFrequency.isValidFrequency("3w"));
        assertTrue(ReminderFrequency.isValidFrequency("5m"));
        assertTrue(ReminderFrequency.isValidFrequency("6y"));
        assertTrue(ReminderFrequency.isValidFrequency("10y"));
    }
}
