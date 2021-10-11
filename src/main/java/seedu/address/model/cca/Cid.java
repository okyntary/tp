package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Cid {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d*";
    public final String value;

    /**
     * Constructs a {@code Cid}.
     *
     * @param cid A valid phone number.
     */
    public Cid(String cid) {
        requireNonNull(cid);
        checkArgument(isValidCid(cid), MESSAGE_CONSTRAINTS);
        value = cid;
    }

    /**
     * Returns true if a given string is a valid phone number.
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
