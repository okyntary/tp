package seedu.address.logic.commands.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCcaAtIndex;
import static seedu.address.testutil.TypicalCcas.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CCA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PersonDeleteCommand}.
 */
public class CcaDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Cca ccaToDelete = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        CcaDeleteCommand ccaDeleteCommand = new CcaDeleteCommand(INDEX_FIRST_CCA);

        String expectedMessage = String.format(CcaDeleteCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteCca(ccaToDelete);

        assertCommandSuccess(ccaDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCcaList().size() + 1);
        CcaDeleteCommand ccaDeleteCommand = new CcaDeleteCommand(outOfBoundIndex);

        assertCommandFailure(ccaDeleteCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        Cca ccaToDelete = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        CcaDeleteCommand ccaDeleteCommand = new CcaDeleteCommand(INDEX_FIRST_CCA);

        String expectedMessage = String.format(CcaDeleteCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteCca(ccaToDelete);
        showAllCcas(expectedModel);

        assertCommandSuccess(ccaDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        Index outOfBoundIndex = INDEX_SECOND_CCA;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCcaList().size());

        CcaDeleteCommand ccaDeleteCommand = new CcaDeleteCommand(outOfBoundIndex);

        assertCommandFailure(ccaDeleteCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CcaDeleteCommand deleteFirstCommand = new CcaDeleteCommand(INDEX_FIRST_CCA);
        CcaDeleteCommand deleteSecondCommand = new CcaDeleteCommand(INDEX_SECOND_CCA);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        CcaDeleteCommand deleteFirstCommandCopy = new CcaDeleteCommand(INDEX_FIRST_CCA);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different cca -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show everyone.
     */
    private void showAllCcas(Model model) {
        model.updateFilteredCcaList(p -> true);
    }
}
