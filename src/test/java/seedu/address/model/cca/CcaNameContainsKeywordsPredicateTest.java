package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CcaBuilder;

public class CcaNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CcaNameContainsKeywordsPredicate firstPredicate = new CcaNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        CcaNameContainsKeywordsPredicate secondPredicate = new CcaNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CcaNameContainsKeywordsPredicate firstPredicateCopy = new CcaNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CcaNameContainsKeywordsPredicate predicate = new CcaNameContainsKeywordsPredicate(
                Collections.singletonList("Nusso"));
        assertTrue(predicate.test(new CcaBuilder().withName("Nusso Cca").build()));

        // Multiple keywords
        predicate = new CcaNameContainsKeywordsPredicate(Arrays.asList("Nusso", "Cca"));
        assertTrue(predicate.test(new CcaBuilder().withName("Nusso Cca").build()));

        // Only one matching keyword
        predicate = new CcaNameContainsKeywordsPredicate(Arrays.asList("Cca", "Track"));
        assertTrue(predicate.test(new CcaBuilder().withName("Nusso Track").build()));

        // Mixed-case keywords
        predicate = new CcaNameContainsKeywordsPredicate(Arrays.asList("nUSso", "cCA"));
        assertTrue(predicate.test(new CcaBuilder().withName("Nusso Cca").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CcaNameContainsKeywordsPredicate predicate = new CcaNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CcaBuilder().withName("Nusso").build()));

        // Non-matching keyword
        predicate = new CcaNameContainsKeywordsPredicate(Arrays.asList("Track"));
        assertFalse(predicate.test(new CcaBuilder().withName("Nusso Cca").build()));

        // Keywords do not match name
        predicate = new CcaNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new CcaBuilder().withName("Nusso").build()));
    }
}
