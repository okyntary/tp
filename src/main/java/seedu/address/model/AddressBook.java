package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.UniqueCcaList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueCcaList ccas;
    private final UniqueReminderList reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        ccas = new UniqueCcaList();
        reminders = new UniqueReminderList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the CCA list with {@code ccas}.
     * {@code ccas} must not contain duplicate CCAs.
     */
    public void setCcas(List<Cca> ccas) {
        this.ccas.setCcas(ccas);
    }

    /**
     * Replaces the contents of the reminder list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setCcas(newData.getCcaList());
        setReminders(newData.getReminderList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in ePoch.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to ePoch.
     * The person must not already exist in ePoch.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in ePoch.
     * The person identity of {@code editedPerson} must not be the same as another existing person in ePoch.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        ccas.setEnrolledPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in ePoch.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// cca-level operations

    /**
     * Returns true if a CCA with the same identity as {@code cca} exists in ePoch.
     */
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);
        return ccas.contains(cca);
    }

    /**
     * Adds a CCA to ePoch.
     * The CCA must not already exist in ePoch.
     */
    public void addCca(Cca cca) {
        ccas.add(cca);
    }

    /**
     * Replaces the given CCA {@code target} in the list with {@code editedCca}.
     * {@code target} must exist in ePoch.
     * The CCA identity of {@code editedCca} must not be the same as another existing CCA in ePoch.
     */
    public void setCca(Cca target, Cca editedCca) {
        requireNonNull(editedCca);
        ccas.setCca(target, editedCca);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in ePoch.
     */
    public void removeCca(Cca key) {
        ccas.remove(key);
    }

    //// reminder-level operations

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in ePoch.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a reminder to ePoch.
     * The reminder must not already exist in ePoch.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in ePoch.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    /**
     * Snoozes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in ePoch.
     */
    public void snoozeReminder(Reminder key) {
        if (key.isAtLastOccurrence()) {
            removeReminder(key);
        } else {
            setReminder(key, key.getSnoozedReminder());
        }
    }

    /**
     * Replaces the given Reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in ePoch.
     * The Reminder identity of {@code editedReminder} must not be the same as another existing
     * Reminder in ePoch.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireNonNull(editedReminder);
        reminders.setReminder(target, editedReminder);
    }

    //// util methods

    @Override
    public String toString() {
        String result = persons.asUnmodifiableObservableList().size() + " persons, "
                + ccas.asUnmodifiableObservableList().size() + " CCAs, "
                + reminders.asUnmodifiableObservableList().size() + " reminders";
        return result;
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Cca> getCcaList() {
        return ccas.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
