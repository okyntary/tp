package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_NUSSO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_TIME_PERIOD_10;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURENCES_5;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_PERIOD_YEAR;
import static seedu.address.testutil.TypicalReminders.CLASS;
import static seedu.address.testutil.TypicalReminders.MEETING;
import static seedu.address.testutil.TypicalReminders.getDateFromString;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;

public class ReminderTest {

    @Test
    public void isSameReminder() {
        // same object -> returns true
        assertTrue(CLASS.isSameReminder(CLASS));

        // null -> returns false
        assertFalse(CLASS.isSameReminder(null));

        // all attributes same -> returns true
        Reminder editedClass = new ReminderBuilder(CLASS).build();
        assertTrue(CLASS.isSameReminder(editedClass));

        // same name, same start date, all other attributes different -> returns true
        editedClass = new ReminderBuilder(CLASS).withFrequency(VALID_TIME_PERIOD_YEAR, VALID_NUM_TIME_PERIOD_10)
                .withOccurrence(VALID_OCCURENCES_5).withCcaName("LCC").build();
        assertTrue(CLASS.isSameReminder(editedClass));

        // same name, different start date, all other attributes same -> returns false
        editedClass = new ReminderBuilder(CLASS).withStartDate(getDateFromString(VALID_START_DATE_MEETING)).build();
        assertFalse(CLASS.isSameReminder(editedClass));

        // different name, same start date, all other attributes same -> returns false
        editedClass = new ReminderBuilder(CLASS).withName("Clash").build();
        assertFalse(CLASS.isSameReminder(editedClass));

        // name differs in case, all other attributes same -> returns false
        Reminder editedMeeting = new ReminderBuilder(MEETING).withName(VALID_REMINDER_NAME_MEETING.toLowerCase())
                .build();
        assertFalse(MEETING.isSameReminder(editedMeeting));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_REMINDER_NAME_MEETING + " ";
        editedMeeting = new ReminderBuilder(MEETING).withName(nameWithTrailingSpaces).build();
        assertFalse(MEETING.isSameReminder(editedMeeting));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder classCopy = new ReminderBuilder(CLASS).build();
        assertTrue(CLASS.equals(classCopy));

        // same object -> returns true
        assertTrue(CLASS.equals(CLASS));

        // null -> returns false
        assertFalse(CLASS.equals(null));

        // different type -> returns false
        assertFalse(CLASS.equals(5));

        // different reminder -> returns false
        assertFalse(CLASS.equals(MEETING));

        // different name -> returns false
        Reminder editedClass = new ReminderBuilder(CLASS).withName(VALID_REMINDER_NAME_MEETING).build();
        assertFalse(CLASS.equals(editedClass));

        // different start date -> returns false
        editedClass = new ReminderBuilder(CLASS).withStartDate(getDateFromString(VALID_START_DATE_MEETING)).build();
        assertFalse(CLASS.equals(editedClass));

        // different frequency -> returns false
        editedClass = new ReminderBuilder(CLASS).withFrequency(VALID_TIME_PERIOD_YEAR, VALID_NUM_TIME_PERIOD_10)
                .build();
        assertFalse(CLASS.equals(editedClass));

        // different occurrence -> returns false
        editedClass = new ReminderBuilder(CLASS).withOccurrence(VALID_OCCURENCES_5).build();
        assertFalse(CLASS.equals(editedClass));

        // different cca name -> returns false
        editedClass = new ReminderBuilder(CLASS).withCcaName(VALID_CCA_NAME_NUSSO).build();
        assertFalse(CLASS.equals(editedClass));
    }
}
