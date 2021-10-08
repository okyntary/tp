package seedu.address.testutil;

import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;

/**
 * A utility class to help with building Cca objects.
 */
public class CcaBuilder {

    public static final String DEFAULT_NAME = "NUSSO";

    private CcaName name;

    /**
     * Creates a {@code Cca} with the default details.
     */
    public CcaBuilder() {
        name = new CcaName(DEFAULT_NAME);
    }

    /**
     * Initializes the CcaBuilder with the data of {@code CcaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        name = ccaToCopy.getName();
    }

    /**
     * Sets the {@code CcaName} of the {@code Cca} that we are building.
     */
    public CcaBuilder withName(String name) {
        this.name = new CcaName(name);
        return this;
    }

    public Cca build() {
        return new Cca(name);
    }
}
