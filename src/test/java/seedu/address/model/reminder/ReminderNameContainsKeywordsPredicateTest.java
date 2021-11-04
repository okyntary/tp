package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;

public class ReminderNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ReminderNameContainsKeywordsPredicate firstPredicate = new ReminderNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        ReminderNameContainsKeywordsPredicate secondPredicate = new ReminderNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ReminderNameContainsKeywordsPredicate firstPredicateCopy = new ReminderNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ReminderNameContainsKeywordsPredicate predicate = new ReminderNameContainsKeywordsPredicate(
                Collections.singletonList("Meeting"));
        assertTrue(predicate.test(new ReminderBuilder().withName("Meeting Reminder").build()));

        // Multiple keywords
        predicate = new ReminderNameContainsKeywordsPredicate(Arrays.asList("Meeting", "Reminder"));
        assertTrue(predicate.test(new ReminderBuilder().withName("Meeting Reminder").build()));

        // Only one matching keyword
        predicate = new ReminderNameContainsKeywordsPredicate(Arrays.asList("Reminder", "Project"));
        assertTrue(predicate.test(new ReminderBuilder().withName("Meeting Project").build()));

        // Mixed-case keywords
        predicate = new ReminderNameContainsKeywordsPredicate(Arrays.asList("mEEtIng", "reMINDer"));
        assertTrue(predicate.test(new ReminderBuilder().withName("Meeting Reminder").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ReminderNameContainsKeywordsPredicate predicate = new ReminderNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ReminderBuilder().withName("Meeting").build()));

        // Non-matching keyword
        predicate = new ReminderNameContainsKeywordsPredicate(Arrays.asList("Project"));
        assertFalse(predicate.test(new ReminderBuilder().withName("Meeting Reminder").build()));

        // Keywords do not match name
        predicate = new ReminderNameContainsKeywordsPredicate(Arrays.asList("12345",
                "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ReminderBuilder().withName("Meeting").build()));
    }
}
