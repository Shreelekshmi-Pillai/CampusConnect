
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    private JLabel pageTitle;
    private JLabel pageSubtitle;

    public AdminDashboard() {
        setTitle("Campus Connect | Admin Dashboard");
        setSize(1250, 780);
        setMinimumSize(new Dimension(1050, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildDashboard();
    }

    private void buildDashboard() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);
        setContentPane(root);

        root.add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(0, 22));
        main.setBackground(background);
        main.setBorder(new EmptyBorder(25, 30, 25, 30));

        main.add(createHeader(), BorderLayout.NORTH);

        contentPanel.setOpaque(false);
        contentPanel.add(createOverviewPage(), "Overview");
        contentPanel.add(createVerificationPage(), "Verification");
        contentPanel.add(createUsersPage(), "Users");
        contentPanel.add(createReportsPage(), "Reports");
        contentPanel.add(createSettingsPage(), "Settings");

        main.add(contentPanel, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);
    }

    // SIDEBAR

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBackground(new Color(20, 29, 52));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(28, 18, 24, 18));

        JLabel brand = new JLabel("Campus Connect");
        brand.setFont(new Font("SansSerif", Font.BOLD, 22));
        brand.setForeground(white);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("ADMINISTRATION");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        subtitle.setForeground(new Color(165, 180, 205));
        subtitle.setBorder(new EmptyBorder(8, 2, 30, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(brand);
        sidebar.add(subtitle);

        addNavItem(sidebar, "▦", "Overview", "Overview", true);
        addNavItem(sidebar, "✓", "Verify Alumni", "Verification", false);
        addNavItem(sidebar, "♙", "Manage Users", "Users", false);
        addNavItem(sidebar, "▤", "Reports & Analytics", "Reports", false);

        sidebar.add(Box.createVerticalGlue());

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(55, 65, 90));
        separator.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 1));

        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(15));

        addNavItem(sidebar, "⚙", "Settings", "Settings", false);

        JPanel logout = createNavItem("↪", "Logout", false);
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        sidebar.add(logout);

        return sidebar;
    }

    private void addNavItem(JPanel sidebar, String symbol,
                            String text, String page,
                            boolean selected) {

        JPanel item = createNavItem(symbol, text, selected);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPage(page);
            }
        });

        sidebar.add(item);
        sidebar.add(Box.createVerticalStrut(7));
    }

    private JPanel createNavItem(String symbol, String text,
                                 boolean selected) {

        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        item.setPreferredSize(new Dimension(205, 44));
        item.setBackground(selected
                ? primary : new Color(20, 29, 52));
        item.setBorder(new EmptyBorder(0, 12, 0, 10));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(symbol);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        icon.setForeground(selected
                ? white : new Color(165, 180, 205));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif",
                selected ? Font.BOLD : Font.PLAIN, 13));
        label.setForeground(selected
                ? white : new Color(215, 223, 238));

        item.add(icon, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    item.setBackground(new Color(37, 48, 75));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    item.setBackground(new Color(20, 29, 52));
                }
            }
        });

        return item;
    }

    private void showPage(String page) {
        cardLayout.show(contentPanel, page);

        switch (page) {
            case "Overview":
                pageTitle.setText("Admin Overview");
                pageSubtitle.setText(
                        "Monitor and manage your campus community.");
                break;

            case "Verification":
                pageTitle.setText("Alumni Verification");
                pageSubtitle.setText(
                        "Review alumni registration requests.");
                break;

            case "Users":
                pageTitle.setText("Manage Users");
                pageSubtitle.setText(
                        "View student and alumni accounts.");
                break;

            case "Reports":
                pageTitle.setText("Reports & Analytics");
                pageSubtitle.setText(
                        "Monitor platform activity and growth.");
                break;

            case "Settings":
                pageTitle.setText("Settings");
                pageSubtitle.setText(
                        "Manage Campus Connect administration.");
                break;
        }
    }

    // HEADER

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        pageTitle = new JLabel("Admin Overview");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 27));
        pageTitle.setForeground(dark);

        pageSubtitle = new JLabel(
                "Monitor and manage your campus community.");
        pageSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        pageSubtitle.setForeground(muted);
        pageSubtitle.setBorder(new EmptyBorder(7, 0, 0, 0));

        heading.add(pageTitle);
        heading.add(pageSubtitle);

        JLabel adminBadge = new JLabel("ADMIN");
        adminBadge.setOpaque(true);
        adminBadge.setBackground(new Color(224, 231, 255));
        adminBadge.setForeground(primary);
        adminBadge.setFont(new Font("SansSerif", Font.BOLD, 12));
        adminBadge.setBorder(new EmptyBorder(9, 14, 9, 14));

        header.add(heading, BorderLayout.WEST);
        header.add(adminBadge, BorderLayout.EAST);

        return header;
    }

    // OVERVIEW PAGE

    private JPanel createOverviewPage() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to your workspace");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 19));
        welcome.setForeground(dark);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "Keep registrations verified and the community thriving.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(muted);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        description.setBorder(new EmptyBorder(7, 0, 20, 0));

        page.add(welcome);
        page.add(description);
        page.add(createStats());

        page.add(Box.createVerticalStrut(25));

        JLabel actionsTitle = new JLabel("Quick actions");
        actionsTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        actionsTitle.setForeground(dark);
        actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        page.add(actionsTitle);
        page.add(Box.createVerticalStrut(13));
        page.add(createQuickActions());

        page.add(Box.createVerticalStrut(25));
        page.add(createAdminInfo());

        return page;
    }

    private JPanel createStats() {
        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

        stats.add(createStatCard("Registered Students",
                "—", "Student accounts"));
        stats.add(createStatCard("Registered Alumni",
                "—", "Alumni accounts"));
        stats.add(createStatCard("Pending Verification",
                "—", "Awaiting review"));
        stats.add(createStatCard("Opportunities",
                "—", "Shared with students"));

        return stats;
    }

    private JPanel createStatCard(String title, String value,
                                  String detail) {
        JPanel card = new JPanel();
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(17, 15, 15, 15)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.PLAIN, 11));
        heading.setForeground(muted);

        JLabel number = new JLabel(value);
        number.setFont(new Font("SansSerif", Font.BOLD, 27));
        number.setForeground(dark);
        number.setBorder(new EmptyBorder(8, 0, 4, 0));

        JLabel note = new JLabel(detail);
        note.setFont(new Font("SansSerif", Font.PLAIN, 10));
        note.setForeground(muted);

        card.add(heading);
        card.add(number);
        card.add(note);

        return card;
    }

    private JPanel createQuickActions() {
        JPanel actions = new JPanel(new GridLayout(1, 3, 14, 0));
        actions.setOpaque(false);
        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);

        actions.add(createActionCard(
                "Verify Alumni",
                "Confirm alumni identities and approve registrations.",
                "Open Verification",
                () -> showPage("Verification")));

        actions.add(createActionCard(
                "Manage Accounts",
                "Review student and alumni account details.",
                "View Users",
                () -> showPage("Users")));

        actions.add(createActionCard(
                "Platform Analytics",
                "Review reports and community activity.",
                "View Reports",
                () -> showPage("Reports")));

        return actions;
    }

    private JPanel createActionCard(String title, String description,
                                    String buttonText, Runnable action) {
        JPanel card = new JPanel();
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(17, 17, 14, 17)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(dark);

        JLabel detail = new JLabel(
                "<html>" + description + "</html>");
        detail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detail.setForeground(muted);
        detail.setBorder(new EmptyBorder(8, 0, 10, 0));

        JButton button = createButton(buttonText);
        button.addActionListener(e -> action.run());
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(detail);
        card.add(button);

        return card;
    }

    private JPanel createAdminInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(235, 237, 255));
        panel.setBorder(new EmptyBorder(20, 22, 20, 22));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Administrator responsibilities");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setForeground(new Color(43, 48, 110));

        JLabel text = new JLabel(
                "<html>Verify alumni registrations, maintain account integrity, "
                + "and ensure the platform remains useful for students "
                + "and graduates.</html>");
        text.setFont(new Font("SansSerif", Font.PLAIN, 12));
        text.setForeground(new Color(82, 91, 145));
        text.setBorder(new EmptyBorder(8, 0, 0, 0));

        panel.add(heading);
        panel.add(text);

        return panel;
    }

    // ALUMNI VERIFICATION PAGE

    private JPanel createVerificationPage() {
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setOpaque(false);

        JLabel heading = new JLabel(
                "<html><b>Pending alumni registrations</b><br>"
                + "<font color='#64748B' size='3'>"
                + "Review submitted details before approving an account."
                + "</font></html>");
        heading.setFont(new Font("SansSerif", Font.PLAIN, 17));

        JPanel empty = new JPanel();
        empty.setBackground(white);
        empty.setBorder(new EmptyBorder(30, 25, 30, 25));
        empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));

        JLabel emptyTitle = new JLabel(
                "Alumni verification queue");
        emptyTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        emptyTitle.setForeground(dark);
        emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emptyText = new JLabel(
                "<html><div style='text-align:center'>"
                + "Pending registrations will appear here once the "
                + "database is connected.</div></html>");
        emptyText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emptyText.setForeground(muted);
        emptyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyText.setBorder(new EmptyBorder(12, 0, 20, 0));

        JButton refresh = createButton("Refresh");
        refresh.setAlignmentX(Component.CENTER_ALIGNMENT);
        refresh.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Connect this page to UserDAO to load pending alumni."));

        empty.add(Box.createVerticalGlue());
        empty.add(emptyTitle);
        empty.add(emptyText);
        empty.add(refresh);
        empty.add(Box.createVerticalGlue());

        page.add(heading, BorderLayout.NORTH);
        page.add(empty, BorderLayout.CENTER);

        return page;
    }

    // USERS PAGE

    private JPanel createUsersPage() {
        JPanel page = new JPanel(new BorderLayout(0, 15));
        page.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setOpaque(false);

        JLabel heading = new JLabel("Community accounts");
        heading.setFont(new Font("SansSerif", Font.BOLD, 19));
        heading.setForeground(dark);

        JButton refresh = createButton("Refresh Users");
        refresh.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Connect this page to UserDAO to load accounts."));

        top.add(heading, BorderLayout.WEST);
        top.add(refresh, BorderLayout.EAST);

        String[] columns = {
                "User ID", "Name", "Email", "Role", "Status"
        };

        Object[][] data = {};

        JTable table = new JTable(data, columns);
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(
                new Color(235, 237, 245));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(230, 234, 242)));

        page.add(top, BorderLayout.NORTH);
        page.add(scrollPane, BorderLayout.CENTER);

        return page;
    }

    // REPORTS PAGE

    private JPanel createReportsPage() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Platform insights");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setForeground(dark);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel detail = new JLabel(
                "Track registrations, mentorship, and opportunity sharing.");
        detail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        detail.setForeground(muted);
        detail.setBorder(new EmptyBorder(8, 0, 22, 0));
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);

        page.add(heading);
        page.add(detail);

        page.add(createReportCard("User growth",
                "Compare student and alumni registrations over time."));

        page.add(Box.createVerticalStrut(13));

        page.add(createReportCard("Mentorship activity",
                "Monitor mentorship requests and completed sessions."));

        page.add(Box.createVerticalStrut(13));

        page.add(createReportCard("Opportunity sharing",
                "Track jobs, internships, and referrals posted by alumni."));

        return page;
    }

    private JPanel createReportCard(String title, String description) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(20, 20, 20, 20)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setFont(new Font("SansSerif", Font.BOLD, 15));
        name.setForeground(dark);

        JLabel detail = new JLabel(description);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detail.setForeground(muted);
        detail.setBorder(new EmptyBorder(7, 0, 0, 0));

        labels.add(name);
        labels.add(detail);

        JLabel tag = new JLabel("ANALYTICS");
        tag.setFont(new Font("SansSerif", Font.BOLD, 10));
        tag.setForeground(primary);

        card.add(labels, BorderLayout.CENTER);
        card.add(tag, BorderLayout.EAST);

        return card;
    }

    // SETTINGS PAGE

    private JPanel createSettingsPage() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Administration settings");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setForeground(dark);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel detail = new JLabel(
                "Administrative configuration and account security.");
        detail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        detail.setForeground(muted);
        detail.setBorder(new EmptyBorder(8, 0, 22, 0));
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);

        page.add(heading);
        page.add(detail);

        page.add(createReportCard("Account verification",
                "Only authorised administrators should approve alumni."));

        page.add(Box.createVerticalStrut(15));

        page.add(createReportCard("Access control",
                "Keep administrative privileges restricted to admins."));

        page.add(Box.createVerticalStrut(15));

        page.add(createReportCard("Database connection",
                "Configure the application to retrieve live account data."));

        return page;
    }

    // HELPERS

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(white);
        button.setBackground(primary);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        return button;
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AdminDashboard().setVisible(true));
    }
}
