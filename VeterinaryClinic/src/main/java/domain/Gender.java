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
        return switch (value.toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> UNKNOWN;
        };
    }
}
