package seedu.address.testutil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.util.Frequency;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_NAME = "meeting";
    public static final Frequency DEFAULT_TIME_PERIOD = Frequency.DAY;
    public static final int DEFAULT_NUM_TIME_PERIOD = 2;
    public static final int DEFAULT_OCCURRENCES = 3;
    public static final String DEFAULT_CCA = "nusso";
    private static Date defaultDate = new Date();

    // Identity fields
    private ReminderName reminderName;
    private ReminderStartDate reminderStartDate;
    private ReminderFrequency reminderFrequency;
    private ReminderOccurrence reminderOccurrence;

    // Data fields
    private String ccaName;
    private ArrayList<Date> dates = new ArrayList<>();

    static {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        try {
            defaultDate = ReminderStartDate.PARSE_INPUT_DATE_FORMAT.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a {@code Reminder} with the default details.
     */
    public ReminderBuilder() {
        reminderName = new ReminderName(DEFAULT_NAME);
        reminderStartDate = new ReminderStartDate(defaultDate);
        reminderFrequency = new ReminderFrequency(DEFAULT_TIME_PERIOD, DEFAULT_NUM_TIME_PERIOD);
        reminderOccurrence = new ReminderOccurrence(DEFAULT_OCCURRENCES);
        ccaName = DEFAULT_CCA;
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code ReminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        reminderName = reminderToCopy.getName();
        reminderStartDate = reminderToCopy.getStartDate();
        reminderFrequency = reminderToCopy.getFrequency();
        reminderOccurrence = reminderToCopy.getOccurrences();
        ccaName = reminderToCopy.getCcaName();
    }

    /**
     * Sets the {@code ReminderName} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withName(String name) {
        this.reminderName = new ReminderName(name);
        return this;
    }

    /**
     * Sets the {@code ReminderStartDate} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withStartDate(Date date) {
        this.reminderStartDate = new ReminderStartDate(date);
        return this;
    }

    /**
     * Sets the {@code ReminderFrequency} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withFrequency(Frequency timePeriod, int numTimePeriod) {
        this.reminderFrequency = new ReminderFrequency(timePeriod, numTimePeriod);
        return this;
    }

    /**
     * Sets the {@code ReminderOccurrence} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withOccurrence(int occurrences) {
        this.reminderOccurrence = new ReminderOccurrence(occurrences);
        return this;
    }

    /**
     * Sets the {@code CcaName} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withCcaName(String ccaName) {
        this.ccaName = ccaName;
        return this;
    }

    /**
     * Builds a {@code Reminder} with the details specified.
     *
     * @return The built reminder
     */
    public Reminder build() {
        Reminder reminder = new Reminder(reminderName, reminderStartDate, reminderFrequency, reminderOccurrence);
        reminder.setCcaName(ccaName);
        return reminder;
    }
}

