package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Represents the in-memory model of ePoch data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Cca> filteredCcas;
    private final FilteredList<Reminder> filteredReminders;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredCcas = new FilteredList<>(this.addressBook.getCcaList());
        filteredReminders = new FilteredList<>(this.addressBook.getReminderList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);
        return addressBook.hasCca(cca);
    }

    @Override
    public void deleteCca(Cca target) {
        addressBook.removeCca(target);
    }

    @Override
    public void addCca(Cca cca) {
        addressBook.addCca(cca);
        updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
    }

    @Override
    public void setCca(Cca target, Cca editedCca) {
        requireAllNonNull(target, editedCca);
        addressBook.setCca(target, editedCca);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return addressBook.hasReminder(reminder);
    }

    @Override
    public void deleteReminder(Reminder target) {
        addressBook.removeReminder(target);
    }

    @Override
    public boolean addReminder(Reminder reminder, Cca ccaToAddInto) {
        requireAllNonNull(reminder, ccaToAddInto);
        addressBook.addReminder(reminder);
        boolean success = ccaToAddInto.addReminder(reminder);
        if (success) {
            updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        }
        logger.log(Level.INFO, "About to add reminder to address book");
        return success;
    }

    @Override
    public void snoozeReminder(Reminder target) {
        requireNonNull(target);
        addressBook.snoozeReminder(target);
    }

    @Override
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);
        addressBook.setReminder(target, editedReminder);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Cca List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Cca} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        return filteredCcas;
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        requireNonNull(predicate);
        filteredCcas.setPredicate(predicate);
    }

    /**
     * Enrols a person into a CCA
     * {@code cca} must exist in ePoch.
     * @param ccaToEnrolInto CCA to enrol into
     * @param personToEnrol Person enrolling into CCA
     */
    @Override
    public boolean enrolPersonIntoCca(Cca ccaToEnrolInto, Person personToEnrol) {
        requireAllNonNull(ccaToEnrolInto, personToEnrol);
        return ccaToEnrolInto.enrolPerson(personToEnrol);
    }

    /**
     * Expels a person from a CCA
     */
    @Override
    public boolean expelPersonFromCca(Cca ccaToExpelFrom, Person personToExpel) {
        requireAllNonNull(ccaToExpelFrom, personToExpel);
        return ccaToExpelFrom.expelPerson(personToExpel);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredCcas.equals(other.filteredCcas);
    }

    //=========== Filtered Reminder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reminder} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }

    //=========== Getters for number of entries =============================================================

    @Override
    public int getNumberOfPersons() {
        return getFilteredPersonList().size();
    }

    @Override
    public int getNumberOfCcas() {
        return getFilteredCcaList().size();
    }

    @Override
    public int getNumberOfReminders() {
        return getFilteredReminderList().size();
    }

    //=========== Filtered List Reset Functions =============================================================

    @Override
    public void resetFiltersForPersonList() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void resetFiltersForCcaList() {
        updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
    }

    @Override
    public void resetFiltersForReminderList() {
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
    }

    @Override
    public void resetAllFilteredLists() {
        this.resetFiltersForPersonList();
        this.resetFiltersForCcaList();
        this.resetFiltersForReminderList();
    }
}
