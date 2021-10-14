package seedu.address.model.cca;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;


public class Cca {

    // Identity fields
    private final CcaName ccaName;
    private Set<Person> personArrayList;
    private Cid cid = new Cid("0");

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName ccaName) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = new HashSet<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName ccaName, Cid cid) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = new HashSet<>();
        this.cid = cid;
    }

    /**
     * Returns the name of this CCA.
     * @return the name of this CCA
     */
    public CcaName getName() {
        return ccaName;
    }

    /**
     * Returns the cid of this CCA.
     * @return the cid of this CCA
     */
    public int getCid() {
        return Integer.parseInt(this.cid.value);
    }

    /**
     * Sets the cid of this CCA.
     * @param cid of this cca
     */
    public void setCid(int cid) {
        this.cid = new Cid(String.valueOf(cid));
    }

    /**
     * Returns true if both CCAs have the same ccaName.
     * This defines a weaker notion of equality between two CCAs.
     */
    public boolean isSameCca(seedu.address.model.cca.Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
                && otherCca.getName().equals(getName());
    }

    /**
     * Returns true if both CCAs have the same identity and data fields.
     * This defines a stronger notion of equality between two CCAs.
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

    // Check if Person Exists but should not need as it is a Set<>
    public boolean checkPerson(Person personToCheck) {
        return this.personArrayList.contains(personToCheck);
    }

    // Expel a Person
    public boolean expelPerson(Person personToExpel) {
        return this.personArrayList.remove(personToExpel);
    }
}

