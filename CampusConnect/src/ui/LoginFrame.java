package ui;

import dao.UserDAO;
import model.User;
import util.SessionManager;
import ui.StudentDashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class LoginFrame extends JFrame {

    private static final Color BG = new Color(246, 248, 247);
    private static final Color GREEN = new Color(24, 126, 77);
    private static final Color DARK = new Color(35, 45, 40);
    private static final Color MUTED = new Color(120, 130, 124);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(225, 230, 226);
    private static final Color RED = new Color(180, 55, 55);

    private final String[] roles = {
        "Student", "Alumni", "Admin"
    };

    private final String[] roleKeys = {
        "STUDENT", "ALUMNI", "ADMIN"
    };

    private final String[] roleDescriptions = {
        "Connect with alumni, explore mentorship and discover opportunities.",
        "Share your experience, mentor students and post career opportunities.",
        "Verify accounts, manage users and oversee Campus Connect."
    };

    private int selectedRole = 0;

    private JLabel roleHeading;
    private JLabel descriptionLabel;
    private JLabel welcomeHeading;
    private JLabel subtitleLabel;
    private JLabel statusLabel;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    private RoleSelector roleSelector;

    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Campus Connect | Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 730);
        setMinimumSize(new Dimension(950, 640));
        setLocationRelativeTo(null);

        buildUI();
        selectRole(0);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(25, 36, 25, 36));
        setContentPane(root);

        // ---------------- HEADER ----------------

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel brandPanel = new JPanel();
        brandPanel.setOpaque(false);
        brandPanel.setLayout(
                new BoxLayout(brandPanel, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("Campus Connect");
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setForeground(DARK);

        JLabel tagline = new JLabel(
                "Your campus. Your community. Your future.");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tagline.setForeground(MUTED);

        brandPanel.add(logo);
        brandPanel.add(Box.createVerticalStrut(4));
        brandPanel.add(tagline);

        JLabel secureLabel = new JLabel("  CONNECT  •  GROW  •  SUCCEED  ");
        secureLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        secureLabel.setForeground(GREEN);

        header.add(brandPanel, BorderLayout.WEST);
        header.add(secureLabel, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // ---------------- MAIN CONTENT ----------------

        JPanel content = new JPanel(new GridLayout(1, 2, 32, 0));
        content.setOpaque(false);

        content.add(createRolePanel());
        content.add(createLoginPanel());

        root.add(content, BorderLayout.CENTER);
    }

    // =====================================================
    // ROLE SELECTOR PANEL
    // =====================================================

    private JPanel createRolePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("A place to grow together.");
        heading.setFont(new Font("SansSerif", Font.BOLD, 25));
        heading.setForeground(DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subheading = new JLabel(
                "<html><div style='width:410px'>"
                + "Choose your role to explore a personalised "
                + "Campus Connect experience."
                + "</div></html>");
        subheading.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subheading.setForeground(MUTED);
        subheading.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subheading);

        roleSelector = new RoleSelector();
        roleSelector.setPreferredSize(new Dimension(470, 410));

        JLabel hint = new JLabel(
                "Click a bubble to switch your login role");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(MUTED);
        hint.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(roleSelector, BorderLayout.CENTER);
        centerPanel.add(hint, BorderLayout.SOUTH);

        panel.add(headingPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // LOGIN CARD
    // =====================================================

    private JPanel createLoginPanel() {
        RoundedPanel card = new RoundedPanel(24, WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 32, 26, 32));

        welcomeHeading = new JLabel("Welcome, Student!");
        welcomeHeading.setFont(
                new Font("SansSerif", Font.BOLD, 27));
        welcomeHeading.setForeground(DARK);
        welcomeHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        subtitleLabel = new JLabel(
                "Sign in to your student account.");
        subtitleLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(MUTED);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        roleHeading = new JLabel("Student Login");
        roleHeading.setFont(
                new Font("SansSerif", Font.BOLD, 16));
        roleHeading.setForeground(GREEN);
        roleHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        descriptionLabel = new JLabel();
        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = createFieldLabel("Email address");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField = new JTextField();
        styleTextField(emailField);
        emailField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = createFieldLabel("Password");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField();
        styleTextField(passwordField);
        passwordField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField.addActionListener(e -> handleLogin());
        emailField.addActionListener(e -> passwordField.requestFocusInWindow());

        loginButton = new JButton("Log In as Student");
        stylePrimaryButton(loginButton);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.addActionListener(e -> handleLogin());

        JLabel registerPrompt = new JLabel("New to Campus Connect?");
        registerPrompt.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        registerPrompt.setForeground(MUTED);
        registerPrompt.setAlignmentX(Component.LEFT_ALIGNMENT);

        registerButton = new JButton("Create an account");
        styleSecondaryButton(registerButton);
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.addActionListener(e -> openRegistration());

        statusLabel = new JLabel(" ");
        statusLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(MUTED);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel footer = new JLabel(
                "<html><div style='width:300px'>"
                + "New accounts require approval before access "
                + "is granted. Admin accounts cannot be created "
                + "through public registration."
                + "</div></html>");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(MUTED);
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(welcomeHeading);
        card.add(Box.createVerticalStrut(7));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(22));
        card.add(roleHeading);
        card.add(Box.createVerticalStrut(7));
        card.add(descriptionLabel);
        card.add(Box.createVerticalStrut(22));
        card.add(emailLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(emailField);
        card.add(Box.createVerticalStrut(16));
        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(20));
        card.add(loginButton);
        card.add(Box.createVerticalStrut(10));
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(registerPrompt);
        card.add(Box.createVerticalStrut(8));
        card.add(registerButton);
        card.add(Box.createVerticalStrut(14));
        card.add(footer);
        card.add(Box.createVerticalGlue());

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(card);

        return wrapper;
    }

    // =====================================================
    // FIELD AND BUTTON STYLING
    // =====================================================

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(DARK);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(DARK);
        field.setBackground(new Color(250, 251, 250));
        field.setCaretColor(GREEN);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 12, 10, 12)));
    }

    private void stylePrimaryButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(WHITE);
        button.setBackground(GREEN);
        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(13, 18, 13, 18));
        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 48));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(GREEN);
        button.setBackground(new Color(239, 247, 241));
        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    // =====================================================
    // ROLE SELECTION
    // =====================================================

    private void selectRole(int index) {
        selectedRole = index;

        roleHeading.setText(roles[index] + " Login");
        descriptionLabel.setText(
                "<html><div style='width:310px'>"
                + roleDescriptions[index]
                + "</div></html>");

        if (index == 0) {
            welcomeHeading.setText("Welcome, Student!");
            subtitleLabel.setText(
                    "Sign in to your student account.");
            loginButton.setText("Log In as Student");
            registerButton.setVisible(true);

        } else if (index == 1) {
            welcomeHeading.setText("Welcome, Alumni!");
            subtitleLabel.setText(
                    "Reconnect with your campus community.");
            loginButton.setText("Log In as Alumni");
            registerButton.setVisible(true);

        } else {
            welcomeHeading.setText("Administrator Access");
            subtitleLabel.setText(
                    "Sign in to manage Campus Connect.");
            loginButton.setText("Admin Log In");
            registerButton.setVisible(false);
        }

        statusLabel.setText(" ");
        statusLabel.setForeground(MUTED);

        revalidate();
        repaint();
    }

    // =====================================================
    // REGISTRATION
    // =====================================================

    private void openRegistration() {
        if (selectedRole == 2) {
            JOptionPane.showMessageDialog(
                    this,
                    "Admin accounts cannot be registered publicly.",
                    "Admin Access",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Support the registration class names used in different versions
        // of this project, without creating a compile-time dependency on one.
        String[] registrationClasses = {
            "ui.RegisterFrame",
            "ui.RegistrationFrame",
            "ui.RegisterPage"
        };

        JFrame registrationFrame = createFrameByReflection(
                registrationClasses, new Class<?>[0], new Object[0]);

        if (registrationFrame == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not find the registration screen.\n"
                    + "Create a class named RegisterFrame, "
                    + "RegistrationFrame, or RegisterPage in the ui package, "
                    + "and give it a public no-argument constructor.",
                    "Registration Screen Not Found",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        registrationFrame.setVisible(true);
        dispose();
    }

    // =====================================================
    // LOGIN AND DATABASE AUTHENTICATION
    // =====================================================

    private void handleLogin() {
        final String email = emailField.getText().trim();
        final String password =
                new String(passwordField.getPassword());
        final String requestedRole = roleKeys[selectedRole];

        if (email.isEmpty() || password.isEmpty()) {
            showStatus(
                    "Enter both your email address and password.",
                    RED);
            return;
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            showStatus("Enter a valid email address.", RED);
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Signing in...");
        showStatus("Checking your account...", MUTED);

        SwingWorker<User, Void> worker =
                new SwingWorker<User, Void>() {

            private String errorMessage =
                    "Invalid email or password.";

            @Override
            protected User doInBackground() throws Exception {
                User user = userDAO.login(email, password);

                if (user == null) {
                    // The DAO returns null for incorrect passwords
                    // and accounts that have not been approved.
                    User existing = userDAO.getUserByEmail(email);

                    if (existing != null) {
                        String status = existing.getStatus();

                        if ("PENDING".equalsIgnoreCase(status)) {
                            errorMessage =
                                    "Your account is awaiting admin approval.";
                        } else if ("BLOCKED".equalsIgnoreCase(status)) {
                            errorMessage =
                                    "Your account has been blocked. "
                                    + "Please contact the administrator.";
                        }
                    }

                    return null;
                }

                if (!requestedRole.equalsIgnoreCase(user.getRole())) {
                    errorMessage =
                            "This account is registered as "
                            + user.getRole()
                            + ". Select the correct role bubble.";
                    return null;
                }

                return user;
            }

            @Override
            protected void done() {
                loginButton.setEnabled(true);
                loginButton.setText(
                        selectedRole == 0 ? "Log In as Student"
                        : selectedRole == 1 ? "Log In as Alumni"
                        : "Admin Log In");

                try {
                    User user = get();

                    if (user == null) {
                        showStatus(errorMessage, RED);
                        return;
                    }

                    // Save the authenticated user for the rest
                    // of the application.
                    SessionManager.setCurrentUser(user);

                    openDashboard(user);

                } catch (Exception ex) {
                    ex.printStackTrace();

                    Throwable cause = ex.getCause();
                    String detail = cause != null
                            ? cause.getMessage()
                            : ex.getMessage();

                    showStatus(
                            "Could not complete login. Check the "
                            + "database connection and credentials.",
                            RED);

                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Login failed.\n\n"
                            + (detail == null ? "" : detail),
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private void openDashboard(User user) {
        if (user == null || user.getRole() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "The account has no valid role assigned.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String role = user.getRole().trim().toUpperCase();
        boolean opened = false;

        switch (role) {
            case "STUDENT":
                // StudentDashboard's constructor requires a User argument.
                // It makes itself visible in its constructor.
                new StudentDashboard(user);
                opened = true;
                break;

            case "ALUMNI":
                JFrame alumniFrame = createFrameByReflection(
                        new String[] { "ui.AlumniDashboard" },
                        new Class<?>[] { String.class },
                        new Object[] { user.getName() });
                if (alumniFrame != null) {
                    alumniFrame.setVisible(true);
                    opened = true;
                }
                break;

            case "ADMIN":
                JFrame adminFrame = createFrameByReflection(
                        new String[] { "ui.AdminDashboard" },
                        new Class<?>[0],
                        new Object[0]);
                if (adminFrame != null) {
                    adminFrame.setVisible(true);
                    opened = true;
                }
                break;

            default:
                JOptionPane.showMessageDialog(
                        this,
                        "Unknown account role: " + user.getRole(),
                        "Role Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (opened) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "The dashboard for the selected role could not be opened.\n"
                    + "Check that the matching class exists in the ui package "
                    + "and has the expected public constructor.",
                    "Dashboard Not Found",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates a JFrame using a list of possible fully-qualified class names.
     * Reflection avoids compile errors when another role's screen has not
     * been added to the project yet.
     */
    private JFrame createFrameByReflection(
            String[] classNames,
            Class<?>[] parameterTypes,
            Object[] arguments) {

        for (String className : classNames) {
            try {
                Class<?> frameClass = Class.forName(className);
                Object instance = frameClass
                        .getConstructor(parameterTypes)
                        .newInstance(arguments);

                if (instance instanceof JFrame) {
                    return (JFrame) instance;
                }
            } catch (ClassNotFoundException ex) {
                // Try the next supported class name.
            } catch (ReflectiveOperationException | LinkageError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Could not create " + className + ".\n"
                        + "Check its constructor and any errors in that class.\n\n"
                        + ex.getMessage(),
                        "Screen Opening Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
    }

    private void showStatus(String message, Color color) {
        statusLabel.setForeground(color);
        statusLabel.setText(
                "<html><div style='width:310px'>"
                + message + "</div></html>");
    }

    // =====================================================
    // ROUNDED LOGIN CARD
    // =====================================================

    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color panelColor;

        RoundedPanel(int radius, Color panelColor) {
            this.radius = radius;
            this.panelColor = panelColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(panelColor);
            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(),
                    radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // =====================================================
    // NON-OVERLAPPING ROLE BUBBLE SELECTOR
    // =====================================================

    private class RoleSelector extends JPanel {

        private final int[] bubbleSizes = {148, 100, 100};

        private Timer animationTimer;
        private int animationFrame = 0;
        private boolean transitioning = false;
        private int pendingRole = 0;

        private Point[] savedPositions;
        private int oldRole;
        private int newRole;

        RoleSelector() {
            setOpaque(false);
            setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickedRole = findRoleAt(e.getPoint());

                    if (clickedRole != -1
                            && clickedRole != selectedRole
                            && !transitioning) {
                        animateTo(clickedRole);
                    }
                }
            });
        }

        /*
         * Layout rule:
         * - The selected bubble is always in the centre.
         * - One unselected bubble is on the left.
         * - The other unselected bubble is on the right.
         *
         * This guarantees that the two unselected bubbles
         * never receive the same target position.
         */
        private Point getTargetPosition(int index, int activeRole) {
            int centerX = getWidth() / 2;
            int centerY = (int) (getHeight() * 0.45);

            if (index == activeRole) {
                return new Point(centerX, centerY);
            }

            int leftRole = (activeRole + 1) % 3;
            int rightRole = (activeRole + 2) % 3;

            int sideSpacing = Math.min(
                    155, Math.max(112, getWidth() / 3));

            if (index == leftRole) {
                return new Point(
                        centerX - sideSpacing,
                        centerY + 42);
            }

            if (index == rightRole) {
                return new Point(
                        centerX + sideSpacing,
                        centerY + 42);
            }

            return new Point(centerX, centerY);
        }

        private int getBubbleSize(int index, int activeRole) {
            return index == activeRole ? 148 : 100;
        }

        private int findRoleAt(Point point) {
            if (transitioning) {
                return -1;
            }

            for (int i = 0; i < 3; i++) {
                Point p = getTargetPosition(i, selectedRole);
                int size = getBubbleSize(i, selectedRole);

                Ellipse2D circle = new Ellipse2D.Float(
                        p.x - size / 2f,
                        p.y - size / 2f,
                        size,
                        size);

                if (circle.contains(point)) {
                    return i;
                }
            }

            return -1;
        }

        /*
         * Two-stage transition:
         * 1. All bubbles shrink in their current positions.
         * 2. Once small, their positions rotate to the new layout.
         * 3. They expand with the newly selected role in the centre.
         *
         * The bubbles do not travel through one another.
         */
        private void animateTo(int targetRole) {
            if (transitioning || targetRole == selectedRole) {
                return;
            }

            oldRole = selectedRole;
            newRole = targetRole;
            pendingRole = targetRole;
            animationFrame = 0;
            transitioning = true;

            savedPositions = new Point[3];

            for (int i = 0; i < 3; i++) {
                savedPositions[i] =
                        getTargetPosition(i, oldRole);
            }

            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }

            animationTimer = new Timer(16, e -> {
                animationFrame++;

                if (animationFrame >= 24) {
                    selectedRole = pendingRole;
                    transitioning = false;
                    animationFrame = 24;
                    animationTimer.stop();

                    selectRole(selectedRole);
                }

                repaint();
            });

            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = (int) (getHeight() * 0.45);

            // Decorative background
            g2.setColor(new Color(230, 240, 233));
            g2.fillOval(
                    centerX - 105,
                    centerY - 105,
                    210, 210);

            float shrink = 1.0f;
            boolean inRepositionStage = false;

            if (transitioning) {
                float progress = animationFrame / 24.0f;

                if (progress < 0.5f) {
                    // Shrink before changing positions.
                    shrink = 1.0f - (progress * 1.36f);
                    shrink = Math.max(0.32f, shrink);
                } else {
                    // Bubbles are already small when their
                    // new positions are displayed.
                    inRepositionStage = true;
                    shrink = 0.32f + ((progress - 0.5f) * 1.36f);
                    shrink = Math.min(1.0f, shrink);
                }
            }

            for (int i = 0; i < 3; i++) {
                Point p;
                int baseSize;
                boolean active;

                if (!transitioning) {
                    p = getTargetPosition(i, selectedRole);
                    baseSize = getBubbleSize(i, selectedRole);
                    active = i == selectedRole;

                } else if (inRepositionStage) {
                    p = getTargetPosition(i, newRole);
                    baseSize = getBubbleSize(i, newRole);
                    active = i == newRole;

                } else {
                    p = savedPositions[i];
                    baseSize = getBubbleSize(i, oldRole);
                    active = i == oldRole;
                }

                int size = Math.max(34, (int) (baseSize * shrink));

                // Bubble shadow
                g2.setColor(new Color(
                        25, 60, 40, active ? 24 : 12));

                g2.fillOval(
                        p.x - size / 2 + 3,
                        p.y - size / 2 + 7,
                        size, size);

                // Bubble fill
                g2.setColor(active ? GREEN : WHITE);

                g2.fillOval(
                        p.x - size / 2,
                        p.y - size / 2,
                        size, size);

                // Bubble outline
                g2.setStroke(new BasicStroke(active ? 3f : 1.5f));

                g2.setColor(
                        active ? GREEN : new Color(218, 227, 220));

                g2.drawOval(
                        p.x - size / 2,
                        p.y - size / 2,
                        size, size);

                // Keep text hidden while bubbles are shrinking.
                if (size >= 65) {
                    String initial = roles[i].substring(0, 1);

                    g2.setFont(new Font(
                            "SansSerif",
                            Font.BOLD,
                            active ? 35 : 26));

                    g2.setColor(active ? WHITE : GREEN);

                    FontMetrics fm = g2.getFontMetrics();

                    g2.drawString(
                            initial,
                            p.x - fm.stringWidth(initial) / 2,
                            p.y + fm.getAscent() / 3);

                    g2.setFont(new Font(
                            "SansSerif",
                            active ? Font.BOLD : Font.PLAIN,
                            active ? 15 : 12));

                    g2.setColor(active ? DARK : MUTED);

                    fm = g2.getFontMetrics();

                    g2.drawString(
                            roles[i],
                            p.x - fm.stringWidth(roles[i]) / 2,
                            p.y + size / 2 + 23);
                }
            }

            g2.dispose();
        }
    }

    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default Swing look and feel.
            }

            new LoginFrame().setVisible(true);
        });
    }
}