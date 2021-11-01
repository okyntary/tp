package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Hashtable;

/**
 * Represents a Tag in ePoch.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static final String CCA_COLOUR_KEY = " "; // works because no tag can be an blank (empty) string
    private static final Hashtable<String, TagColour> tagColours = new Hashtable<>() {
        { put(CCA_COLOUR_KEY, TagColour.DEFAULT_CCA_COLOUR); }
    };

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        if (!Tag.tagColours.containsKey(this.tagName)) {
            Tag.tagColours.put(this.tagName, TagColour.DEFAULT_COLOUR);
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Changes the tag colour of all tags with that tag name.
     *
     * @param tagName The name of the tag to assign the new colour to.
     * @param colour The colour to assign to the tag.
     */
    public static void setTagColour(String tagName, TagColour colour) {
        Tag.tagColours.put(tagName, colour);
    }

    public static boolean hasTagBeenCreated(String tagName) {
        return Tag.tagColours.containsKey(tagName);
    }

    public TagColour getTagColour() {
        return Tag.tagColours.get(this.tagName);
    }

    public static void setCcaTagColour(TagColour colour) {
        Tag.tagColours.put(CCA_COLOUR_KEY, colour);
    }

    public static TagColour getCcaTagColour() {
        return Tag.tagColours.get(CCA_COLOUR_KEY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
