package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_TAG_INTEREST_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_TAG_INTERVIEW_NEEDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCcas.NUSSO;
import static seedu.address.testutil.TypicalCcas.TRACK;
import static seedu.address.testutil.TypicalCcas.USKICK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CcaBuilder;

public class CcaTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Cca cca = new CcaBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> cca.getTags().remove(0));
    }

    @Test
    public void isSameCca() {
        // same object -> returns true
        assertTrue(NUSSO.isSameCca(NUSSO));

        // null -> returns false
        assertFalse(NUSSO.isSameCca(null));

        // same name, all other attributes different -> returns true
        Cca editedNusso = new CcaBuilder(NUSSO).withTags(VALID_CCA_TAG_INTERVIEW_NEEDED).build();
        assertTrue(NUSSO.isSameCca(editedNusso));

        // different name, all other attributes same -> returns false
        editedNusso = new CcaBuilder(NUSSO).withName(VALID_CCA_NAME_TRACK).build();
        assertFalse(NUSSO.isSameCca(editedNusso));

        // name differs in case, all other attributes same -> returns false
        Cca editedTrack = new CcaBuilder(TRACK).withName(VALID_CCA_NAME_TRACK.toLowerCase()).build();
        assertFalse(TRACK.isSameCca(editedTrack));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_CCA_NAME_TRACK + " ";
        editedTrack = new CcaBuilder(TRACK).withName(nameWithTrailingSpaces).build();
        assertFalse(TRACK.isSameCca(editedTrack));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Cca nussoCopy = new CcaBuilder(NUSSO).build();
        assertTrue(NUSSO.equals(nussoCopy));

        // same object -> returns true
        assertTrue(NUSSO.equals(NUSSO));

        // null -> returns false
        assertFalse(NUSSO.equals(null));

        // different type -> returns false
        assertFalse(NUSSO.equals(5));

        // different person -> returns false
        assertFalse(NUSSO.equals(USKICK));

        // different name -> returns false
        Cca editedNusso = new CcaBuilder(NUSSO).withName(VALID_CCA_TAG_INTEREST_GROUP).build();
        assertFalse(NUSSO.equals(editedNusso));

        // different phone -> returns false
        editedNusso = new CcaBuilder(NUSSO).withTags(VALID_PHONE_BOB).build();
        assertFalse(NUSSO.equals(editedNusso));
    }
}
