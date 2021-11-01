package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Reminder}'s {@code CcaName} matches any of the keywords given.
 */
public class ReminderContainsCcaNamePredicate implements Predicate<Reminder> {
    private final List<String> ccaNames;

    public ReminderContainsCcaNamePredicate(List<String> ccaNames) {
        this.ccaNames = ccaNames;
    }

    @Override
    public boolean test(Reminder reminder) {
        requireNonNull(reminder);
        return this.ccaNames.parallelStream()
                .anyMatch(ccaName -> ccaName.equals(reminder.getCcaName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderContainsCcaNamePredicate // instanceof handles nulls
                && ccaNames.equals(((ReminderContainsCcaNamePredicate) other).ccaNames)); // state check
    }
}
