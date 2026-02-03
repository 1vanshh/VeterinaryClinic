package domain;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown");

    private final String dbValue;

    Gender(String dbValue) {
        this.dbValue = dbValue;
    }

    public String dbValue() {
        return dbValue;
    }

    public static Gender fromDb(String value) {
        if (value == null) return null;

        for (Gender g : values()) {
            if (g.dbValue.equalsIgnoreCase(value)) {
                return g;
            }
        }

        throw new IllegalArgumentException("Unknown gender from DB: " + value);
    }
}
