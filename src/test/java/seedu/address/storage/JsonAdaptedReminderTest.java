package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.MEETING;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.util.Frequency;

class JsonAdaptedReminderTest {
    private static final String INVALID_REMINDERNAME = " ";

    private static final String VALID_REMINDERNAME = MEETING.getName().toString();
    private static final Date VALID_STARTDATE = MEETING.getStartDate().getDate();
    private static final Frequency VALID_FREQUENCY = MEETING.getFrequency().getTimePeriod();
    private static final int VALID_NUMTIMEPERIOD = MEETING.getFrequency().getNumTimePeriod();
    private static final int VALID_OCCURRENCE = MEETING.getOccurrences().getOccurrences();
    private static final String VALID_CCANAME = MEETING.getCcaName();
    private static final ArrayList<Date> VALID_DATES = new ArrayList<>();

    @Test
    void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder meeting = new JsonAdaptedReminder(MEETING);
        assertEquals(MEETING, meeting.toModelType());
    }

    @Test
    public void toModelType_invalidReminderName_throwsIllegalValueException() {
        JsonAdaptedReminder invalidReminder =
                new JsonAdaptedReminder(INVALID_REMINDERNAME, VALID_STARTDATE, VALID_FREQUENCY, VALID_NUMTIMEPERIOD,
                        VALID_OCCURRENCE, VALID_CCANAME, VALID_DATES);
        String expectedMessage = ReminderName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, invalidReminder::toModelType);
    }
}
