package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;


public class ReminderContainsCcaNamePredicateTest {


    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ReminderContainsCcaNamePredicate firstPredicate = new ReminderContainsCcaNamePredicate(
                firstPredicateKeywordList);
        ReminderContainsCcaNamePredicate secondPredicate = new ReminderContainsCcaNamePredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ReminderContainsCcaNamePredicate firstPredicateCopy = new ReminderContainsCcaNamePredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different ReminderContainsCcaNamePredicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_reminderContainsCcaName_returnsTrue() {
        // One keyword
        ReminderContainsCcaNamePredicate predicate = new ReminderContainsCcaNamePredicate(
                Collections.singletonList("NUSSO"));
        Reminder reminder = new ReminderBuilder().withName("Meeting Reminder").build();
        reminder.setCcaName("NUSSO");
        assertTrue(predicate.test(reminder));

        // Multiple keywords
        predicate = new ReminderContainsCcaNamePredicate(Arrays.asList("NUSSO", "NUSSO"));
        assertTrue(predicate.test(reminder));

        // Only one matching keyword
        predicate = new ReminderContainsCcaNamePredicate(Arrays.asList("NUSSO", "USKick"));
        assertTrue(predicate.test(reminder));
    }

    @Test
    public void test_reminderDoesNotContainCcaName_returnsFalse() {
        // Zero keywords
        ReminderContainsCcaNamePredicate predicate = new ReminderContainsCcaNamePredicate(
                Collections.emptyList());
        Reminder reminder = new ReminderBuilder().withName("Meeting Reminder").build();
        reminder.setCcaName("NUSSO");
        assertFalse(predicate.test(reminder));

        // Non-matching keyword
        predicate = new ReminderContainsCcaNamePredicate(Arrays.asList("USKick"));
        assertFalse(predicate.test(reminder));

        // Keywords do not match name
        predicate = new ReminderContainsCcaNamePredicate(Arrays.asList("USKick", "nusso", "USC"));
        assertFalse(predicate.test(reminder));
    }
}
