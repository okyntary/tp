package seedu.address.logic.commands.cca;

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
import seedu.address.testutil.CcaBuilder;

public class CcaAddCommandTest {

    @Test
    public void constructor_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CcaAddCommand(null));
    }

    @Test
    public void execute_ccaAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();

        CommandResult commandResult = new CcaAddCommand(validCca).execute(modelStub);

        assertEquals(String.format(CcaAddCommand.MESSAGE_SUCCESS, validCca), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCca), modelStub.ccasAdded);
    }

    @Test
    public void execute_duplicateCca_throwsCommandException() {
        Cca validCca = new CcaBuilder().build();
        CcaAddCommand ccaAddCommand = new CcaAddCommand(validCca);
        ModelStub modelStub = new ModelStubWithCca(validCca);

        assertThrows(CommandException.class,
                CcaAddCommand.MESSAGE_DUPLICATE_CCA, () -> ccaAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Cca alice = new CcaBuilder().withName("Alice").build();
        Cca bob = new CcaBuilder().withName("Bob").build();
        CcaAddCommand addAliceCommand = new CcaAddCommand(alice);
        CcaAddCommand addBobCommand = new CcaAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CcaAddCommand addAliceCommandCopy = new CcaAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different cca -> returns false
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
        public void addCca(Cca cca) {
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
            return;
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
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Enrols a cca into a CCA
         *
         *  @param ccaToEnrolInto CCA to enrol into
         * @param personToEnrol cca enrolling into CCA
         */
        @Override
        public boolean enrolPersonIntoCca(Cca ccaToEnrolInto, Person personToEnrol) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Expels a cca from a CCA
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
     * A Model stub that contains a single cca.
     */
    private class ModelStubWithCca extends ModelStub {
        private final Cca cca;

        ModelStubWithCca(Cca cca) {
            requireNonNull(cca);
            this.cca = cca;
        }

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return this.cca.isSameCca(cca);
        }
    }

    /**
     * A Model stub that always accept the cca being added.
     */
    private class ModelStubAcceptingCcaAdded extends ModelStub {
        final ArrayList<Cca> ccasAdded = new ArrayList<>();

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return ccasAdded.stream().anyMatch(cca::isSameCca);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccasAdded.add(cca);
        }

        @Override
        public int getNumberOfCcas() {
            return 1;
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            // do nothing
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
