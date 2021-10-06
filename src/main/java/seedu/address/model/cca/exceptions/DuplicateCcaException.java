package seedu.address.model.cca.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCcaException extends RuntimeException {
    public DuplicateCcaException() {
        super("Operation would result in duplicate CCAs");
    }
}
