package seedu.address.model.cca;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;


public class Cca {

    // Identity fields
    private final CcaName ccaName;
    // Data fields
    private Set<Person> personArrayList = new HashSet<>();
    private Set<Reminder> reminders = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName ccaName) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = new HashSet<>();
    }

    public Cca(CcaName ccaName, Set<Tag> tags) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = new HashSet<>();
        this.tags.addAll(tags);
    }

    public Cca(CcaName ccaName, Set<Person> personArrayList, Set<Reminder> reminders, Set<Tag> tags) {
        requireAllNonNull(ccaName);
        this.ccaName = ccaName;
        this.personArrayList = personArrayList;
        this.reminders = reminders;
        this.tags.addAll(tags);
    }

    /**
     * Returns the name of this CCA.
     * @return the name of this CCA
     */
    public CcaName getName() {
        return ccaName;
    }

    /**
     * Returns the personArrayList of this CCA.
     * @return the personArrayList of this CCA
     */
    public Set<Person> getPersonArrayList() {
        return personArrayList;
    }

    /**
     * Returns the number of people in this CCA.
     * @return the number of members of this CCA
     */
    public int getNumberOfMembers() {
        return personArrayList.size();
    }

    /**
<<<<<<< HEAD
     * Returns the reminders of this CCA.
     * @return the reminders of this CCA
     */
    public Set<Reminder> getReminders() {
        return reminders;
    }

    /**
     * Returns the number of reminders in this CCA.
     * @return the number of reminders of this CCA
     */
    public int getNumberOfReminders() {
        return reminders.size();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        return otherCca.getName().equals(this.getName())
                && otherCca.getPersonArrayList().equals(this.getPersonArrayList())
                && otherCca.getTags().equals(this.getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ccaName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Number of enrolled persons: ")
                .append(this.getPersonArrayList().size());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
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

    // Add a reminder
    public boolean addReminder(Reminder reminder) {
        return this.reminders.add(reminder);
    }

    // Remove a reminder
    public boolean removeReminder(Reminder reminder) {
        return this.reminders.remove(reminder);
    }
}
