package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Reminder {
    // Identity fields
    private final ReminderName reminderName;
    private final ReminderStartDate reminderStartDate;
    private final ReminderFrequency reminderFrequency;
    private final ReminderOccurrence reminderOccurrence;

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

        seedu.address.model.reminder.Reminder otherReminder = (seedu.address.model.reminder.Reminder) other;
        return otherReminder.getName().equals(getName()) && otherReminder.getStartDate().equals(getStartDate())
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
