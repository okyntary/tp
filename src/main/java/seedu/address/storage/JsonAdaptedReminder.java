package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.util.Frequency;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String name;
    private final Date startDate;
    private final Frequency timePeriod;
    private final int numTimePeriod;
    private final int occurrences;

    private final String ccaName;
    private final ArrayList<Date> dates;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("name") String name, @JsonProperty("startDate") Date startDate,
                               @JsonProperty("frequency") Frequency timePeriod,
                               @JsonProperty("numTimePeriod") int numTimePeriod,
                               @JsonProperty("occurrences") int occurrences,
                               @JsonProperty("ccaName") String ccaName,
                               @JsonProperty("dates") ArrayList<Date> dates) {
        this.name = name;
        this.startDate = startDate;
        this.timePeriod = timePeriod;
        this.numTimePeriod = numTimePeriod;
        this.occurrences = occurrences;
        this.ccaName = ccaName;
        this.dates = dates;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        name = source.getName().fullName;
        startDate = source.getStartDate().startDate;
        timePeriod = source.getFrequency().timePeriod;
        numTimePeriod = source.getFrequency().numTimePeriod;
        occurrences = source.getOccurrences().getOccurrences();

        ccaName = source.getCcaName();
        dates = source.getDates();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReminderName.class.getSimpleName()));
        }
        if (!ReminderName.isValidName(name)) {
            throw new IllegalValueException(ReminderName.MESSAGE_CONSTRAINTS);
        }
        final ReminderName reminderName = new ReminderName(name);
        final ReminderStartDate reminderStartDate = new ReminderStartDate(startDate);
        final ReminderFrequency reminderFrequency = new ReminderFrequency(this.timePeriod, this.numTimePeriod);
        final ReminderOccurrence reminderOccurrence = new ReminderOccurrence(occurrences);

        return new Reminder(reminderName, reminderStartDate, reminderFrequency, reminderOccurrence, ccaName,
                dates);
    }
}
