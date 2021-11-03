package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REHEARSAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_TIME_PERIOD_10;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_NAME_REHEARSAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_PERIOD_DAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showReminderAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.ReminderBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ReminderEditCommand.
 */
public class ReminderEditCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Reminder editedReminder = new ReminderBuilder().build();
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(editedReminder).build();
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(INDEX_FIRST_REMINDER, descriptor);

        String expectedMessage = String.format(ReminderEditCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReminder(model.getFilteredReminderList().get(0), editedReminder);

        assertCommandSuccess(reminderEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastReminder = Index.fromOneBased(model.getFilteredReminderList().size());
        Reminder lastReminder = model.getFilteredReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder editedReminder = reminderInList.withName(VALID_NAME_BOB)
                .withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_10).build();

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withName(VALID_NAME_BOB)
                .withFrequency(VALID_TIME_PERIOD_DAY, VALID_NUM_TIME_PERIOD_10).build();
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(ReminderEditCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReminder(lastReminder, editedReminder);

        assertCommandSuccess(reminderEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(INDEX_FIRST_REMINDER,
                new EditReminderDescriptor());
        Reminder editedReminder = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());

        String expectedMessage = String.format(ReminderEditCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(reminderEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showReminderAtIndex(model, INDEX_FIRST_REMINDER);

        Reminder reminderInFilteredList = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        Reminder editedReminder = new ReminderBuilder(reminderInFilteredList)
                .withName(VALID_REMINDER_NAME_PROJECT).build();
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(INDEX_FIRST_REMINDER,
                new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_PROJECT).build());

        String expectedMessage = String.format(ReminderEditCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReminder(model.getFilteredReminderList().get(0), editedReminder);

        assertCommandSuccess(reminderEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminderUnfilteredList_failure() {
        Reminder firstReminder = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(firstReminder).build();
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(INDEX_SECOND_REMINDER, descriptor);

        assertCommandFailure(reminderEditCommand, model, ReminderEditCommand.MESSAGE_DUPLICATE_EDIT);
    }

    @Test
    public void execute_duplicateReminderFilteredList_failure() {
        showReminderAtIndex(model, INDEX_FIRST_REMINDER);

        // edit reminder in filtered list into a duplicate in address book
        Reminder reminderInList = model.getAddressBook().getReminderList().get(INDEX_SECOND_REMINDER.getZeroBased());
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(INDEX_FIRST_REMINDER,
                new EditReminderDescriptorBuilder(reminderInList).build());

        assertCommandFailure(reminderEditCommand, model, ReminderEditCommand.MESSAGE_DUPLICATE_EDIT);
    }

    @Test
    public void execute_invalidReminderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withName(VALID_REMINDER_NAME_REHEARSAL).build();
        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(reminderEditCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidReminderIndexFilteredList_failure() {
        showReminderAtIndex(model, INDEX_FIRST_REMINDER);
        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        ReminderEditCommand reminderEditCommand = new ReminderEditCommand(outOfBoundIndex,
                new EditReminderDescriptorBuilder().withName(VALID_REMINDER_NAME_MEETING).build());

        assertCommandFailure(reminderEditCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ReminderEditCommand standardCommand = new ReminderEditCommand(INDEX_FIRST_REMINDER, DESC_REHEARSAL);

        // same values -> returns true
        EditReminderDescriptor copyDescriptor = new EditReminderDescriptor(DESC_REHEARSAL);
        ReminderEditCommand commandWithSameValues = new ReminderEditCommand(INDEX_FIRST_REMINDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ReminderEditCommand(INDEX_SECOND_REMINDER, DESC_REHEARSAL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ReminderEditCommand(INDEX_FIRST_REMINDER, DESC_MEETING)));
    }
}
