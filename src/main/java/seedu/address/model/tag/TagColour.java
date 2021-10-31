package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a TagColour in ePoch.
 * Guarantees: immutable; RGB values are valid as declared in {@link #areValidRgbValues(int, int, int)}
 */
public class TagColour {
    public static final String MESSAGE_CONSTRAINTS =
            "Colours should be in the form of 3 space-separated integers from 0 to 255";
    private static final int DEFAULT_COLOUR_RED = 73;
    private static final int DEFAULT_COLOUR_GREEN = 130;
    private static final int DEFAULT_COLOUR_BLUE = 150;
    private static final int DEFAULT_COLOUR_CCA_RED = 0;
    private static final int DEFAULT_COLOUR_CCA_GREEN = 0;
    private static final int DEFAULT_COLOUR_CCA_BLUE = 155;

    /** Default colour for new tags. */
    public static final TagColour DEFAULT_COLOUR = new TagColour(DEFAULT_COLOUR_RED,
            DEFAULT_COLOUR_GREEN, DEFAULT_COLOUR_BLUE);
    /** Default colour for CCA tags, can be changed. */
    public static final TagColour DEFAULT_CCA_COLOUR = new TagColour(DEFAULT_COLOUR_CCA_RED,
            DEFAULT_COLOUR_CCA_GREEN, DEFAULT_COLOUR_CCA_BLUE);

    public final int red;
    public final int green;
    public final int blue;

    /**
     * Constructs a {@code TagColour}.
     *
     * @param red An integer describing the red value of the colour, from 0 to 255 inclusive.
     * @param green An integer describing the green value of the colour, from 0 to 255 inclusive.
     * @param blue An integer describing the blue value of the colour, from 0 to 255 inclusive.
     */
    public TagColour(int red, int green, int blue) {
        requireAllNonNull(red, green, blue);
        assert (red >= 0 && red <= 255) : "Invalid red value";
        assert (green >= 0 && green <= 255) : "Invalid green value";
        assert (blue >= 0 && blue <= 255) : "Invalid blue value";
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Returns true if the given values are valid RGB values.
     * RGB values are valid if they are between 0 and 255 inclusive.
     *
     * @param red The red value to check.
     * @param green The green value to check.
     * @param blue The blue value to check.
     * @return Whether or not the values are valid.
     */
    public static boolean areValidRgbValues(int red, int green, int blue) {
        return (red >= 0 && red <= 255) && (green >= 0 && green <= 255) && (blue >= 0 && blue <= 255);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColour // instanceof handles nulls
                && red == ((TagColour) other).red
                && green == ((TagColour) other).green
                && blue == ((TagColour) other).blue); // state check
    }

    @Override
    public int hashCode() {
        return red + green + blue;
    }

    /**
     * Format as text for viewing.
     */
    public String toString() {
        return "[RGB: " + red + ", " + green + ", " + blue + ']';
    }
}
