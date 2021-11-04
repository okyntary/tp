package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.CLASS;
import static seedu.address.testutil.TypicalReminders.MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;

public class UniqueReminderListTest {

    private final UniqueReminderList uniqueReminderList = new UniqueReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.contains(null));
    }

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(uniqueReminderList.contains(MEETING));
    }

    @Test
    public void contains_ccaInList_returnsTrue() {
        uniqueReminderList.add(MEETING);
        assertTrue(uniqueReminderList.contains(MEETING));
    }

    @Test
    public void contains_ccaWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReminderList.add(MEETING);
        Reminder editedNusso = new ReminderBuilder(MEETING).build();
        assertTrue(uniqueReminderList.contains(editedNusso));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateReminderException() {
        uniqueReminderList.add(MEETING);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.add(MEETING));
    }

    @Test
    public void setReminder_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(null, MEETING));
    }

    @Test
    public void setReminder_nullEditedReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(MEETING, null));
    }

    @Test
    public void setReminder_targetReminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.setReminder(MEETING, MEETING));
    }

    @Test
    public void setReminder_editedReminderIsSameReminder_success() {
        uniqueReminderList.add(MEETING);
        uniqueReminderList.setReminder(MEETING, MEETING);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(MEETING);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasSameIdentity_success() {
        uniqueReminderList.add(MEETING);
        Reminder editedNusso = new ReminderBuilder(MEETING).build();
        uniqueReminderList.setReminder(MEETING, editedNusso);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(editedNusso);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasDifferentIdentity_success() {
        uniqueReminderList.add(MEETING);
        uniqueReminderList.setReminder(MEETING, CLASS);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(CLASS);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasNonUniqueIdentity_throwsDuplicateReminderException() {
        uniqueReminderList.add(MEETING);
        uniqueReminderList.add(CLASS);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.setReminder(MEETING, CLASS));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.remove(null));
    }

    @Test
    public void remove_ccaDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.remove(MEETING));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        uniqueReminderList.add(MEETING);
        uniqueReminderList.remove(MEETING);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullUniqueReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((UniqueReminderList) null));
    }

    @Test
    public void setReminders_uniqueReminderList_replacesOwnListWithProvidedUniqueReminderList() {
        uniqueReminderList.add(MEETING);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(CLASS);
        uniqueReminderList.setReminders(expectedUniqueReminderList);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((List<Reminder>) null));
    }

    @Test
    public void setReminders_list_replacesOwnListWithProvidedList() {
        uniqueReminderList.add(MEETING);
        List<Reminder> ccaList = Collections.singletonList(CLASS);
        uniqueReminderList.setReminders(ccaList);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(CLASS);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_listWithDuplicateReminders_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(MEETING, MEETING);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList
            .setReminders(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueReminderList.asUnmodifiableObservableList().remove(0));
    }
}
