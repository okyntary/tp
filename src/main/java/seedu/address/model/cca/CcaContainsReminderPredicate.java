package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.reminder.Reminder;

public class CcaContainsReminderPredicate implements Predicate<Cca> {
    private final List<Reminder> reminders;

    public CcaContainsReminderPredicate(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @Override
    public boolean test(Cca cca) {
        requireNonNull(cca);
        return reminders.stream()
                .anyMatch(reminder -> reminder.getCcaName().equals(cca.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuitif same object
            || (other instanceof CcaContainsReminderPredicate) // handles nulls
            && reminders.equals(((CcaContainsReminderPredicate) other).reminders);
    }
}
