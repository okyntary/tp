package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.PersonBuilder;

public class PersonAddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new PersonAddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(PersonAddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        PersonAddCommand personAddCommand = new PersonAddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
            PersonAddCommand.MESSAGE_DUPLICATE_PERSON, () -> personAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        PersonAddCommand addAliceCommand = new PersonAddCommand(alice);
        PersonAddCommand addBobCommand = new PersonAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        PersonAddCommand addAliceCommandCopy = new PersonAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCca(Cca person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNumberOfPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCca(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCca(Cca target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCca(Cca target, Cca editedCca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Cca> getFilteredCcaList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNumberOfCcas() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            return;
        }

        /**
         * Enrols a person into a CCA
         *
         *  @param ccaToEnrolInto CCA to enrol into
         * @param personToEnrol person enrolling into CCA
         */
        @Override
        public boolean enrolPersonIntoCca(Cca ccaToEnrolInto, Person personToEnrol) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Expels a person from a CCA
         *
         * @param ccaToExpelFrom CCA to expel from
         * @param personToExpel Person to be expelled from CCA
         */
        @Override
        public boolean expelPersonFromCca(Cca ccaToExpelFrom, Person personToExpel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNumberOfReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> reminderPredicate) {
            return;
        }

        @Override
        public void resetFiltersForPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFiltersForCcaList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFiltersForReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetAllFilteredLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean addReminder(Reminder reminder, Cca ccaToAddInto) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void snoozeReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        public void setReminder(Reminder target, Reminder editedReminder) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public int getNumberOfPersons() {
            return 1;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // do nothing
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
