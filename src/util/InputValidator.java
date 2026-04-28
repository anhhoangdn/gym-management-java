package util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{9,11}$");
        private static final Pattern INT_PATTERN =
            Pattern.compile("^-?\\d+$");
        private static final Pattern DOUBLE_PATTERN =
            Pattern.compile("^-?\\d+(\\.\\d+)?$");

    public static boolean validateString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean validateInt(String input) {
        return validateString(input) && INT_PATTERN.matcher(input.trim()).matches();
    }

    public static boolean validateDouble(String input) {
        return validateString(input) && DOUBLE_PATTERN.matcher(input.trim()).matches();
    }

    public static boolean validateEmail(String email) {
        return validateString(email) && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return validateString(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }
}
