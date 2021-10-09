package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;

/**
 * Jackson-friendly version of {@link Cca}.
 */
public class JsonAdaptedCca {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Cca's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedCca} with the given cca details.
     */
    @JsonCreator
    public JsonAdaptedCca(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Cca} into this class for Jackson use.
     */
    public JsonAdaptedCca(Cca source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Cca} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Cca toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, CcaName.class.getSimpleName()));
        }
        if (!CcaName.isValidName(name)) {
            throw new IllegalValueException(CcaName.MESSAGE_CONSTRAINTS);
        }
        final CcaName modelName = new CcaName(name);

        return new Cca(modelName);
    }

}
