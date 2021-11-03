package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.cca.CcaEditCommand.EditCcaDescriptor;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCcaDescriptor objects.
 */
public class EditCcaDescriptorBuilder {

    private EditCcaDescriptor descriptor;

    public EditCcaDescriptorBuilder() {
        descriptor = new EditCcaDescriptor();
    }

    public EditCcaDescriptorBuilder(EditCcaDescriptor descriptor) {
        this.descriptor = new EditCcaDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCcaDescriptor} with fields containing {@code cca}'s details
     */
    public EditCcaDescriptorBuilder(Cca cca) {
        descriptor = new EditCcaDescriptor();
        descriptor.setName(cca.getName());
        descriptor.setTags(cca.getTags());
    }

    /**
     * Sets the {@code CcaName} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withName(String name) {
        descriptor.setName(new CcaName(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCcaDescriptor}
     * that we are building.
     */
    public EditCcaDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCcaDescriptor build() {
        return descriptor;
    }
}
