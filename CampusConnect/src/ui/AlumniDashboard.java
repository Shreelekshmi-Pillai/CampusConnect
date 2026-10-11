
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AlumniDashboard extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;

    private String alumniName = "Alumni";

    public AlumniDashboard() {
        this("Alumni");
    }

    public AlumniDashboard(String name) {
        alumniName = (name == null || name.trim().isEmpty())
                ? "Alumni" : name.trim();

        setTitle("Campus Connect | Alumni Dashboard");
        setSize(1200, 760);
        setMinimumSize(new Dimension(1000, 650));
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
        main.setBorder(new EmptyBorder(28, 32, 28, 32));

        main.add(createTopBar(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);

        root.add(main, BorderLayout.CENTER);
    }

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

        JLabel subtitle = new JLabel("YOUR COMMUNITY");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        subtitle.setForeground(new Color(165, 180, 205));
        subtitle.setBorder(new EmptyBorder(8, 2, 30, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(brand);
        sidebar.add(subtitle);

        addNavItem(sidebar, "▦", "Dashboard", true,
                () -> showMessage("You are already on your dashboard."));

        addNavItem(sidebar, "◎", "Find Students",
                false, () -> showMessage(
                        "Student directory will appear here."));

        addNavItem(sidebar, "✦", "Mentorship",
                false, () -> showMessage(
                        "Manage your mentorship requests and sessions here."));

        addNavItem(sidebar, "↗", "Opportunities",
                false, () -> showMessage(
                        "View and manage the opportunities you share."));

        addNavItem(sidebar, "▤", "Experience Posts",
                false, () -> showMessage(
                        "Share your college and career experiences here."));

        addNavItem(sidebar, "◷", "Career Roadmaps",
                false, () -> showMessage(
                        "Help students plan their career journeys here."));

        addNavItem(sidebar, "♧", "Notifications",
                false, () -> showMessage(
                        "Your notifications will appear here."));

        sidebar.add(Box.createVerticalGlue());

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(55, 65, 90));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(separator);

        sidebar.add(Box.createVerticalStrut(15));

        addNavItem(sidebar, "⚙", "My Profile",
                false, () -> showMessage(
                        "Profile editing can be connected here."));

        addNavItem(sidebar, "↪", "Logout",
                false, this::logout);

        return sidebar;
    }

    private void addNavItem(JPanel sidebar, String symbol,
                            String text, boolean selected,
                            Runnable action) {

        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        item.setPreferredSize(new Dimension(205, 44));
        item.setBackground(selected
                ? new Color(79, 70, 229)
                : new Color(20, 29, 52));

        item.setBorder(new EmptyBorder(0, 12, 0, 10));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(symbol);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        icon.setForeground(selected ? white : new Color(165, 180, 205));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif",
                selected ? Font.BOLD : Font.PLAIN, 13));
        label.setForeground(selected ? white : new Color(215, 223, 238));

        item.add(icon, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);

        MouseAdapter click = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }

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
        };

        item.addMouseListener(click);
        icon.addMouseListener(click);
        label.addMouseListener(click);

        sidebar.add(item);
        sidebar.add(Box.createVerticalStrut(7));
    }

    private JPanel createTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Alumni Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(dark);

        JLabel description = new JLabel(
                "Reconnect, mentor, and make a difference.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(muted);
        description.setBorder(new EmptyBorder(7, 0, 0, 0));

        heading.add(title);
        heading.add(description);

        JPanel profile = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 10, 5));
        profile.setOpaque(false);

        JLabel avatar = new JLabel(getInitials(alumniName),
                SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(224, 231, 255));
        avatar.setForeground(primary);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        avatar.setPreferredSize(new Dimension(42, 42));

        JLabel name = new JLabel(alumniName);
        name.setFont(new Font("SansSerif", Font.BOLD, 13));
        name.setForeground(dark);

        profile.add(avatar);
        profile.add(name);

        top.add(heading, BorderLayout.WEST);
        top.add(profile, BorderLayout.EAST);

        return top;
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createWelcomeCard());
        content.add(Box.createVerticalStrut(22));

        JLabel section = new JLabel("Your alumni space");
        section.setFont(new Font("SansSerif", Font.BOLD, 18));
        section.setForeground(dark);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(section);
        content.add(Box.createVerticalStrut(13));
        content.add(createFeatureCards());

        content.add(Box.createVerticalStrut(24));
        content.add(createBottomSection());

        return content;
    }

    private JPanel createWelcomeCard() {
        JPanel card = new JPanel(new BorderLayout(20, 10));
        card.setBackground(new Color(235, 237, 255));
        card.setBorder(new EmptyBorder(24, 27, 24, 27));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel greeting = new JLabel("Welcome back, " + alumniName + "!");
        greeting.setFont(new Font("SansSerif", Font.BOLD, 23));
        greeting.setForeground(new Color(43, 48, 110));

        JLabel message = new JLabel(
                "<html>Your experience can open doors for the next generation."
                + "<br>Connect with students and share what you've learned.</html>");
        message.setFont(new Font("SansSerif", Font.PLAIN, 13));
        message.setForeground(new Color(82, 91, 145));
        message.setBorder(new EmptyBorder(10, 0, 0, 0));

        text.add(greeting);
        text.add(message);

        JButton mentorButton = createButton("Become a Mentor");
        mentorButton.addActionListener(e ->
                showMessage("Mentorship registration selected."));

        card.add(text, BorderLayout.CENTER);
        card.add(mentorButton, BorderLayout.EAST);

        return card;
    }

    private JPanel createFeatureCards() {
        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 0));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        cards.add(createFeatureCard(
                "01", "Mentorship",
                "Guide students through your career experience.",
                "Explore Mentorship",
                () -> showMessage("Mentorship section selected.")));

        cards.add(createFeatureCard(
                "02", "Share Opportunities",
                "Post internships, jobs, and referrals.",
                "Post Opportunity",
                this::postOpportunity));

        cards.add(createFeatureCard(
                "03", "Share Your Story",
                "Help students learn from your journey.",
                "Write a Post",
                this::writeExperience));

        return cards;
    }

    private JPanel createFeatureCard(String number, String title,
                                     String description, String buttonText,
                                     Runnable action) {

        JPanel card = new JPanel();
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(17, 17, 15, 17)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel(number);
        badge.setOpaque(true);
        badge.setBackground(new Color(238, 242, 255));
        badge.setForeground(primary);
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setBorder(new EmptyBorder(5, 8, 5, 8));
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(dark);
        heading.setBorder(new EmptyBorder(12, 0, 5, 0));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel detail = new JLabel("<html>" + description + "</html>");
        detail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detail.setForeground(muted);
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton(buttonText);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(primary);
        button.setBackground(white);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(e -> action.run());

        card.add(badge);
        card.add(heading);
        card.add(detail);
        card.add(Box.createVerticalGlue());
        card.add(button);

        return card;
    }

    private JPanel createBottomSection() {
        JPanel bottom = new JPanel(new GridLayout(1, 2, 15, 0));
        bottom.setOpaque(false);
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        JPanel activity = createInfoPanel("Get involved",
                "Your next contribution could change a student's future.");

        JButton mentorship = createButton("Offer Mentorship");
        mentorship.setAlignmentX(Component.LEFT_ALIGNMENT);
        mentorship.addActionListener(e ->
                showMessage("Mentorship section selected."));
        activity.add(Box.createVerticalStrut(13));
        activity.add(mentorship);

        JPanel community = createInfoPanel("Build your network",
                "Reconnect with your college community and discover new opportunities.");

        JButton network = createButton("Explore Community");
        network.setAlignmentX(Component.LEFT_ALIGNMENT);
        network.addActionListener(e ->
                showMessage("Community section selected."));
        community.add(Box.createVerticalStrut(13));
        community.add(network);

        bottom.add(activity);
        bottom.add(community);

        return bottom;
    }

    private JPanel createInfoPanel(String title, String description) {
        JPanel panel = new JPanel();
        panel.setBackground(white);
        panel.setBorder(new EmptyBorder(18, 20, 18, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 15));
        heading.setForeground(dark);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel detail = new JLabel(
                "<html>" + description + "</html>");
        detail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detail.setForeground(muted);
        detail.setBorder(new EmptyBorder(8, 0, 0, 0));
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(heading);
        panel.add(detail);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(white);
        button.setBackground(primary);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(11, 16, 11, 16));
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        return button;
    }

    private void postOpportunity() {
        JTextField titleField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField typeField = new JTextField();
        JTextArea descriptionArea = new JTextArea(5, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.add(new JLabel("Opportunity title:"));
        form.add(titleField);
        form.add(new JLabel("Company:"));
        form.add(companyField);
        form.add(new JLabel("Type (Internship / Job / Referral):"));
        form.add(typeField);
        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
                this, form, "Post an Opportunity",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty()
                    || companyField.getText().trim().isEmpty()) {
                showMessage("Please enter the opportunity title and company.");
                return;
            }

            showMessage("Opportunity details entered successfully. "
                    + "Database saving still needs to be connected.");
        }
    }

    private void writeExperience() {
        JTextField titleField = new JTextField();
        JTextArea storyArea = new JTextArea(7, 28);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.add(new JLabel("Post title:"));
        form.add(titleField);
        form.add(new JLabel("Your experience:"));
        form.add(new JScrollPane(storyArea));

        int result = JOptionPane.showConfirmDialog(
                this, form, "Share Your Experience",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty()
                    || storyArea.getText().trim().isEmpty()) {
                showMessage("Please enter a title and your experience.");
                return;
            }

            showMessage("Your post has been drafted. "
                    + "Database saving still needs to be connected.");
        }
    }

    private String getInitials(String name) {
        String[] parts = name.trim().split("\\s+");

        if (parts.length == 0 || parts[0].isEmpty()) {
            return "A";
        }

        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }

        return (parts[0].substring(0, 1)
                + parts[parts.length - 1].substring(0, 1))
                .toUpperCase();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Campus Connect",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();

            // Replace this with your LoginFrame constructor
            // if your login screen needs to be reopened.
            new LoginFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AlumniDashboard("Alumni").setVisible(true));
    }
}
