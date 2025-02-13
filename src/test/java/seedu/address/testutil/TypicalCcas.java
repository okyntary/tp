package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.cca.Cca;

/**
 * A utility class containing a list of {@code Cca} objects to be used in tests.
 */
public class TypicalCcas {

    public static final Cca NUSSO = new CcaBuilder().withName("NUSSO").build();
    public static final Cca USKICK = new CcaBuilder().withName("USKick").build();

    // Manually added
    public static final Cca USCOFFEE = new CcaBuilder().withName("USCoffee").build();
    public static final Cca USC = new CcaBuilder().withName("USC").build();

    // Manually added - CCA's details found in {@code CommandTestUtil}
    // TODO - too lazy to add for now

    public static final String KEYWORD_MATCHING_NUSSO = "NUSSO"; // A keyword that matches NUSSO

    private TypicalCcas() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Cca cca : getTypicalCcas()) {
            ab.addCca(cca);
        }
        return ab;
    }

    public static List<Cca> getTypicalCcas() {
        return new ArrayList<>(Arrays.asList(NUSSO, USKICK));
    }
}
