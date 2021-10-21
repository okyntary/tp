package seedu.address.model.reminder;

public class ReminderOccurrence {
    public static final String MESSAGE_CONSTRAINTS = "Occurrences should be entered as an integer e.g. 2";

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
