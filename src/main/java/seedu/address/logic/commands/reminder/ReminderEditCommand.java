package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFrequency;
import seedu.address.model.reminder.ReminderName;
import seedu.address.model.reminder.ReminderOccurrence;
import seedu.address.model.reminder.ReminderStartDate;


/**
 * Edits the details of an existing person in ePoch.
 */
public class ReminderEditCommand extends Command {

    public static final String COMMAND_WORD = "editr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number used in the displayed reminder list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer less than 1,000,000,000) "
            + "[" + PREFIX_NAME + "REMINDER_NAME] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_FREQUENCY + "FREQUENCY] "
            + "[" + PREFIX_OCCURRENCES + "OCCURRENCES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "NUSSO rehearsal "
            + PREFIX_START_DATE + "2021-10-31";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_ONE_OF_FREQUENCY_OCCURRENCE =
            "Please input either both frequency and occurrence or neither of them.";
    public static final String MESSAGE_DUPLICATE_EDIT = "This reminder already exists in ePoch.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public ReminderEditCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = editReminderDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = lastShownList.get(index.getZeroBased());
        if (editReminderDescriptor.getCcaIndex().isPresent()) {
            Index ccaIndex = editReminderDescriptor.getCcaIndex().get();
            List<Cca> lastShownCcaList = model.getFilteredCcaList();
            if (ccaIndex.getZeroBased() >= lastShownCcaList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
            }
            editReminderDescriptor.setCca(lastShownCcaList.get(ccaIndex.getZeroBased()).getName().fullName);
        }
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        if (!reminderToEdit.isSameReminder(editedReminder) && model.hasReminder(editedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_EDIT);
        }

        model.setReminder(reminderToEdit, editedReminder);

        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createEditedReminder(Reminder remToEdit, EditReminderDescriptor editRemDescriptor) {
        assert editRemDescriptor != null;

        ReminderName updatedName = editRemDescriptor.getReminderName().orElse(remToEdit.getName());
        ReminderStartDate updatedReminderStartDate = editRemDescriptor
                .getReminderStartDate().orElse(remToEdit.getStartDate());
        ReminderFrequency updatedFreq = editRemDescriptor
                .getReminderFrequency().orElse(remToEdit.getFrequency());
        ReminderOccurrence updatedReminderOccurrence = editRemDescriptor
                .getReminderOccurrence().orElse(remToEdit.getOccurrences());
        String updatedCca = editRemDescriptor
                .getCca().orElse(remToEdit.getCcaName());

        Reminder newReminder = new Reminder(updatedName, updatedReminderStartDate,
                updatedFreq, updatedReminderOccurrence);
        newReminder.setCcaName(updatedCca);
        return newReminder;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderEditCommand)) {
            return false;
        }

        // state check
        ReminderEditCommand e = (ReminderEditCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditReminderDescriptor {
        private ReminderName reminderName;
        private ReminderStartDate reminderStartDate;
        private ReminderFrequency reminderFrequency;
        private ReminderOccurrence reminderOccurrence;

        private Index ccaIndex;
        private String ccaName;

        public EditReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            setReminderName(toCopy.reminderName);
            setReminderStartDate(toCopy.reminderStartDate);
            setReminderFrequency(toCopy.reminderFrequency);
            setReminderOccurrence(toCopy.reminderOccurrence);
            setCcaIndex(toCopy.ccaIndex);
            setCca(toCopy.ccaName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil
                    .isAnyNonNull(reminderName, reminderStartDate, reminderFrequency, reminderOccurrence, ccaName);
        }

        public void setReminderName(ReminderName reminderName) {
            this.reminderName = reminderName;
        }

        public Optional<ReminderName> getReminderName() {
            return Optional.ofNullable(reminderName);
        }

        public void setReminderStartDate(ReminderStartDate reminderStartDate) {
            this.reminderStartDate = reminderStartDate;
        }

        public Optional<ReminderStartDate> getReminderStartDate() {
            return Optional.ofNullable(reminderStartDate);
        }

        public void setReminderFrequency(ReminderFrequency reminderFrequency) {
            this.reminderFrequency = reminderFrequency;
        }

        public Optional<ReminderFrequency> getReminderFrequency() {
            return Optional.ofNullable(reminderFrequency);
        }

        public void setReminderOccurrence(ReminderOccurrence reminderOccurrence) {
            this.reminderOccurrence = reminderOccurrence;
        }

        public Optional<ReminderOccurrence> getReminderOccurrence() {
            return Optional.ofNullable(reminderOccurrence);
        }

        /**
         * Sets {@code ccaIndex} to this object's {@code ccaIndex}.
         * A defensive copy of {@code ccaIndex} is used internally.
         */
        public void setCcaIndex(Index ccaIndex) {
            this.ccaIndex = ccaIndex;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Index> getCcaIndex() {
            return Optional.ofNullable(ccaIndex);
        }

        /**
         * Sets {@code cca} to this object's {@code cca}.
         * A defensive copy of {@code ccaIndex} is used internally.
         * This is done only when model is accessible.
         */
        public void setCca(String ccaName) {
            this.ccaName = ccaName;
        }

        public Optional<String> getCca() {
            return Optional.ofNullable(ccaName);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getReminderName().equals(e.getReminderName())
                    && getReminderStartDate().equals(e.getReminderStartDate())
                    && getReminderFrequency().equals(e.getReminderFrequency())
                    && getReminderOccurrence().equals(e.getReminderOccurrence())
                    && getCcaIndex().equals(e.getCcaIndex())
                    && getCca().equals(e.getCca());
        }
    }
}
