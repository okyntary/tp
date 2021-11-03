package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.cca.Cca;

/**
 * Tests that a {@code Person} is enroled in any cca contained in {@code ccaList}
 */
public class PersonIsInCcaPredicate implements Predicate<Person> {
    private final List<Cca> ccaList;

    public PersonIsInCcaPredicate(List<Cca> ccaList) {
        this.ccaList = ccaList;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return ccaList.parallelStream().anyMatch(cca -> cca.containsEnrolledPerson(person));
    }
}
