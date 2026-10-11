
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.regex.Pattern;

import util.DBConnection;
import util.PasswordUtil;

public class RegisterFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color GREEN = new Color(24, 126, 77);
    private static final Color DARK = new Color(35, 45, 40);
    private static final Color MUTED = new Color(120, 130, 124);
    private static final Color BG = new Color(246, 248, 247);

    private JTextField nameField;
    private JTextField emailField;
    private JTextField collegeIdField;
    private JTextField departmentField;
    private JTextField graduationYearField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JComboBox<String> roleComboBox;
    private JButton registerButton;

    public RegisterFrame() {
        setTitle("Campus Connect - Create Account");
        setSize(600, 760);
        setMinimumSize(new Dimension(520, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(new EmptyBorder(24, 35, 24, 35));

        JLabel logo = new JLabel("Campus Connect");
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setForeground(DARK);

        JLabel heading = new JLabel("Create your account");
        heading.setFont(new Font("SansSerif", Font.BOLD, 23));
        heading.setForeground(DARK);

        JLabel subtitle = new JLabel(
            "Join your campus community and explore opportunities."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        header.add(logo);
        header.add(Box.createVerticalStrut(18));
        header.add(heading);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(22, 24, 22, 24));

        roleComboBox = new JComboBox<>(
            new String[] { "Student", "Alumni" }
        );
        roleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 38)
        );

        nameField = createTextField();
        emailField = createTextField();
        collegeIdField = createTextField();
        departmentField = createTextField();
        graduationYearField = createTextField();

        passwordField = createPasswordField();
        confirmPasswordField = createPasswordField();

        addField(form, "I am registering as", roleComboBox);
        addField(form, "Full name", nameField);
        addField(form, "College email address", emailField);
        addField(form, "College ID / Register number", collegeIdField);
        addField(form, "Department", departmentField);
        addField(form, "Graduation year (Alumni)", graduationYearField);
        addField(form, "Password (minimum 8 characters)", passwordField);
        addField(form, "Confirm password", confirmPasswordField);

        JLabel note = new JLabel(
            "<html><div style='width:390px'>" +
            "Your account will remain pending until an administrator " +
            "verifies your college association. You can log in after " +
            "your account has been approved.</div></html>"
        );
        note.setFont(new Font("SansSerif", Font.PLAIN, 11));
        note.setForeground(MUTED);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);

        registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(GREEN);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setOpaque(true);
        registerButton.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 44)
        );

        registerButton.addActionListener(e -> registerUser());

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backButton.setForeground(GREEN);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        form.add(Box.createVerticalStrut(4));
        form.add(note);
        form.add(Box.createVerticalStrut(16));
        form.add(registerButton);
        form.add(Box.createVerticalStrut(8));
        form.add(backButton);

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        roleComboBox.addActionListener(e -> updateRoleFields());

        updateRoleFields();
    }

    private JTextField createTextField() {

        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 230, 226)
                ),
                new EmptyBorder(8, 10, 8, 10)
            )
        );

        field.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 38)
        );

        return field;
    }

    private JPasswordField createPasswordField() {

        JPasswordField field = new JPasswordField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 230, 226)
                ),
                new EmptyBorder(8, 10, 8, 10)
            )
        );

        field.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 38)
        );

        return field;
    }

    private void addField(
        JPanel panel,
        String labelText,
        JComponent component
    ) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(component);
        panel.add(Box.createVerticalStrut(12));
    }

    private void updateRoleFields() {

        boolean isAlumni =
            "Alumni".equals(roleComboBox.getSelectedItem());

        graduationYearField.setEnabled(isAlumni);

        graduationYearField.setBackground(
            isAlumni ? Color.WHITE : new Color(240, 242, 240)
        );

        if (!isAlumni) {
            graduationYearField.setText("");
        }
    }

    private void registerUser() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim().toLowerCase();
        String collegeId = collegeIdField.getText().trim();
        String department = departmentField.getText().trim();

        String selectedRole =
            (String) roleComboBox.getSelectedItem();

        String role = "Alumni".equals(selectedRole)
            ? "ALUMNI"
            : "STUDENT";

        char[] passwordChars = passwordField.getPassword();
        char[] confirmChars = confirmPasswordField.getPassword();

        try {

            // 1. Validate required fields.

            if (name.isEmpty()
                    || email.isEmpty()
                    || collegeId.isEmpty()
                    || department.isEmpty()
                    || passwordChars.length == 0
                    || confirmChars.length == 0) {

                showWarning(
                    "Please fill in all required fields."
                );
                return;
            }

            if (!Pattern.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                    email)) {

                showWarning("Please enter a valid email address.");
                return;
            }

            if (name.length() > 100 || email.length() > 150
                    || collegeId.length() > 50
                    || department.length() > 100) {

                showWarning(
                    "One or more fields exceed the allowed length."
                );
                return;
            }

            if (passwordChars.length < 8) {
                showWarning(
                    "Password must contain at least 8 characters."
                );
                return;
            }

            if (!Arrays.equals(passwordChars, confirmChars)) {
                showWarning("Passwords do not match.");
                return;
            }

            // 2. Validate the alumni graduation year.

            Integer graduationYear = null;

            if ("ALUMNI".equals(role)) {

                String yearText =
                    graduationYearField.getText().trim();

                if (yearText.isEmpty()) {
                    showWarning(
                        "Please enter your graduation year."
                    );
                    return;
                }

                try {
                    graduationYear = Integer.parseInt(yearText);

                    if (graduationYear < 1950
                            || graduationYear > 2100) {
                        showWarning(
                            "Please enter a valid graduation year."
                        );
                        return;
                    }

                } catch (NumberFormatException ex) {
                    showWarning(
                        "Graduation year must be a valid number."
                    );
                    return;
                }
            }

            // 3. Hash the password using the project's PasswordUtil.

            String passwordHash =
                PasswordUtil.hashPassword(passwordChars);

            // 4. Insert the user and corresponding profile together.

            registerButton.setEnabled(false);

            try {
                createAccount(
                    name,
                    email,
                    passwordHash,
                    role,
                    collegeId,
                    department,
                    graduationYear
                );

                JOptionPane.showMessageDialog(
                    this,
                    "Registration submitted successfully!\n\n"
                    + "Your account is pending admin verification.\n"
                    + "You can log in after your account is approved.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );

                new LoginFrame().setVisible(true);
                dispose();

            } catch (SQLException ex) {

                if (ex.getErrorCode() == 1062
                        || "23000".equals(ex.getSQLState())) {

                    showWarning(
                        "This email or registration number may "
                        + "already be registered."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "Registration failed.\n\n"
                        + "Please check your database connection "
                        + "and table structure.\n\n"
                        + "Details: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                    );

                    ex.printStackTrace();
                }

            } finally {
                registerButton.setEnabled(true);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                this,
                "Could not process registration.\n"
                + "Details: " + ex.getMessage(),
                "Registration Error",
                JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();

        } finally {
            Arrays.fill(passwordChars, '\0');
            Arrays.fill(confirmChars, '\0');

            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }

    private void createAccount(
        String name,
        String email,
        String passwordHash,
        String role,
        String collegeId,
        String department,
        Integer graduationYear
    ) throws SQLException {

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
            + "(user_id, graduation_year, verification_status, "
            + "college_id_number, registration_number, department) "
            + "VALUES (?, ?, 'PENDING', ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int userId;

                // Insert into users first.

                try (PreparedStatement userStatement =
                        connection.prepareStatement(
                            userSql,
                            Statement.RETURN_GENERATED_KEYS
                        )) {

                    userStatement.setString(1, name);
                    userStatement.setString(2, email);
                    userStatement.setString(3, passwordHash);
                    userStatement.setString(4, role);

                    int affectedRows =
                        userStatement.executeUpdate();

                    if (affectedRows != 1) {
                        throw new SQLException(
                            "User account could not be created."
                        );
                    }

                    try (ResultSet keys =
                            userStatement.getGeneratedKeys()) {

                        if (!keys.next()) {
                            throw new SQLException(
                                "Could not retrieve the new user ID."
                            );
                        }

                        userId = keys.getInt(1);
                    }
                }

                // Insert the matching profile.

                if ("STUDENT".equals(role)) {

                    try (PreparedStatement profileStatement =
                            connection.prepareStatement(studentSql)) {

                        profileStatement.setInt(1, userId);
                        profileStatement.setString(2, department);
                        profileStatement.setString(3, collegeId);

                        profileStatement.executeUpdate();
                    }

                } else {

                    try (PreparedStatement profileStatement =
                            connection.prepareStatement(alumniSql)) {

                        profileStatement.setInt(1, userId);
                        profileStatement.setInt(2, graduationYear);
                        profileStatement.setString(3, collegeId);
                        profileStatement.setString(4, collegeId);
                        profileStatement.setString(5, department);

                        profileStatement.executeUpdate();
                    }
                }

                // Commit only if both inserts succeed.

                connection.commit();

            } catch (SQLException ex) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    ex.addSuppressed(rollbackException);
                }

                throw ex;

            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    // Connection closure will release this connection.
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showWarning(String message) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "Registration",
            JOptionPane.WARNING_MESSAGE
        );
    }
}
