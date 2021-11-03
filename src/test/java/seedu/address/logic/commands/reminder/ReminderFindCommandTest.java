package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReminders.CHRISTMAS;
import static seedu.address.testutil.TypicalReminders.CLASS;
import static seedu.address.testutil.TypicalReminders.MEETING;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReminderNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ReminderFindCommand}.
 */
public class ReminderFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ReminderNameContainsKeywordsPredicate firstPredicate =
                new ReminderNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ReminderNameContainsKeywordsPredicate secondPredicate =
                new ReminderNameContainsKeywordsPredicate(Collections.singletonList("second"));

        ReminderFindCommand findFirstCommand = new ReminderFindCommand(firstPredicate);
        ReminderFindCommand findSecondCommand = new ReminderFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ReminderFindCommand findFirstCommandCopy = new ReminderFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different reminder -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noReminderFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 0);
        ReminderNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ReminderFindCommand command = new ReminderFindCommand(predicate);
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredReminderList());
    }

    @Test
    public void execute_multipleKeywords_multipleRemindersFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 3);
        ReminderNameContainsKeywordsPredicate predicate = preparePredicate("Holiday Class Meeting");
        ReminderFindCommand command = new ReminderFindCommand(predicate);
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING, CLASS, CHRISTMAS), model.getFilteredReminderList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ReminderNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ReminderNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
