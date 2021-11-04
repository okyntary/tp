package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    public static final int MAXIMUM_CAPACITY_PERSONS = 999999999;
    public static final int MAXIMUM_CAPACITY_CCAS = 999999999;
    public static final int MAXIMUM_CAPACITY_REMINDERS = 999999999;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Cca> PREDICATE_SHOW_ALL_CCAS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Person> PREDICATE_SHOW_NO_PERSONS = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in ePoch.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in ePoch.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in ePoch.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in ePoch.
     * The person identity of {@code editedPerson} must not be the same as another existing person in ePoch.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns true if a CCA with the same identity as {@code CCA} exists in ePoch.
     */
    boolean hasCca(Cca cca);

    /**
     * Deletes the given CCA.
     * The CCA must exist in ePoch.
     */
    void deleteCca(Cca target);

    /**
     * Adds the given CCA.
     * {@code cca} must not already exist in ePoch.
     */
    void addCca(Cca cca);

    /**
     * Replaces the given CCA {@code target} with {@code editedCca}.
     * {@code target} must exist in ePoch.
     * The CCA identity of {@code editedCca} must not be the same as another existing CCA in ePoch.
     */
    void setCca(Cca target, Cca editedCca);

    /** Returns an unmodifiable view of the filtered CCA list */
    ObservableList<Cca> getFilteredCcaList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered CCA list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCcaList(Predicate<Cca> predicate);

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

    /**
     * Returns the number of persons in ePoch.
     * @return the number of persons in ePoch.
     */
    int getNumberOfPersons();

    /**
     * Returns the number of CCAs in ePoch.
     * @return the number of CCAs in ePoch.
     */
    int getNumberOfCcas();

    /**
     * Returns the number of reminders in ePoch.
     * @return the number of reminders in ePoch.
     */
    int getNumberOfReminders();

    /**
     * Resets person list by removing all filters.
     */
    void resetFiltersForPersonList();

    /**
     * Resets CCA list by removing all filters.
     */
    void resetFiltersForCcaList();

    /**
     * Resets reminder list by removing all filters.
     */
    void resetFiltersForReminderList();

    /**
     * Resets the CCA, Person, and Reminder lists by removing all filters.
     */
    void resetAllFilteredLists();

    /**
     * Enrols a person into a CCA
     */
    boolean enrolPersonIntoCca(Cca ccaToEnrolInto, Person personToEnrol);

    /**
     * Expels a person from a CCA
     */
    boolean expelPersonFromCca(Cca ccaToExpelFrom, Person personToExpel);

    /** Returns an unmodifiable view of the filtered CCA list */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in ePoch.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Deletes the given reminder.
     * The reminder must exist in ePoch.
     */
    void deleteReminder(Reminder target);

    /**
     * Snoozes the given reminder.
     * The reminder must exist n ePoch.
     */
    void snoozeReminder(Reminder target);

    /**
     * Replaces the given Reminder {@code target} with {@code editedReminder}.
     * {@code target} must exist in ePoch.
     * The Reminder identity of {@code editedReminder} must not be the same as another existing
     * Reminder in ePoch.
     */
    void setReminder(Reminder target, Reminder editedReminder);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in ePoch.
     */
    boolean addReminder(Reminder reminder, Cca ccaToAddInto);
}
