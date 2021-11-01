package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCcas.NUSSO;
import static seedu.address.testutil.TypicalCcas.USKICK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cca.exceptions.CcaNotFoundException;
import seedu.address.model.cca.exceptions.DuplicateCcaException;
import seedu.address.testutil.CcaBuilder;

public class UniqueCcaListTest {

    private final UniqueCcaList uniqueCcaList = new UniqueCcaList();

    @Test
    public void contains_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.contains(null));
    }

    @Test
    public void contains_ccaNotInList_returnsFalse() {
        assertFalse(uniqueCcaList.contains(NUSSO));
    }

    @Test
    public void contains_ccaInList_returnsTrue() {
        uniqueCcaList.add(NUSSO);
        assertTrue(uniqueCcaList.contains(NUSSO));
    }

    @Test
    public void contains_ccaWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCcaList.add(NUSSO);
        Cca editedNusso = new CcaBuilder(NUSSO).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueCcaList.contains(editedNusso));
    }

    @Test
    public void add_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.add(null));
    }

    @Test
    public void add_duplicateCca_throwsDuplicateCcaException() {
        uniqueCcaList.add(NUSSO);
        assertThrows(DuplicateCcaException.class, () -> uniqueCcaList.add(NUSSO));
    }

    @Test
    public void setCca_nullTargetCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.setCca(null, NUSSO));
    }

    @Test
    public void setCca_nullEditedCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.setCca(NUSSO, null));
    }

    @Test
    public void setCca_targetCcaNotInList_throwsCcaNotFoundException() {
        assertThrows(CcaNotFoundException.class, () -> uniqueCcaList.setCca(NUSSO, NUSSO));
    }

    @Test
    public void setCca_editedCcaIsSameCca_success() {
        uniqueCcaList.add(NUSSO);
        uniqueCcaList.setCca(NUSSO, NUSSO);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        expectedUniqueCcaList.add(NUSSO);
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCca_editedCcaHasSameIdentity_success() {
        uniqueCcaList.add(NUSSO);
        Cca editedNusso = new CcaBuilder(NUSSO).withTags(VALID_TAG_HUSBAND).build();
        uniqueCcaList.setCca(NUSSO, editedNusso);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        expectedUniqueCcaList.add(editedNusso);
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCca_editedCcaHasDifferentIdentity_success() {
        uniqueCcaList.add(NUSSO);
        uniqueCcaList.setCca(NUSSO, USKICK);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        expectedUniqueCcaList.add(USKICK);
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCca_editedCcaHasNonUniqueIdentity_throwsDuplicateCcaException() {
        uniqueCcaList.add(NUSSO);
        uniqueCcaList.add(USKICK);
        assertThrows(DuplicateCcaException.class, () -> uniqueCcaList.setCca(NUSSO, USKICK));
    }

    @Test
    public void remove_nullCca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.remove(null));
    }

    @Test
    public void remove_ccaDoesNotExist_throwsCcaNotFoundException() {
        assertThrows(CcaNotFoundException.class, () -> uniqueCcaList.remove(NUSSO));
    }

    @Test
    public void remove_existingCca_removesCca() {
        uniqueCcaList.add(NUSSO);
        uniqueCcaList.remove(NUSSO);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCcas_nullUniqueCcaList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.setCcas((UniqueCcaList) null));
    }

    @Test
    public void setCcas_uniqueCcaList_replacesOwnListWithProvidedUniqueCcaList() {
        uniqueCcaList.add(NUSSO);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        expectedUniqueCcaList.add(USKICK);
        uniqueCcaList.setCcas(expectedUniqueCcaList);
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCcas_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCcaList.setCcas((List<Cca>) null));
    }

    @Test
    public void setCcas_list_replacesOwnListWithProvidedList() {
        uniqueCcaList.add(NUSSO);
        List<Cca> ccaList = Collections.singletonList(USKICK);
        uniqueCcaList.setCcas(ccaList);
        UniqueCcaList expectedUniqueCcaList = new UniqueCcaList();
        expectedUniqueCcaList.add(USKICK);
        assertEquals(expectedUniqueCcaList, uniqueCcaList);
    }

    @Test
    public void setCcas_listWithDuplicateCcas_throwsDuplicateCcaException() {
        List<Cca> listWithDuplicateCcas = Arrays.asList(NUSSO, NUSSO);
        assertThrows(DuplicateCcaException.class, () -> uniqueCcaList.setCcas(listWithDuplicateCcas));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCcaList.asUnmodifiableObservableList().remove(0));
    }
}
