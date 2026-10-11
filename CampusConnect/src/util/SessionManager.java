
package util;

import model.User;

public class SessionManager {

    private static User currentUser;

    private SessionManager() {
    }

    public static void setCurrentUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null.");
        }

        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static String getCurrentRole() {
        if (currentUser == null) {
            return null;
        }

        return currentUser.getRole();
    }

    public static boolean hasRole(String role) {
        return currentUser != null
                && currentUser.getRole() != null
                && currentUser.getRole().equalsIgnoreCase(role);
    }

    public static void logout() {
        currentUser = null;
    }
}
