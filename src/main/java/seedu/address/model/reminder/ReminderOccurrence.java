package seedu.address.model.reminder;

public class ReminderOccurrence {
    public static final String MESSAGE_CONSTRAINTS = "Occurrences should be entered as a positive integer from "
            + "1 to 50 (inclusive), e.g. 2";

    private int occurrences;

    /**
     * Constructs a {@code ReminderOccurrence}.
     *
     * @param occurrences The number of times the reminder occurs.
     */
    public ReminderOccurrence(int occurrences) {
        assert occurrences > 0 : "Occurrence parsing incorrect";
        this.occurrences = occurrences;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public boolean isAtLastOccurrence() {
        return occurrences == 1;
    }

    @Override
    public String toString() {
        return Integer.toString(occurrences);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ReminderOccurrence
                    && occurrences == ((ReminderOccurrence) other).occurrences);
    }

    @Override
    public int hashCode() {
        return occurrences;
    }
}
