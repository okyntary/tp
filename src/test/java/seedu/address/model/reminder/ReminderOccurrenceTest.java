package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderOccurrenceTest {

    @Test
    public void constructor_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new ReminderOccurrence(0));
    }

    @Test
    public void constructor_invalidReminderOccurrence_throwsIllegalArgumentException() {
        int invalidReminderOccurrence = 0;
        assertThrows(AssertionError.class, () -> new ReminderOccurrence(invalidReminderOccurrence));
    }

    @Test
    public void equals() {
        ReminderOccurrence reminderOccurrence1 = new ReminderOccurrence(1);
        ReminderOccurrence reminderOccurrence2 = new ReminderOccurrence(2);
        ReminderOccurrence reminderOccurrence1Copy = new ReminderOccurrence(1);

        // same object -> returns true
        assertTrue(reminderOccurrence1.equals(reminderOccurrence1));

        // same values -> returns true
        assertTrue(reminderOccurrence1.equals(reminderOccurrence1Copy));

        // different types -> returns false
        assertFalse(reminderOccurrence1.equals(1));

        // null -> returns false
        assertFalse(reminderOccurrence1.equals(null));

        // different values -> returns false
        assertFalse(reminderOccurrence1.equals(reminderOccurrence2));
    }
}
