package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code CcaName} matches any of the keywords given.
 * To be used by findCcaCommand, but not implemented yet
 */
public class CcaContainsPersonPredicate implements Predicate<Cca> {
    private final List<Person> persons;

    public CcaContainsPersonPredicate(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean test(Cca cca) {
        requireNonNull(cca);
        return persons.stream()
                .anyMatch(person -> cca.containsEnrolledPerson(person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaContainsPersonPredicate // instanceof handles nulls
                && persons.equals(((CcaContainsPersonPredicate) other).persons)); // state check
    }
}
