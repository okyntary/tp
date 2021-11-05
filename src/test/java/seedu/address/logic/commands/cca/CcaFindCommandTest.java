package seedu.address.logic.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CCAS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.NUSSO;
import static seedu.address.testutil.TypicalCcas.USC;
import static seedu.address.testutil.TypicalCcas.USCOFFEE;
import static seedu.address.testutil.TypicalCcas.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.CcaNameContainsKeywordsPredicate;

public class CcaFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CcaNameContainsKeywordsPredicate firstPredicate =
                new CcaNameContainsKeywordsPredicate(Collections.singletonList("first"));
        CcaNameContainsKeywordsPredicate secondPredicate =
                new CcaNameContainsKeywordsPredicate(Collections.singletonList("second"));

        CcaFindCommand findFirstCommand = new CcaFindCommand(firstPredicate);
        CcaFindCommand findSecondCommand = new CcaFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        CcaFindCommand findFirstCommandCopy = new CcaFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different cca -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCcaFound() {
        String expectedMessage = String.format(MESSAGE_CCAS_LISTED_OVERVIEW, 0);
        CcaNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        CcaFindCommand command = new CcaFindCommand(predicate);
        expectedModel.updateFilteredCcaList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCcaList());
    }

    @Test
    public void execute_oneKeyword_oneCcaFound() {
        String expectedMessage = String.format(MESSAGE_CCAS_LISTED_OVERVIEW, 1);
        CcaNameContainsKeywordsPredicate predicate = preparePredicate("nusso");
        CcaFindCommand command = new CcaFindCommand(predicate);
        expectedModel.updateFilteredCcaList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NUSSO), model.getFilteredCcaList());
    }

    @Test
    public void execute_multipleKeywords_multipleCcasFound() {
        String expectedMessage = String.format(MESSAGE_CCAS_LISTED_OVERVIEW, 3);
        CcaNameContainsKeywordsPredicate predicate = preparePredicate("nuSSo uscoFFee USC");
        CcaFindCommand command = new CcaFindCommand(predicate);
        expectedModel.updateFilteredCcaList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NUSSO, USCOFFEE, USC), model.getFilteredCcaList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private CcaNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CcaNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
