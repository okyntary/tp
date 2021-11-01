package seedu.address.model.reminder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Reminder}'s {@code CcaName} matches any of the keywords given.
 */
public class ReminderNameContainsKeywordsPredicate implements Predicate<Reminder> {
    private final List<String> keywords;

    public ReminderNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Reminder reminder) {
        return keywords.stream()
                .anyMatch(keyword ->
                        Arrays.stream(keyword.split(" "))
                            .anyMatch(word -> StringUtil.containsWordIgnoreCase(reminder.getName().fullName, word)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ReminderNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
