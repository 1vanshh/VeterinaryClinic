package domain;

public enum Role {
    ADMIN("admin"),
    OWNER("owner"),
    DOCTOR("doctor");

    private final String dbValue;

    Role(String dbValue) {
        this.dbValue = dbValue;
    }

    public String dbValue() {
        return dbValue;
    }

    public static Role fromDb(String value) {
        if (value == null) return null;

        for (Role r : values()) {
            if (r.dbValue.equalsIgnoreCase(value)) {
                return r;
            }
        }

        throw new IllegalArgumentException("Unknown role from DB: " + value);
    }
}
