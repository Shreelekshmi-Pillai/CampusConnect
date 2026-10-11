
package ui;

import util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminVerificationFrame extends JFrame {

    private static final Color NAVY = new Color(24, 42, 58);
    private static final Color GREEN = new Color(36, 120, 86);
    private static final Color RED = new Color(190, 65, 65);
    private static final Color BACKGROUND = new Color(246, 248, 247);
    private static final Color MUTED = new Color(105, 120, 125);

    private JTable verificationTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public AdminVerificationFrame() {
        setTitle("Campus Connect | Registration Verification");
        setSize(1100, 650);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        loadPendingRegistrations();
    }

    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(28, 30, 28, 30));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(
            new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Registration Verification");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
            "Review student and alumni accounts before granting access."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(7));
        headingPanel.add(subtitle);

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, NAVY);
        refreshButton.addActionListener(
            e -> loadPendingRegistrations()
        );

        header.add(headingPanel, BorderLayout.CENTER);
        header.add(refreshButton, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // Pending count
        JPanel countPanel = new JPanel(new BorderLayout());
        countPanel.setBackground(Color.WHITE);
        countPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 231, 228)),
            new EmptyBorder(14, 18, 14, 18)
        ));

        countLabel = new JLabel("Pending registrations: 0");
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        countLabel.setForeground(NAVY);

        countPanel.add(countLabel, BorderLayout.WEST);

        // Table
        String[] columns = {
            "User ID",
            "Name",
            "Email",
            "Role",
            "Department",
            "College ID",
            "Graduation Year"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        verificationTable = new JTable(tableModel);
        verificationTable.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );
        verificationTable.setRowHeight(36);
        verificationTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
        verificationTable.setGridColor(new Color(232, 236, 233));
        verificationTable.setShowVerticalLines(false);
        verificationTable.setSelectionBackground(
            new Color(222, 240, 230)
        );
        verificationTable.setSelectionForeground(NAVY);
        verificationTable.getTableHeader().setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );
        verificationTable.getTableHeader().setBackground(
            new Color(237, 242, 239)
        );
        verificationTable.getTableHeader().setForeground(NAVY);
        verificationTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(verificationTable);
        scrollPane.setBorder(
            BorderFactory.createLineBorder(new Color(225, 231, 228))
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.setOpaque(false);
        centerPanel.add(countPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        root.add(centerPanel, BorderLayout.CENTER);

        // Bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setOpaque(false);

        JButton rejectButton = new JButton("Reject Registration");
        styleButton(rejectButton, RED);

        JButton approveButton = new JButton("Approve Registration");
        styleButton(approveButton, GREEN);

        rejectButton.addActionListener(e -> updateSelectedRegistration(false));
        approveButton.addActionListener(e -> updateSelectedRegistration(true));

        buttonPanel.add(rejectButton);
        buttonPanel.add(approveButton);

        root.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(new EmptyBorder(11, 17, 11, 17));
    }

    public void loadPendingRegistrations() {

        tableModel.setRowCount(0);

        String sql =
            "SELECT u.user_id, u.name, u.email, u.role, " +
            "COALESCE(s.department, a.department, '') AS department, " +
            "COALESCE(s.college_id_number, a.college_id_number, '') " +
            "AS college_id, " +
            "a.graduation_year " +
            "FROM users u " +
            "LEFT JOIN student_profiles s ON u.user_id = s.user_id " +
            "LEFT JOIN alumni_profiles a ON u.user_id = a.user_id " +
            "WHERE u.status = 'PENDING' " +
            "AND u.role IN ('STUDENT', 'ALUMNI') " +
            "ORDER BY u.user_id ASC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Object graduationYear = rs.getObject("graduation_year");

                tableModel.addRow(new Object[] {
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("department"),
                    rs.getString("college_id"),
                    graduationYear == null ? "-" : graduationYear
                });
            }

            countLabel.setText(
                "Pending registrations: " + tableModel.getRowCount()
            );

        } catch (SQLException e) {
            showDatabaseError("Could not load pending registrations.", e);
        }
    }

    private void updateSelectedRegistration(boolean approve) {

        int selectedRow = verificationTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a registration first.",
                "No Registration Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = verificationTable.convertRowIndexToModel(selectedRow);

        int userId = Integer.parseInt(
            tableModel.getValueAt(modelRow, 0).toString()
        );

        String name = tableModel.getValueAt(modelRow, 1).toString();
        String role = tableModel.getValueAt(modelRow, 3).toString();

        int choice = JOptionPane.showConfirmDialog(
            this,
            (approve ? "Approve" : "Reject") +
                " the registration of " + name + " (" + role + ")?",
            "Confirm Registration Decision",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // Update the main account status.
            String userSql =
                "UPDATE users SET status = ? " +
                "WHERE user_id = ? AND role = ? AND status = 'PENDING'";

            try (PreparedStatement ps =
                    connection.prepareStatement(userSql)) {

                ps.setString(1, approve ? "ACTIVE" : "BLOCKED");
                ps.setInt(2, userId);
                ps.setString(3, role);

                int updated = ps.executeUpdate();

                if (updated != 1) {
                    connection.rollback();

                    JOptionPane.showMessageDialog(
                        this,
                        "This registration is no longer pending. Refresh the table.",
                        "Registration Already Processed",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
            }

            // Alumni profiles have their own verification status.
            if ("ALUMNI".equalsIgnoreCase(role)) {

                String alumniSql =
                    "UPDATE alumni_profiles " +
                    "SET verification_status = ? " +
                    "WHERE user_id = ?";

                try (PreparedStatement ps =
                        connection.prepareStatement(alumniSql)) {

                    ps.setString(
                        1,
                        approve ? "VERIFIED" : "REJECTED"
                    );
                    ps.setInt(2, userId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                            "The alumni profile was not found."
                        );
                    }
                }
            }

            connection.commit();

            JOptionPane.showMessageDialog(
                this,
                name + "'s registration has been " +
                    (approve ? "approved." : "rejected."),
                "Registration Updated",
                JOptionPane.INFORMATION_MESSAGE
            );

            loadPendingRegistrations();

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            showDatabaseError("Could not update the registration.", e);

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(
                        "Error closing database connection: "
                            + e.getMessage()
                    );
                }
            }
        }
    }

    private void showDatabaseError(String message, SQLException e) {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
            this,
            message + "\n\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
