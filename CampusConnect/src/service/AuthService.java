package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;
import java.util.Arrays;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) throws Exception {
        if (email == null || password == null || email.isBlank() || password.isEmpty()) return null;
        return userDAO.login(email.trim().toLowerCase(), password);
    }

    /** Backward-compatible registration entry point. */
    public boolean register(String name, String email, String password, String role) {
        return register(name, email, password, role, null, null, null);
    }

    public boolean register(String name, String email, String password, String role,
                            String collegeId, String department, Integer graduationYear) {
        if (name == null || email == null || password == null || role == null) return false;
        String cleanName = name.trim();
        String cleanEmail = email.trim().toLowerCase();
        String cleanRole = role.trim().toUpperCase();
        if (cleanName.isEmpty() || !cleanEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                || password.length() < 8 || !(cleanRole.equals("STUDENT") || cleanRole.equals("ALUMNI"))) return false;
        if (cleanRole.equals("ALUMNI") && (graduationYear == null || graduationYear < 1950 || graduationYear > 2100)) return false;
        if (userDAO.emailExists(cleanEmail)) return false;
        char[] chars = password.toCharArray();
        try {
            String hash = PasswordUtil.hashPassword(chars);
            return userDAO.registerUserWithProfile(cleanName, cleanEmail, hash, cleanRole, collegeId, department, graduationYear);
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        } finally { Arrays.fill(chars, '\0'); }
    }
}
