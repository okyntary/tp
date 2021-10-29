package seedu.address.commons.exceptions;

/**
 * Signals that some given index exceeds ePoch's maximum capacity.
 */
public class IndexExceedsCapacityException extends IllegalValueException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IndexExceedsCapacityException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public IndexExceedsCapacityException(String message, Throwable cause) {
        super(message, cause);
    }
}
