package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.model.util.Frequency;

public class ReminderFrequency {
    public static final String MESSAGE_CONSTRAINTS =
            "Frequency should be entered in [Positive number][Type] format, e.g. 2d"
                    + "\nThe maximum value of the number is 100 (inclusive).";

    /*
     * The frequency should be in [Positive number][Type] format.
     */
    public static final String VALIDATION_REGEX = "\\d+[dwmy]$";
    public final Frequency timePeriod;
    public final int numTimePeriod;

    /**
     * Constructs a {@code ReminderFrequency}.
     *
     * @param timePeriod The time period of the frequency, i.e. days, weeks, months, or years.
     * @param numTimePeriod The number of the period of time to be used e.g. 1 day or 3 months.
     */
    public ReminderFrequency(Frequency timePeriod, int numTimePeriod) {
        requireNonNull(timePeriod);
        requireNonNull(numTimePeriod);
        assert numTimePeriod > 0 : "Frequency parsing incorrect";

        this.timePeriod = timePeriod;
        this.numTimePeriod = numTimePeriod;
    }

    /**
     * Returns true if a given string is a valid frequency.
     */
    public static boolean isValidFrequency(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Frequency getTimePeriod() {
        return timePeriod;
    }

    public int getNumTimePeriod() {
        return numTimePeriod;
    }

    public boolean isRecurring() {
        return !timePeriod.equals(Frequency.ONE_OFF);
    }

    @Override
    public String toString() {
        if (isRecurring()) {
            return "Every " + numTimePeriod + " " + timePeriod;
        } else {
            return "Once only";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderFrequency // instanceof handles nulls
                    && timePeriod.toString().equals(((ReminderFrequency) other).timePeriod.toString())
                    && numTimePeriod == ((ReminderFrequency) other).numTimePeriod); // state check
    }

    @Override
    public int hashCode() {
        return timePeriod.hashCode() + numTimePeriod;
    }
}
