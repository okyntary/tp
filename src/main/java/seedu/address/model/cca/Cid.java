package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Cid {

    public static final String MESSAGE_CONSTRAINTS =
            "Cid should be an integer";
    public static final String VALIDATION_REGEX = "\\d*";
    public final String value;

    /**
     * Constructs a {@code Cid}.
     *
     * @param cid A valid integer.
     */
    public Cid(String cid) {
        // TODO: cid.trim() shouldn't be done here
        cid = cid.trim();
        requireNonNull(cid);
        checkArgument(isValidCid(cid), MESSAGE_CONSTRAINTS);
        value = cid;
    }

    /**
     * Returns true if a given string is a valid integer.
     */
    public static boolean isValidCid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cid // instanceof handles nulls
                && value.equals(((Cid) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
