
package util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ValidationUtil() {
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return isNotEmpty(email)
                && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null
                && password.length() >= 8;
    }

    public static boolean isValidName(String name) {
        return isNotEmpty(name)
                && name.trim().length() >= 2;
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isValidUrl(String url) {
        if (!isNotEmpty(url)) {
            return false;
        }

        try {
            java.net.URI uri = java.net.URI.create(url.trim());

            return ("https".equalsIgnoreCase(uri.getScheme())
                    || "http".equalsIgnoreCase(uri.getScheme()))
                    && uri.getHost() != null;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
