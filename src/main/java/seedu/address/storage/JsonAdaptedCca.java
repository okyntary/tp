package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Cca}.
 */
public class JsonAdaptedCca {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Cca's %s field is missing!";

    private final String name;
    private final Set<JsonAdaptedPerson> members = new HashSet<>();
    private final Set<JsonAdaptedReminder> reminders = new HashSet<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCca} with the given CCA details.
     */
    @JsonCreator
    public JsonAdaptedCca(@JsonProperty("name") String name,
                          @JsonProperty("members")Set<JsonAdaptedPerson> members,
                          @JsonProperty("reminders")Set<JsonAdaptedReminder> reminders,
                          @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        if (members != null) {
            this.members.addAll(members);
        }
        if (reminders != null) {
            this.reminders.addAll(reminders);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Cca} into this class for Jackson use.
     */
    public JsonAdaptedCca(Cca source) {
        name = source.getName().fullName;
        members.addAll(source.getMembers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toSet()));
        reminders.addAll(source.getReminders().stream()
                .map(JsonAdaptedReminder::new)
                .collect(Collectors.toSet()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Cca} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Cca toModelType() throws IllegalValueException {
        final List<Person> personList = new ArrayList<>();
        final List<Reminder> reminderList = new ArrayList<>();

        for (JsonAdaptedPerson member : members) {
            personList.add(member.toModelType());
        }
        for (JsonAdaptedReminder reminder: reminders) {
            reminderList.add(reminder.toModelType());
        }
        final List<Tag> ccaTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            ccaTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, CcaName.class.getSimpleName()));
        }
        if (!CcaName.isValidCcaName(name)) {
            throw new IllegalValueException(CcaName.MESSAGE_CONSTRAINTS);
        }
        final CcaName modelName = new CcaName(name);
        final Set<Person> members = new HashSet<>(personList);
        final Set<Reminder> reminders = new HashSet<>(reminderList);
        final Set<Tag> tags = new HashSet<>(ccaTags);

        return new Cca(modelName, members, reminders, tags);
    }
}
