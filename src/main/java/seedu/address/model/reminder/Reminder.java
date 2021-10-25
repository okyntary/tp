package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.address.model.cca.Cca;
import seedu.address.model.util.Frequency;

public class Reminder {
    private static final SimpleDateFormat PARSE_DATE_TO_STRING_FORMAT = new SimpleDateFormat("E, dd MMM yyyy");

    // Identity fields
    private final ReminderName reminderName;
    private final ReminderStartDate reminderStartDate;
    private final ReminderFrequency reminderFrequency;
    private final ReminderOccurrence reminderOccurrence;

    // Data fields
    // Assumes a reminder can be tagged to at most 1 CCA
    private Cca cca;
    private ArrayList<Date> dates = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Reminder(ReminderName reminderName, ReminderStartDate reminderStartDate,
                    ReminderFrequency reminderFrequency, ReminderOccurrence reminderOccurrence) {
        requireAllNonNull(reminderName, reminderStartDate, reminderFrequency, reminderOccurrence);
        this.reminderName = reminderName;
        this.reminderStartDate = reminderStartDate;
        this.reminderFrequency = reminderFrequency;
        this.reminderOccurrence = reminderOccurrence;
        fillAllUpcomingDates();
    }

    private void setAllUpcomingDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    private void fillAllUpcomingDates() {
        int occurrences = reminderOccurrence.getOccurrences();
        assert occurrences > 0 : "An error occurred getting the dates for the Reminder";
        dates.add(reminderStartDate.getDate());
        occurrences--;

        Frequency frequency = reminderFrequency.getTimePeriod();
        int numFrequency = reminderFrequency.getNumTimePeriod();
        while (occurrences > 0) {
            // TODO: handle the feb stuff
            Date prevDate = dates.get(dates.size() - 1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(prevDate);
            switch (frequency) {
            case DAY:
                calendar.add(Calendar.DAY_OF_YEAR, numFrequency);
                break;
            case WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, numFrequency);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, numFrequency);
                break;
            case YEAR:
                calendar.add(Calendar.YEAR, numFrequency);
                break;
            default:
                // hmm
            }
            dates.add(calendar.getTime());
            occurrences--;
        }
    }

    public String getNextDate() {
        return PARSE_DATE_TO_STRING_FORMAT.format(dates.get(0));
    }

    public ReminderName getName() {
        return reminderName;
    }

    public ReminderStartDate getStartDate() {
        return reminderStartDate;
    }

    public ReminderFrequency getFrequency() {
        return reminderFrequency;
    }

    public ReminderOccurrence getOccurrences() {
        return reminderOccurrence;
    }

    public Cca getCca() {
        return cca;
    }

    public void setCca(Cca cca) {
        this.cca = cca;
    }

    public boolean isAtLastOccurrence() {
        return reminderOccurrence.isAtLastOccurrence();
    }

    /**
     * Gets the snoozed Reminder
     * This decrements the occurrences remaining.
     *
     * @return A new Reminder representing the previous Reminder that has been snoozed.
     */
    public Reminder getSnoozedReminder() {
        Reminder snoozedReminder = new Reminder(getName(), getStartDate(), getFrequency(),
                new ReminderOccurrence(getOccurrences().getOccurrences() - 1));
        dates.remove(0);
        snoozedReminder.setAllUpcomingDates(dates);
        return snoozedReminder;
    }

    /**
     * Returns true if both Reminders have the same reminderName.
     * This defines a weaker notion of equality between two Reminders.
     */
    public boolean isSameReminder(seedu.address.model.reminder.Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getName().equals(getName());
    }

    /**
     * Returns true if both Reminders have the same identity and data fields.
     * This defines a stronger notion of equality between two Reminders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.reminder.Reminder)) {
            return false;
        }

        // Same if they have the same name, associated cca, start date, frequency, and occurrences
        seedu.address.model.reminder.Reminder otherReminder = (seedu.address.model.reminder.Reminder) other;
        return otherReminder.getName().equals(getName()) && otherReminder.getCca().equals(getCca())
                && otherReminder.getStartDate().equals(getStartDate())
                && otherReminder.getFrequency().equals(getFrequency())
                && otherReminder.getOccurrences().equals(getOccurrences());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(reminderName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        return builder.toString();
    }
}
