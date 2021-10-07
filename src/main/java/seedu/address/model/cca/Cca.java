package seedu.address.model.cca;

import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Cca {

    // Identity fields
    private final CcaName ccaName;
    private ArrayList<Person> personArrayList;

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName ccaName) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = new ArrayList<>();
    }

    public CcaName getName() {
        return ccaName;
    }

    /**
     * Returns true if both persons have the same ccaName.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCca(seedu.address.model.cca.Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
                && otherCca.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.cca.Cca)) {
            return false;
        }

        seedu.address.model.cca.Cca otherCca = (seedu.address.model.cca.Cca) other;
        return otherCca.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ccaName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        return builder.toString();
    }

    // Enrol a Person
    public boolean enrolPerson(Person newPerson) {
        return this.personArrayList.add(newPerson);
    }

    // Check if Person Exists
    public boolean checkPerson(Person personToCheck) {
        return this.personArrayList.contains(personToCheck);
    }

    // Expel a Person
    public boolean expelPerson(Person personToExpel) {
        return this.personArrayList.remove(personToExpel);
    }
}

