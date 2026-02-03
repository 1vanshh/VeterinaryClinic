package domain;

public enum AppointmentStatus {

    PLANNED("planned"),
    CANCELLED("cancelled"),
    DONE("done"),
    NO_SHOW("no_show");

    private final String dbValue;

    AppointmentStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String dbValue() {
        return dbValue;
    }

    public static AppointmentStatus fromDb(String value) {
        if (value == null) return null;

        for (AppointmentStatus as : values()) {
            if (as.dbValue.equalsIgnoreCase(value)) {
                return as;
            }
        }

        throw new IllegalArgumentException("Unknown appointment status from DB: " + value);
    }
}
