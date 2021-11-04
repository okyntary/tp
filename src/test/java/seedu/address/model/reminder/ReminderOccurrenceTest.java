package seedu.address.model.reminder;

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
}
