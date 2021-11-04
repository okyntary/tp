package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void equals() {
        ReminderFrequency frequency1 = new ReminderFrequency(Frequency.DAY, 5);
        ReminderFrequency frequency2 = new ReminderFrequency(Frequency.WEEK, 5);
        ReminderFrequency frequency3 = new ReminderFrequency(Frequency.DAY, 10);
        ReminderFrequency frequency1Copy = new ReminderFrequency(Frequency.DAY, 5);

        // same object -> returns true
        assertTrue(frequency1.equals(frequency1));

        // same values -> returns true
        assertTrue(frequency1.equals(frequency1Copy));

        // different types -> returns false
        assertFalse(frequency1.equals(1));

        // null -> returns false
        assertFalse(frequency1.equals(null));

        // different Frequency in ReminderFrequency -> returns false
        assertFalse(frequency1.equals(frequency2));

        // different int in ReminderFrequency -> returns false
        assertFalse(frequency1.equals(frequency3));
    }

    @Test
    public void isRecurring() {
        ReminderFrequency frequency1 = new ReminderFrequency(Frequency.MONTH, 1);
        ReminderFrequency frequency2 = new ReminderFrequency(Frequency.YEAR, 1);
        ReminderFrequency frequency3 = new ReminderFrequency(Frequency.ONE_OFF, 1);

        assertTrue(frequency1.isRecurring());
        assertTrue(frequency2.isRecurring());
        assertFalse(frequency3.isRecurring());
    }

    @Test
    public void checkToString() {
        ReminderFrequency frequency1 = new ReminderFrequency(Frequency.MONTH, 5);
        ReminderFrequency frequency2 = new ReminderFrequency(Frequency.YEAR, 10);
        ReminderFrequency frequency3 = new ReminderFrequency(Frequency.ONE_OFF, 1);

        assertEquals("Every 5 Month(s)", frequency1.toString());
        assertEquals("Every 10 Year(s)", frequency2.toString());
        assertEquals("Once only", frequency3.toString());
    }
}
