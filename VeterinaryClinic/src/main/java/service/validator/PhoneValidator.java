package service.validator;

public class PhoneValidator {

    public static String validateAndNormalize(String rawPhone) {
        if (rawPhone == null) return "";

        String normalized = rawPhone.trim().replaceAll("[^\\d+]", "");

        String digits = normalized.startsWith("+")
                ? normalized.substring(1)
                : normalized;

        if (!digits.matches("\\d{10,15}")) {
            return null;
        }

        return digits;
    }

    public static boolean isValid(String rawPhone) {
        return validateAndNormalize(rawPhone) != null;
    }
}
