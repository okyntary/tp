package seedu.address.testutil;

import java.util.Date;

import seedu.address.logic.commands.reminder.ReminderEditCommand.EditReminderDescriptor;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;
import seedu.address.model.util.Frequency;

/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setReminderName(reminder.getName());
        descriptor.setReminderStartDate(reminder.getStartDate());
        descriptor.setReminderFrequency(reminder.getFrequency());
        descriptor.setReminderOccurrence(reminder.getOccurrences());
        descriptor.setCca(reminder.getCcaName());
    }

    /**
     * Sets the {@code ReminderName} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withName(String name) {
        descriptor.setReminderName(new ReminderName(name));
        return this;
    }

    /**
     * Sets the {@code ReminderStartDate} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withStartDate(Date date) {
        descriptor.setReminderStartDate(new ReminderStartDate(date));
        return this;
    }

    /**
     * Sets the {@code ReminderFrequency} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withFrequency(Frequency timePeriod, int numTimePeriod) {
        descriptor.setReminderFrequency(new ReminderFrequency(timePeriod, numTimePeriod));
        return this;
    }

    /**
     * Sets the {@code ReminderOccurrence} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withOccurrence(int occurrences) {
        descriptor.setReminderOccurrence(new ReminderOccurrence(occurrences));
        return this;
    }

    /**
     * Sets the CCA name of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withCcaName(String ccaName) {
        descriptor.setCca(ccaName);
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
