package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCcas.USC;
import static seedu.address.testutil.TypicalCcas.USCOFFEE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalReminders.CHRISTMAS;
import static seedu.address.testutil.TypicalReminders.CLASS;
import static seedu.address.testutil.TypicalReminders.CONCERT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cca.exceptions.CcaNotFoundException;
import seedu.address.model.cca.exceptions.DuplicateCcaException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    ///// tests for person

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void addPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addPerson(null));
    }

    @Test
    public void addPerson_personNotInAddressBook_addsPerson() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void addPerson_personInAddressBook_throwsDuplicatePersonException() {
        modelManager.addPerson(ALICE);
        assertThrows(DuplicatePersonException.class, () -> modelManager.addPerson(ALICE));
    }

    @Test
    public void deletePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePerson(null));
    }

    @Test
    public void deletePerson_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deletePerson(BENSON));
    }

    @Test
    public void deletePerson_personInAddressBook_deletesPerson() {
        modelManager.addPerson(BENSON);
        assertTrue(modelManager.hasPerson(BENSON));
        modelManager.deletePerson(BENSON);
        assertFalse(modelManager.hasPerson(BENSON));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.setPerson(ALICE, BENSON));
    }

    @Test
    public void setPerson_targetPersonAndEditedPersonInAddressBook_throwsDuplicatePersonException() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        assertThrows(DuplicatePersonException.class, () -> modelManager.setPerson(ALICE, BENSON));
    }

    @Test
    public void setPerson_targetPersonInAddressBookAndEditedPersonSameAsTargetPerson_setsPerson() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void setPerson_targetPersonInAddressBookAndEditedPersonDifferentFromTargetPerson_setsPerson() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, BENSON);
        assertTrue(modelManager.hasPerson(BENSON));
    }

    ///// tests for cca

    @Test
    public void hasCca_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCca(null));
    }

    @Test
    public void hasCca_ccaNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCca(USCOFFEE));
    }

    @Test
    public void hasCca_ccaInAddressBook_returnsTrue() {
        modelManager.addCca(USCOFFEE);
        assertTrue(modelManager.hasCca(USCOFFEE));
    }

    @Test
    public void addCca_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addCca(null));
    }

    @Test
    public void addCca_ccaNotInAddressBook_addsCca() {
        modelManager.addCca(USCOFFEE);
        assertTrue(modelManager.hasCca(USCOFFEE));
    }

    @Test
    public void addCca_ccaInAddressBook_throwsDuplicateCcaException() {
        modelManager.addCca(USCOFFEE);
        assertThrows(DuplicateCcaException.class, () -> modelManager.addCca(USCOFFEE));
    }

    @Test
    public void deleteCca_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteCca(null));
    }

    @Test
    public void deleteCca_ccaNotInAddressBook_throwsCcaNotFoundException() {
        assertThrows(CcaNotFoundException.class, () -> modelManager.deleteCca(USC));
    }

    @Test
    public void deleteCca_ccaInAddressBook_deletesCca() {
        modelManager.addCca(USC);
        assertTrue(modelManager.hasCca(USC));
        modelManager.deleteCca(USC);
        assertFalse(modelManager.hasCca(USC));
    }

    @Test
    public void setCca_nullTargetCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCca(null, USCOFFEE));
    }

    @Test
    public void setCca_nullEditedCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCca(USCOFFEE, null));
    }

    @Test
    public void setCca_targetCcaNotInAddressBook_throwsCcaNotFoundException() {
        assertThrows(CcaNotFoundException.class, () -> modelManager.setCca(USCOFFEE, USC));
    }

    @Test
    public void setCca_targetCcaAndEditedCcaInAddressBook_throwsDuplicateCcaException() {
        modelManager.addCca(USCOFFEE);
        modelManager.addCca(USC);
        assertThrows(DuplicateCcaException.class, () -> modelManager.setCca(USCOFFEE, USC));
    }

    @Test
    public void setCca_targetCcaInAddressBookAndEditedCcaSameAsTargetCca_setsCca() {
        modelManager.addCca(USCOFFEE);
        modelManager.setCca(USCOFFEE, USCOFFEE);
        assertTrue(modelManager.hasCca(USCOFFEE));
    }

    @Test
    public void setCca_targetCcaInAddressBookAndEditedCcaDifferentFromTargetCca_setsCca() {
        modelManager.addCca(USCOFFEE);
        modelManager.setCca(USCOFFEE, USC);
        assertTrue(modelManager.hasCca(USC));
    }

    @Test
    public void enrolPersonIntoCca_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.enrolPersonIntoCca(null, ALICE));
    }

    @Test
    public void enrolPersonIntoCca_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.enrolPersonIntoCca(USCOFFEE, null));
    }

    @Test
    public void enrolPersonIntoCca_personAlreadyEnrolled_enrolListStaysSame() {
        assertFalse(USCOFFEE.getMembers().contains(BENSON));
        modelManager.enrolPersonIntoCca(USCOFFEE, BENSON);
        int originalMembers = USCOFFEE.getMembers().size();
        modelManager.enrolPersonIntoCca(USCOFFEE, BENSON);
        int newMembers = USCOFFEE.getMembers().size();
        assertTrue(USCOFFEE.getMembers().contains(BENSON));
        assertEquals(originalMembers, newMembers);
    }

    @Test
    public void enrolPersonIntoCca_personNotAlreadyEnrolled_enrolsPerson() {
        assertFalse(USCOFFEE.getMembers().contains(ALICE));
        modelManager.enrolPersonIntoCca(USCOFFEE, ALICE);
        assertTrue(USCOFFEE.getMembers().contains(ALICE));
    }

    @Test
    public void expelPersonFromCca_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.expelPersonFromCca(null, ALICE));
    }

    @Test
    public void expelPersonFromCca_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.expelPersonFromCca(USCOFFEE, null));
    }

    @Test
    public void expelPersonFromCca_personNotEnrolled_enrolListStaysSame() {
        int originalMembers = USC.getMembers().size();
        modelManager.expelPersonFromCca(USC, ALICE);
        int newMembers = USC.getMembers().size();
        assertFalse(USC.getMembers().contains(ALICE));
        assertEquals(originalMembers, newMembers);
    }

    @Test
    public void expelPersonFromCca_personEnrolled_expelsPerson() {
        modelManager.enrolPersonIntoCca(USC, ALICE);
        assertTrue(USC.getMembers().contains(ALICE));
        modelManager.expelPersonFromCca(USC, ALICE);
        assertFalse(USC.getMembers().contains(ALICE));
    }

    ///// tests for reminder

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasReminder(CHRISTMAS));
    }

    @Test
    public void hasReminder_reminderInAddressBook_returnsTrue() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        assertTrue(modelManager.hasReminder(CHRISTMAS));
    }

    @Test
    public void addReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addReminder(null, USCOFFEE));
    }

    @Test
    public void addReminder_nullCcaToAddInto_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addReminder(CHRISTMAS, null));
    }

    @Test
    public void addReminder_reminderNotInAddressBook_addsReminder() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        assertTrue(modelManager.hasReminder(CHRISTMAS));
    }

    @Test
    public void addReminder_reminderInAddressBook_throwsDuplicateReminderException() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        assertThrows(DuplicateReminderException.class, () -> modelManager.addReminder(CHRISTMAS, USCOFFEE));
    }

    @Test
    public void deleteReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteReminder(null));
    }

    @Test
    public void deleteReminder_reminderNotInAddressBook_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> modelManager.deleteReminder(CHRISTMAS));
    }

    @Test
    public void deleteReminder_reminderInAddressBook_deletesReminder() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        assertTrue(modelManager.hasReminder(CHRISTMAS));
        modelManager.deleteReminder(CHRISTMAS);
        assertFalse(modelManager.hasReminder(CHRISTMAS));
    }

    @Test
    public void setReminder_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setReminder(null, CHRISTMAS));
    }

    @Test
    public void setReminder_nullEditedReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setReminder(CHRISTMAS, null));
    }

    @Test
    public void setReminder_targetReminderNotInAddressBook_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> modelManager.setReminder(CHRISTMAS, CLASS));
    }

    @Test
    public void setReminder_targetReminderAndEditedReminderInAddressBook_throwsDuplicateReminderException() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        modelManager.addReminder(CLASS, USCOFFEE);
        assertThrows(DuplicateReminderException.class, () -> modelManager.setReminder(CHRISTMAS, CLASS));
    }

    @Test
    public void setReminder_targetReminderInAddressBookAndEditedReminderSameAsTargetReminder_setsReminder() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        modelManager.setReminder(CHRISTMAS, CHRISTMAS);
        assertTrue(modelManager.hasReminder(CHRISTMAS));
    }

    @Test
    public void setReminder_targetReminderInAddressBookAndEditedReminderDifferentFromTargetReminder_setsReminder() {
        modelManager.addReminder(CHRISTMAS, USCOFFEE);
        modelManager.setReminder(CHRISTMAS, CLASS);
        assertTrue(modelManager.hasReminder(CLASS));
    }

    @Test
    public void snoozeReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.snoozeReminder(null));
    }

    @Test
    public void snoozeReminder_reminderNotInAddressBook_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> modelManager.snoozeReminder(CHRISTMAS));
    }

    @Test
    public void snoozeReminder_reminderInAddressBookAndLastOccurrence_deletesReminder() {
        modelManager.addReminder(CONCERT, USCOFFEE);
        assertTrue(modelManager.hasReminder(CONCERT));
        modelManager.snoozeReminder(CONCERT);
        assertFalse(modelManager.hasReminder(CONCERT));
    }

    @Test
    public void snoozeReminder_reminderInAddressBookAndNotLastOccurrence_snoozesReminder() {
        modelManager.addReminder(CLASS, USCOFFEE);
        assertTrue(modelManager.hasReminder(CLASS));
        int prevOccurrences = CLASS.getOccurrences().getOccurrences();
        modelManager.snoozeReminder(CLASS);
        int newOccurrences = modelManager.getFilteredReminderList().get(0).getOccurrences().getOccurrences();
        assertEquals(prevOccurrences, newOccurrences + 1);
    }

    ///// tests for filters

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredCcaList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCcaList().remove(0));
    }

    @Test
    public void getFilteredReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredReminderList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
