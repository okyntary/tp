package seedu.address.logic.commands.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_USKICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_NUSSO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCcaAtIndex;
import static seedu.address.testutil.TypicalCcas.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CCA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.cca.CcaEditCommand.EditCcaDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.EditCcaDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for CcaEditCommand.
 */
public class CcaEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Cca editedCca = new CcaBuilder().build();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(editedCca).build();
        CcaEditCommand ccaEditCommand = new CcaEditCommand(INDEX_FIRST_CCA, descriptor);

        String expectedMessage = String.format(CcaEditCommand.MESSAGE_EDIT_CCA_SUCCESS, editedCca);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCca(model.getFilteredCcaList().get(0), editedCca);

        assertCommandSuccess(ccaEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCca = Index.fromOneBased(model.getFilteredCcaList().size());
        Cca lastCca = model.getFilteredCcaList().get(indexLastCca.getZeroBased());

        CcaBuilder ccaInList = new CcaBuilder(lastCca);
        Cca editedCca = ccaInList.withName(VALID_CCA_NAME_TRACK).build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_TRACK).build();
        CcaEditCommand ccaEditCommand = new CcaEditCommand(indexLastCca, descriptor);

        String expectedMessage = String.format(CcaEditCommand.MESSAGE_EDIT_CCA_SUCCESS, editedCca);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCca(lastCca, editedCca);

        assertCommandSuccess(ccaEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        CcaEditCommand ccaEditCommand = new CcaEditCommand(INDEX_FIRST_CCA, new EditCcaDescriptor());
        Cca editedCca = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());

        String expectedMessage = String.format(CcaEditCommand.MESSAGE_EDIT_CCA_SUCCESS, editedCca);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(ccaEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        Cca ccaInFilteredList = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        Cca editedCca = new CcaBuilder(ccaInFilteredList).withName(VALID_CCA_NAME_NUSSO).build();
        CcaEditCommand ccaEditCommand = new CcaEditCommand(INDEX_FIRST_CCA,
                new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_NUSSO).build());

        String expectedMessage = String.format(CcaEditCommand.MESSAGE_EDIT_CCA_SUCCESS, editedCca);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCca(model.getFilteredCcaList().get(0), editedCca);

        assertCommandSuccess(ccaEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCcaUnfilteredList_failure() {
        Cca firstCca = model.getFilteredCcaList().get(INDEX_FIRST_CCA.getZeroBased());
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(firstCca).build();
        CcaEditCommand ccaEditCommand = new CcaEditCommand(INDEX_SECOND_CCA, descriptor);

        assertCommandFailure(ccaEditCommand, model, CcaEditCommand.MESSAGE_DUPLICATE_CCA);
    }

    @Test
    public void execute_duplicateCcaFilteredList_failure() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);

        // edit cca in filtered list into a duplicate in address book
        Cca ccaInList = model.getAddressBook().getCcaList().get(INDEX_SECOND_CCA.getZeroBased());
        CcaEditCommand ccaEditCommand = new CcaEditCommand(INDEX_FIRST_CCA,
                new EditCcaDescriptorBuilder(ccaInList).build());

        assertCommandFailure(ccaEditCommand, model, CcaEditCommand.MESSAGE_DUPLICATE_CCA);
    }

    @Test
    public void execute_invalidCcaIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCcaList().size() + 1);
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_NUSSO).build();
        CcaEditCommand ccaEditCommand = new CcaEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(ccaEditCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCcaIndexFilteredList_failure() {
        showCcaAtIndex(model, INDEX_FIRST_CCA);
        Index outOfBoundIndex = INDEX_SECOND_CCA;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCcaList().size());

        CcaEditCommand ccaEditCommand = new CcaEditCommand(outOfBoundIndex,
                new EditCcaDescriptorBuilder().withName(VALID_CCA_NAME_NUSSO).build());

        assertCommandFailure(ccaEditCommand, model, Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CcaEditCommand standardCommand = new CcaEditCommand(INDEX_FIRST_CCA, DESC_TRACK);

        // same values -> returns true
        EditCcaDescriptor copyDescriptor = new EditCcaDescriptor(DESC_TRACK);
        CcaEditCommand commandWithSameValues = new CcaEditCommand(INDEX_FIRST_CCA, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CcaEditCommand(INDEX_SECOND_CCA, DESC_TRACK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CcaEditCommand(INDEX_FIRST_CCA, DESC_USKICK)));
    }
}
