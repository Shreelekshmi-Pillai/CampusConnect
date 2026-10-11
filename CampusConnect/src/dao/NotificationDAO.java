
package dao;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public boolean addNotification(int userId, String message)
            throws SQLException {

        String sql =
            "INSERT INTO notifications (user_id, message) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, message);

            return ps.executeUpdate() == 1;
        }
    }

    public List<String> getNotifications(int userId)
            throws SQLException {

        List<String> notifications = new ArrayList<>();

        String sql = """
            SELECT message
            FROM notifications
            WHERE user_id = ?
            ORDER BY created_at DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notifications.add(rs.getString("message"));
                }
            }
        }

        return notifications;
    }

    public boolean markAsRead(int notificationId, int userId)
            throws SQLException {

        String sql = """
            UPDATE notifications
            SET is_read = TRUE
            WHERE notification_id = ? AND user_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        }
    }

	public void createNotification(int alumniId, String string) {
		// TODO Auto-generated method stub
		
	}
}
