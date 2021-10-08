package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCcas.NUSSO;

import org.junit.jupiter.api.Test;

class JsonAdaptedCcaTest {

    private static final String VALID_NAME = NUSSO.getName().toString();

    @Test
    public void toModelType_validCcaDetails_returnsCca() throws Exception {
        JsonAdaptedCca cca = new JsonAdaptedCca(NUSSO);
        assertEquals(NUSSO, cca.toModelType());
    }

}
