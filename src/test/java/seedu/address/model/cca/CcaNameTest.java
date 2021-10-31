package seedu.address.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CcaNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CcaName(null));
    }

    @Test
    public void constructor_invalidCcaName_throwsIllegalArgumentException() {
        String invalidCcaName = "";
        assertThrows(IllegalArgumentException.class, () -> new CcaName(invalidCcaName));
    }

    @Test
    public void isValidCcaName() {
        // null name
        assertThrows(NullPointerException.class, () -> CcaName.isValidCcaName(null));

        // invalid name
        assertFalse(CcaName.isValidCcaName("")); // empty string
        assertFalse(CcaName.isValidCcaName(" ")); // spaces only
        assertFalse(CcaName.isValidCcaName("^")); // only non-alphanumeric characters
        assertFalse(CcaName.isValidCcaName("nusso*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CcaName.isValidCcaName("nus symphony orchestra")); // alphabets only
        assertTrue(CcaName.isValidCcaName("12345")); // numbers only
        assertTrue(CcaName.isValidCcaName("nusso the 2nd")); // alphanumeric characters
        assertTrue(CcaName.isValidCcaName("Capital NUSSO")); // with capital letters
        assertTrue(CcaName.isValidCcaName("NUS Symphony Orchestra NUS Symphony Orchestra")); // long names
    }
}
