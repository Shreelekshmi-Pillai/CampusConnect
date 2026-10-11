
package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlumniDAO {

    /*
     * Each String[] contains:
     * [0] user_id
     * [1] name
     * [2] email
     * [3] graduation_year
     * [4] company
     * [5] designation
     * [6] industry
     * [7] expertise
     */

    // Get all verified alumni.
    public List<String[]> getAllAlumni() {
        return query("", false);
    }

    // Search verified alumni by name, company, designation, industry or expertise.
    public List<String[]> searchAlumni(String keyword) {
        return query(keyword, true);
    }

    private List<String[]> query(String keyword, boolean search) {

        List<String[]> alumniList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT u.user_id, u.name, u.email, " +
            "a.graduation_year, a.company, a.designation, " +
            "a.industry, a.expertise " +
            "FROM users u " +
            "INNER JOIN alumni_profiles a ON u.user_id = a.user_id " +
            "WHERE u.role = 'ALUMNI' " +
            "AND u.status = 'ACTIVE' " +
            "AND a.verification_status = 'VERIFIED' "
        );

        if (search) {
            sql.append(
                "AND (" +
                "u.name LIKE ? OR " +
                "a.company LIKE ? OR " +
                "a.designation LIKE ? OR " +
                "a.industry LIKE ? OR " +
                "a.expertise LIKE ?" +
                ") "
            );
        }

        sql.append("ORDER BY u.name ASC");

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString())
        ) {

            if (search) {
                String searchTerm = "%" +
                    (keyword == null ? "" : keyword.trim()) + "%";

                for (int i = 1; i <= 5; i++) {
                    ps.setString(i, searchTerm);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String[] alumni = {
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("graduation_year"),
                        rs.getString("company"),
                        rs.getString("designation"),
                        rs.getString("industry"),
                        rs.getString("expertise")
                    };

                    alumniList.add(alumni);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving alumni: " + e.getMessage());
            e.printStackTrace();
        }

        return alumniList;
    }

    // Get alumni awaiting admin verification.
    public List<String[]> getPendingAlumni() {

        List<String[]> alumniList = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.name, u.email, " +
            "a.graduation_year, a.company, a.designation, " +
            "a.industry, a.expertise " +
            "FROM users u " +
            "INNER JOIN alumni_profiles a ON u.user_id = a.user_id " +
            "WHERE u.role = 'ALUMNI' " +
            "AND a.verification_status = 'PENDING' " +
            "ORDER BY u.user_id ASC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String[] alumni = {
                    String.valueOf(rs.getInt("user_id")),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("graduation_year"),
                    rs.getString("company"),
                    rs.getString("designation"),
                    rs.getString("industry"),
                    rs.getString("expertise")
                };

                alumniList.add(alumni);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving pending alumni: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return alumniList;
    }

    // Update an alumni's verification status.
    public boolean updateVerificationStatus(int userId, String status) {

        if (userId <= 0 || status == null) {
            return false;
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!Arrays.asList("PENDING", "VERIFIED", "REJECTED")
                .contains(normalizedStatus)) {
            return false;
        }

        String sql =
            "UPDATE alumni_profiles " +
            "SET verification_status = ? " +
            "WHERE user_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, normalizedStatus);
            ps.setInt(2, userId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 1) {
                // Keep the main user account status consistent with verification.
                String userStatus;

                if ("VERIFIED".equals(normalizedStatus)) {
                    userStatus = "ACTIVE";
                } else if ("REJECTED".equals(normalizedStatus)) {
                    userStatus = "BLOCKED";
                } else {
                    userStatus = "PENDING";
                }

                updateUserStatus(connection, userId, userStatus);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error updating alumni verification: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private void updateUserStatus(
            Connection connection, int userId, String status)
            throws SQLException {

        String sql =
            "UPDATE users SET status = ? " +
            "WHERE user_id = ? AND role = 'ALUMNI'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    // Save or update an alumni's professional profile.
    public boolean saveProfile(
            int userId,
            int graduationYear,
            String company,
            String designation,
            String industry,
            String expertise) {

        if (userId <= 0 || graduationYear < 1950 ||
                company == null || company.trim().isEmpty()) {
            return false;
        }

        String sql =
            "INSERT INTO alumni_profiles " +
            "(user_id, graduation_year, company, designation, " +
            "industry, expertise, verification_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, 'PENDING') " +
            "ON DUPLICATE KEY UPDATE " +
            "graduation_year = VALUES(graduation_year), " +
            "company = VALUES(company), " +
            "designation = VALUES(designation), " +
            "industry = VALUES(industry), " +
            "expertise = VALUES(expertise)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ps.setInt(2, graduationYear);
            ps.setString(3, company.trim());
            ps.setString(4, designation);
            ps.setString(5, industry);
            ps.setString(6, expertise);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving alumni profile: "
                    + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
