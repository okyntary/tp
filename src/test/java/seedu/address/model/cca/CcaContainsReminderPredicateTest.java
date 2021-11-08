package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalReminders.CHRISTMAS;
import static seedu.address.testutil.TypicalReminders.CLASS;
import static seedu.address.testutil.TypicalReminders.CONCERT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.ReminderBuilder;

public class CcaContainsReminderPredicateTest {

    @Test
    public void equals() {
        List<Reminder> firstPredicateReminderList = Collections.singletonList(CHRISTMAS);
        List<Reminder> secondPredicateReminderList = Collections.singletonList(CONCERT);

        CcaContainsReminderPredicate firstPredicate = new CcaContainsReminderPredicate(firstPredicateReminderList);
        CcaContainsReminderPredicate secondPredicate = new CcaContainsReminderPredicate(secondPredicateReminderList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CcaContainsReminderPredicate firstPredicateCopy = new CcaContainsReminderPredicate(firstPredicateReminderList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ccaContainsReminder_returnsTrue() {
        Cca cca = new CcaBuilder().withName("NUSSO").build();
        Reminder editedChristmas = new ReminderBuilder(CHRISTMAS).withCcaName("NUSSO").build();
        cca.addReminder(CHRISTMAS);

        // One reminder
        CcaContainsReminderPredicate predicate = new CcaContainsReminderPredicate(Collections
                .singletonList(editedChristmas));
        assertTrue(predicate.test(cca));

        // Only one matching reminder
        predicate = new CcaContainsReminderPredicate(Arrays.asList(editedChristmas, CONCERT));
        assertTrue(predicate.test(cca));

        // Multiple reminder
        cca.addReminder(CONCERT);
        Reminder editedConcert = new ReminderBuilder(CONCERT).withCcaName("NUSSO").build();
        predicate = new CcaContainsReminderPredicate(Arrays.asList(editedChristmas, editedConcert));
        assertTrue(predicate.test(cca));
    }

    @Test
    public void test_ccaDoesNotContainReminders_returnsFalse() {
        Cca cca = new CcaBuilder().withName("NUSSO").build();

        // Zero reminders
        CcaContainsReminderPredicate predicate = new CcaContainsReminderPredicate(Collections.emptyList());
        assertFalse(predicate.test(cca));

        // Non-matching reminder
        predicate = new CcaContainsReminderPredicate(Arrays.asList(CONCERT));
        assertFalse(predicate.test(cca));

        // Non-matching reminders
        predicate = new CcaContainsReminderPredicate(Arrays.asList(CONCERT, CLASS));
        assertFalse(predicate.test(cca));
    }
}
