package seedu.address.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CCAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing CCA in the address book.
 */
public class CcaEditCommand extends Command {

    public static final String COMMAND_WORD = "editc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the CCA identified "
            + "by the index number used in the displayed CCA list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer less than 1,000,000,000) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "New Name "
            + PREFIX_TAG + "newTag";

    public static final String MESSAGE_EDIT_CCA_SUCCESS = "Edited CCA: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CCA = "This CCA already exists in ePoch.";

    private final Index index;
    private final EditCcaDescriptor editCcaDescriptor;

    /**
     * @param index of the CCA in the filtered CCA list to edit
     * @param editCcaDescriptor details to edit the person with
     */
    public CcaEditCommand(Index index, EditCcaDescriptor editCcaDescriptor) {
        requireNonNull(index);
        requireNonNull(editCcaDescriptor);

        this.index = index;
        this.editCcaDescriptor = new EditCcaDescriptor(editCcaDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cca> lastShownList = model.getFilteredCcaList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        Cca ccaToEdit = lastShownList.get(index.getZeroBased());
        Cca editedCca = createEditedCca(ccaToEdit, editCcaDescriptor);

        if (!ccaToEdit.isSameCca(editedCca) && model.hasCca(editedCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        ObservableList<Reminder> reminderList = model.getAddressBook().getReminderList();
        for (int i = 0; i < reminderList.size(); i++) {
            Reminder reminder = reminderList.get(i);
            if (reminder.getCcaName().equals(ccaToEdit.getName().fullName)) {
                reminder.setCcaName(editedCca.getName().fullName);
            }
            model.setReminder(reminder, reminder);
        }
        model.setCca(ccaToEdit, editedCca);
        editedCca.updateCcaNameForReminders();

        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);

        return new CommandResult(String.format(MESSAGE_EDIT_CCA_SUCCESS, editedCca));
    }

    /**
     * Creates and returns a {@code Cca} with the details of {@code ccaToEdit}
     * edited with {@code editCcaDescriptor}.
     */
    private static Cca createEditedCca(Cca ccaToEdit, EditCcaDescriptor editCcaDescriptor) {
        assert ccaToEdit != null;

        CcaName updatedName = editCcaDescriptor.getName().orElse(ccaToEdit.getName());
        Set<Person> copyOfmembers = ccaToEdit.getMembers();
        Set<Reminder> copyOfReminders = ccaToEdit.getReminders();
        Set<Tag> updatedTags = editCcaDescriptor.getTags().orElse(ccaToEdit.getTags());

        return new Cca(updatedName, copyOfmembers, copyOfReminders, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CcaEditCommand)) {
            return false;
        }

        // state check
        CcaEditCommand e = (CcaEditCommand) other;
        return index.equals(e.index)
                && editCcaDescriptor.equals(e.editCcaDescriptor);
    }

    /**
     * Stores the details to edit the CCA with. Each non-empty field value will replace the
     * corresponding field value of the CCA.
     */
    public static class EditCcaDescriptor {
        private CcaName name;
        private Set<Tag> tags;

        public EditCcaDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCcaDescriptor(EditCcaDescriptor toCopy) {
            setName(toCopy.name);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags);
        }

        public void setName(CcaName name) {
            this.name = name;
        }

        public Optional<CcaName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCcaDescriptor)) {
                return false;
            }

            // state check
            EditCcaDescriptor e = (EditCcaDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
