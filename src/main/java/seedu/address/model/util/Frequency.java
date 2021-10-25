package seedu.address.model.util;

public enum Frequency {
    ONE_OFF, DAY, WEEK, MONTH, YEAR;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase() + "(s)";
    }

    public static Frequency getFrequency(String frequencyStr) {
        Frequency frequency;
        switch (frequencyStr) {
        case "d":
            frequency = Frequency.DAY;
            break;
        case "w":
            frequency = Frequency.WEEK;
            break;
        case "m":
            frequency = Frequency.MONTH;
            break;
        case "y":
            frequency = Frequency.YEAR;
            break;
        default:
            frequency = Frequency.ONE_OFF;
        }
        return frequency;
    }
}
