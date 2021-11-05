package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.CcaBuilder;

public class CcaContainsPersonPredicateTest {

    @Test
    public void equals() {
        List<Person> firstPredicatePersonList = Collections.singletonList(ALICE);
        List<Person> secondPredicatePersonList = Arrays.asList(ALICE, BOB);

        CcaContainsPersonPredicate firstPredicate = new CcaContainsPersonPredicate(firstPredicatePersonList);
        CcaContainsPersonPredicate secondPredicate = new CcaContainsPersonPredicate(secondPredicatePersonList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CcaContainsPersonPredicate firstPredicateCopy = new CcaContainsPersonPredicate(firstPredicatePersonList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ccaContainsPerson_returnsTrue() {
        Cca cca = new CcaBuilder().withName("NUSSO").build();
        cca.enrolPerson(ALICE);

        // One person
        CcaContainsPersonPredicate predicate = new CcaContainsPersonPredicate(Collections.singletonList(ALICE));
        assertTrue(predicate.test(cca));

        // Only one matching person
        predicate = new CcaContainsPersonPredicate(Arrays.asList(ALICE, BOB));
        assertTrue(predicate.test(cca));

        // Multiple persons
        cca.enrolPerson(BOB);
        predicate = new CcaContainsPersonPredicate(Arrays.asList(ALICE, BOB));
        assertTrue(predicate.test(cca));
    }

    @Test
    public void test_ccaDoesNotContainPersons_returnsFalse() {
        Cca cca = new CcaBuilder().withName("NUSSO").build();
        cca.enrolPerson(ALICE);

        // Zero persons
        CcaContainsPersonPredicate predicate = new CcaContainsPersonPredicate(Collections.emptyList());
        assertFalse(predicate.test(cca));

        // Non-matching person
        predicate = new CcaContainsPersonPredicate(Arrays.asList(BOB));
        assertFalse(predicate.test(cca));

        // Non-matching persons
        predicate = new CcaContainsPersonPredicate(Arrays.asList(BOB, CARL));
        assertFalse(predicate.test(cca));
    }
}
