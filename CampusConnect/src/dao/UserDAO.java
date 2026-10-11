package dao;

import model.User;
import util.DBConnection;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    // =====================================================
    // REGISTER USER WITH PROFILE
    // Creates a PENDING user and their student/alumni profile.
    // Uses a transaction so both inserts succeed together.
    // =====================================================

    public boolean registerUserWithProfile(
            String name,
            String email,
            String passwordHash,
            String role,
            String collegeId,
            String department,
            int graduationYear) {

        if (name == null || email == null
                || passwordHash == null || role == null) {
            return false;
        }

        name = name.trim();
        email = email.trim().toLowerCase();
        role = role.trim().toUpperCase();

        if (name.isEmpty() || email.isEmpty()
                || passwordHash.isEmpty()) {
            return false;
        }

        if (!role.equals("STUDENT") && !role.equals("ALUMNI")) {
            return false;
        }

        String userSql =
                "INSERT INTO users "
                + "(name, email, password_hash, role, status) "
                + "VALUES (?, ?, ?, ?, 'PENDING')";

        String studentSql =
                "INSERT INTO student_profiles "
                + "(user_id, department, college_id_number) "
                + "VALUES (?, ?, ?)";

        String alumniSql =
                "INSERT INTO alumni_profiles "
                + "(user_id, graduation_year, department, "
                + "college_id_number, verification_status) "
                + "VALUES (?, ?, ?, ?, 'PENDING')";

        try (Connection con = DBConnection.getConnection()) {

            boolean originalAutoCommit = con.getAutoCommit();

            try {
                con.setAutoCommit(false);

                int userId;

                // Insert the main user account.
                try (PreparedStatement ps = con.prepareStatement(
                        userSql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, passwordHash);
                    ps.setString(4, role);

                    int rows = ps.executeUpdate();

                    if (rows != 1) {
                        con.rollback();
                        return false;
                    }

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            con.rollback();
                            return false;
                        }

                        userId = keys.getInt(1);
                    }
                }

                // Insert the appropriate profile.
                if ("STUDENT".equals(role)) {

                    try (PreparedStatement ps =
                            con.prepareStatement(studentSql)) {

                        ps.setInt(1, userId);
                        ps.setString(2, department);
                        ps.setString(3, collegeId);

                        ps.executeUpdate();
                    }

                } else if ("ALUMNI".equals(role)) {

                    try (PreparedStatement ps =
                            con.prepareStatement(alumniSql)) {

                        ps.setInt(1, userId);

                        if (graduationYear > 0) {
                            ps.setInt(2, graduationYear);
                        } else {
                            ps.setNull(2, java.sql.Types.INTEGER);
                        }

                        ps.setString(3, department);
                        ps.setString(4, collegeId);

                        ps.executeUpdate();
                    }
                }

                con.commit();
                return true;

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    System.err.println(
                            "Rollback failed: "
                            + rollbackError.getMessage());
                }

                System.err.println(
                        "Registration failed: " + e.getMessage());

                return false;

            } finally {
                try {
                    con.setAutoCommit(originalAutoCommit);
                } catch (SQLException e) {
                    System.err.println(
                            "Could not restore auto-commit: "
                            + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Database connection failed during registration: "
                    + e.getMessage());
            return false;
        }
    }

    // =====================================================
    // REGISTER USER WITHOUT PROFILE
    // Retained for compatibility with existing code.
    // =====================================================

    public boolean registerUser(
            String name,
            String email,
            String passwordHash,
            String role) {

        if (name == null || email == null
                || passwordHash == null || role == null) {
            return false;
        }

        name = name.trim();
        email = email.trim().toLowerCase();
        role = role.trim().toUpperCase();

        if (name.isEmpty() || email.isEmpty()
                || passwordHash.isEmpty()) {
            return false;
        }

        if (!role.equals("STUDENT") && !role.equals("ALUMNI")) {
            return false;
        }

        String sql =
                "INSERT INTO users "
                + "(name, email, password_hash, role, status) "
                + "VALUES (?, ?, ?, ?, 'PENDING')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, passwordHash);
            ps.setString(4, role);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(
                    "Error registering user: " + e.getMessage());
            return false;
        }
    }

    // =====================================================
    // GET USER BY EMAIL
    // =====================================================

    public User getUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        String sql =
                "SELECT user_id, name, email, password_hash, "
                + "role, status "
                + "FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error retrieving user: " + e.getMessage());
        }

        return null;
    }

    // =====================================================
    // LOGIN
    // Only ACTIVE accounts can log in.
    // =====================================================

    public User login(String email, String password)
            throws Exception {

        if (email == null || password == null
                || email.isBlank() || password.isEmpty()) {
            return null;
        }

        char[] passwordChars = password.toCharArray();

        String sql =
                "SELECT user_id, name, email, password_hash, "
                + "role, status "
                + "FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                String status = rs.getString("status");
                String storedHash = rs.getString("password_hash");

                // Pending or blocked users cannot log in.
                if (!"ACTIVE".equalsIgnoreCase(status)) {
                    return null;
                }

                // Verify the password using PasswordUtil.
                if (!PasswordUtil.verifyPassword(
                        passwordChars, storedHash)) {
                    return null;
                }

                return mapUser(rs);
            }

        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    // =====================================================
    // MAP RESULT SET TO USER OBJECT
    // =====================================================

    private User mapUser(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("role"),
                rs.getString("status")
        );
    }

    // =====================================================
    // CHECK WHETHER EMAIL EXISTS
    // =====================================================

    public boolean emailExists(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        String sql =
                "SELECT 1 FROM users WHERE email = ? LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error checking email: " + e.getMessage());

            // Fail safely when the database cannot be checked.
            return true;
        }
    }

    // =====================================================
    // GET ALL USERS FOR ADMIN DASHBOARD
    // =====================================================

    public List<Map<String, Object>> getAllUsers() {

        List<Map<String, Object>> users = new ArrayList<>();

        String sql =
                "SELECT user_id, name, email, role, status "
                + "FROM users ORDER BY user_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Map<String, Object> user =
                        new LinkedHashMap<>();

                user.put("user_id", rs.getInt("user_id"));
                user.put("name", rs.getString("name"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                user.put("status", rs.getString("status"));

                // Kept for compatibility with existing UI code.
                user.put("created_at", null);

                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error retrieving users: " + e.getMessage());
        }

        return users;
    }

    // =====================================================
    // UPDATE USER ACCOUNT STATUS
    // PENDING = waiting for verification
    // ACTIVE  = approved; can log in
    // BLOCKED = rejected or disabled
    // =====================================================

    public boolean updateUserStatus(
            int userId,
            String status) {

        if (userId <= 0 || status == null) {
            return false;
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!List.of("PENDING", "ACTIVE", "BLOCKED")
                .contains(normalizedStatus)) {
            return false;
        }

        String sql =
                "UPDATE users SET status = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizedStatus);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println(
                    "Error updating user status: " + e.getMessage());
            return false;
        }
    }

    // =====================================================
    // GET USERS BY ROLE AND STATUS
    // Used by admin verification screens.
    // =====================================================

    public List<User> getUsersByRoleAndStatus(
            String role,
            String status) {

        List<User> users = new ArrayList<>();

        if (role == null || status == null) {
            return users;
        }

        String normalizedRole = role.trim().toUpperCase();
        String normalizedStatus = status.trim().toUpperCase();

        if (!List.of("STUDENT", "ALUMNI", "ADMIN")
                .contains(normalizedRole)) {
            return users;
        }

        if (!List.of("PENDING", "ACTIVE", "BLOCKED")
                .contains(normalizedStatus)) {
            return users;
        }

        String sql =
                "SELECT user_id, name, email, password_hash, "
                + "role, status FROM users "
                + "WHERE role = ? AND status = ? "
                + "ORDER BY user_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, normalizedRole);
            ps.setString(2, normalizedStatus);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error retrieving filtered users: "
                    + e.getMessage());
        }

        return users;
    }
}