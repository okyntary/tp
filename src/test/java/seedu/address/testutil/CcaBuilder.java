package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CCA objects.
 */
public class CcaBuilder {

    public static final String DEFAULT_NAME = "NUSSO";

    // Identity fields
    private CcaName name;
    // Data fields
    private Set<Person> members = new HashSet<>();
    private Set<Reminder> reminders = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();

    /**
     * Creates a {@code Cca} with the default details.
     */
    public CcaBuilder() {
        name = new CcaName(DEFAULT_NAME);
    }

    /**
     * Initializes the CcaBuilder with the data of {@code CcaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        name = ccaToCopy.getName();
    }

    /**
     * Sets the {@code CcaName} of the {@code Cca} that we are building.
     */
    public CcaBuilder withName(String name) {
        this.name = new CcaName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CcaBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Cca build() {
        return new Cca(name, tags);
    }
}
