package seedu.address.testutil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.util.Frequency;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder MEETING = new ReminderBuilder().withName("Meeting")
            .withStartDate(getDateFromString("2021-10-30"))
            .withFrequency(Frequency.WEEK, 1)
            .withOccurrence(30)
            .withCcaName("").build();
    public static final Reminder CLASS = new ReminderBuilder().withName("Class")
            .withStartDate(getDateFromString("2021-11-01"))
            .withFrequency(Frequency.DAY, 2)
            .withOccurrence(50)
            .withCcaName("").build();
    public static final Reminder CHRISTMAS = new ReminderBuilder().withName("Holiday")
            .withStartDate(getDateFromString("2021-12-25"))
            .withFrequency(Frequency.YEAR, 1)
            .withOccurrence(10)
            .withCcaName("").build();
    public static final Reminder CONCERT = new ReminderBuilder().withName("Concert")
            .withStartDate(getDateFromString("2022-02-24"))
            .withFrequency(Frequency.ONE_OFF, 1)
            .withOccurrence(1)
            .withCcaName("").build();

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical reminder.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reminder reminder : getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(MEETING, CLASS, CHRISTMAS, CONCERT));
    }

    public static Date getDateFromString(String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (java.text.ParseException e) {
            date = new Date();
        }
        return date;
    }
}
