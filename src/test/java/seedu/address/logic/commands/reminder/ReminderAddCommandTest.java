package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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
import seedu.address.model.cca.UniqueCcaList;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.TypicalIndexes;

public class ReminderAddCommandTest {

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderAddCommand(null, null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = new ReminderAddCommand(validReminder, TypicalIndexes.INDEX_FIRST_CCA)
                .execute(modelStub);

        assertEquals(String.format(ReminderAddCommand.MESSAGE_SUCCESS, validReminder),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder validReminder = new ReminderBuilder().build();
        ReminderAddCommand reminderAddCommand = new ReminderAddCommand(validReminder, TypicalIndexes.INDEX_FIRST_CCA);
        ModelStub modelStub = new ModelStubWithReminder(validReminder);

        assertThrows(CommandException.class,
                ReminderAddCommand.MESSAGE_DUPLICATE_REMINDER, () -> reminderAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Reminder reminder1 = new ReminderBuilder().withName("reminder1").build();
        Reminder reminder2 = new ReminderBuilder().withName("reminder2").build();
        ReminderAddCommand addReminder1Command = new ReminderAddCommand(reminder1, TypicalIndexes.INDEX_FIRST_CCA);
        ReminderAddCommand addReminder2Command = new ReminderAddCommand(reminder2, TypicalIndexes.INDEX_FIRST_CCA);

        // same object -> returns true
        assertTrue(addReminder1Command.equals(addReminder1Command));

        // same values -> returns true
        ReminderAddCommand addReminder1CommandCopy = new ReminderAddCommand(reminder1, TypicalIndexes.INDEX_FIRST_CCA);
        assertTrue(addReminder1Command.equals(addReminder1CommandCopy));

        // different types -> returns false
        assertFalse(addReminder1Command.equals(1));

        // null -> returns false
        assertFalse(addReminder1Command.equals(null));

        // different person -> returns false
        assertFalse(addReminder1Command.equals(addReminder2Command));
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
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
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
     * A Model stub that contains a single reminder.
     */
    private class ModelStubWithReminder extends ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return this.reminder.isSameReminder(reminder);
        }

        @Override
        public ObservableList<Cca> getFilteredCcaList() {
            UniqueCcaList ccas = new UniqueCcaList();
            CcaBuilder ccaBuilder = new CcaBuilder();
            ccas.add(ccaBuilder.build());
            return ccas.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            // do nothing
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            // do nothing
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return remindersAdded.stream().anyMatch(reminder::isSameReminder);
        }

        @Override
        public boolean addReminder(Reminder reminder, Cca ccaToAddInto) {
            requireAllNonNull(reminder, ccaToAddInto);
            remindersAdded.add(reminder);
            return true;
        }

        @Override
        public int getNumberOfReminders() {
            return 1;
        }

        @Override
        public ObservableList<Cca> getFilteredCcaList() {
            UniqueCcaList ccas = new UniqueCcaList();
            CcaBuilder ccaBuilder = new CcaBuilder();
            ccas.add(ccaBuilder.build());
            return ccas.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // do nothing
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            // do nothing
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            // do nothing
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
