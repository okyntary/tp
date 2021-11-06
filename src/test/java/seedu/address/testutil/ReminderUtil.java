package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.text.SimpleDateFormat;

import seedu.address.logic.commands.reminder.ReminderAddCommand;
import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.util.Frequency;

/**
 * A utility class for Reminder.
 */
public class ReminderUtil {
    /**
     * Returns an add command string for adding the {@code reminder}.
     */
    public static String getAddCommand(Reminder reminder) {
        return ReminderAddCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(Reminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CCA_ID + "1 "); // doesn't matter
        sb.append(PREFIX_NAME + reminder.getName().fullName + " ");
        sb.append(PREFIX_START_DATE + getStartDateString(reminder.getStartDate()) + " ");
        sb.append(getFrequencyString(reminder.getFrequency()));
        sb.append(getOccurrenceString(reminder.getFrequency(), reminder.getOccurrences()));
        return sb.toString();
    }

    private static String getStartDateString(ReminderStartDate startDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(startDate.startDate);
    }

    private static String getFrequencyString(ReminderFrequency frequency) {
        String frequencyString = "";
        switch (frequency.timePeriod) {
        case DAY:
            frequencyString = frequencyString + PREFIX_FREQUENCY + frequency.numTimePeriod + "d ";
            break;
        case WEEK:
            frequencyString = frequencyString + PREFIX_FREQUENCY + frequency.numTimePeriod + "w ";
            break;
        case MONTH:
            frequencyString = frequencyString + PREFIX_FREQUENCY + frequency.numTimePeriod + "m ";
            break;
        case YEAR:
            frequencyString = frequencyString + PREFIX_FREQUENCY + frequency.numTimePeriod + "y ";
            break;
        default:
            break;
        }

        return frequencyString;
    }

    private static String getOccurrenceString(ReminderFrequency frequency, ReminderOccurrence occurrence) {
        String occurrenceString = "";
        if (frequency.timePeriod == Frequency.ONE_OFF) {
            return occurrenceString;
        }
        return occurrenceString + PREFIX_OCCURRENCES + occurrence.getOccurrences();
    }

    /**
     * Returns the part of command string for the given {@code EditReminderDescriptor}'s details.
     */
    public static String getEditReminderDescriptorDetails(EditReminderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getReminderName().ifPresent(reminderName -> sb.append(PREFIX_NAME)
                .append(reminderName.fullName).append(" "));
        descriptor.getReminderStartDate().ifPresent(startDate -> sb.append(PREFIX_START_DATE)
                .append(getStartDateString(startDate)).append(" "));
        descriptor.getReminderFrequency().ifPresent(frequency -> {
            descriptor.getReminderOccurrence().ifPresent(occurrence -> {
                sb.append(getFrequencyString(frequency));
                sb.append(getOccurrenceString(frequency, occurrence));
            });
        });
        return sb.toString();
    }
}
