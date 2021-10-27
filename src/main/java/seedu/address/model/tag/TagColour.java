package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagColour {
    public static final String MESSAGE_CONSTRAINTS =
            "Colours should be in the form of 3 space-separated integers from 0 to 255";
    public static final TagColour DEFAULT_COLOUR = new TagColour(255, 0, 0);
    // 73, 130, 150
    // swap out for public static final

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
        // asserts
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
