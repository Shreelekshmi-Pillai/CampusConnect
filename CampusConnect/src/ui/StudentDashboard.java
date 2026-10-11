package ui;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentDashboard extends JFrame {

    private final User user;

    private final Color NAVY = new Color(24, 35, 63);
    private final Color NAVY_LIGHT = new Color(39, 52, 84);
    private final Color BACKGROUND = new Color(246, 248, 252);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(37, 48, 70);
    private final Color MUTED = new Color(120, 130, 148);
    private final Color PURPLE = new Color(112, 91, 230);
    private final Color TEAL = new Color(38, 166, 154);
    private final Color ORANGE = new Color(244, 155, 73);

    private JPanel contentPanel;
    private JPanel sidebar;

    public StudentDashboard(User user) {

        this.user = user;

        setTitle("Campus Connect | Student Dashboard");
        setSize(1250, 780);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        createSidebar();
        createMainArea();

        setVisible(true);
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private void createSidebar() {

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setBackground(NAVY);
        sidebar.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(28, 20, 20, 20));

        JLabel logo = new JLabel("Campus Connect");
        logo.setFont(new Font("SansSerif", Font.BOLD, 21));
        logo.setForeground(WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("YOUR CAMPUS, YOUR FUTURE");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 9));
        tagline.setForeground(new Color(165, 179, 211));
        tagline.setBorder(new EmptyBorder(7, 1, 28, 0));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(logo);
        top.add(tagline);

        JLabel menuLabel = new JLabel("  WORKSPACE");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        menuLabel.setForeground(new Color(145, 159, 191));
        menuLabel.setBorder(new EmptyBorder(0, 0, 12, 0));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(menuLabel);

        top.add(createNavButton("⌂", "Overview", true,
                () -> showHome()));

        top.add(createNavButton("◎", "Explore Alumni", false,
                () -> showSection(
                        "Explore Alumni",
                        "Discover alumni by company, industry, or expertise.",
                        "Connect with graduates who can share their experiences.")));

        top.add(createNavButton("✦", "Mentorship", false,
                () -> showSection(
                        "Mentorship",
                        "Build a connection with an experienced alumnus.",
                        "Explore mentors and manage your mentorship requests.")));

        top.add(createNavButton("↗", "Opportunities", false,
                () -> showSection(
                        "Opportunities",
                        "Find internships, jobs, and career opportunities.",
                        "Explore opportunities shared by the Campus Connect community.")));

        top.add(createNavButton("◇", "Career Roadmap", false,
                () -> showSection(
                        "Career Roadmap",
                        "Plan your journey from campus to career.",
                        "Explore skills, milestones, and learning goals for your future.")));

        top.add(createNavButton("▤", "Experience Posts", false,
                () -> showSection(
                        "Experience Posts",
                        "Learn from the experiences of your seniors.",
                        "Discover placement stories, interview tips, and career advice.")));

        top.add(createNavButton("♧", "Notifications", false,
                () -> showSection(
                        "Notifications",
                        "Stay updated on your campus connections.",
                        "Your mentorship updates and opportunity notifications will appear here.")));

        sidebar.add(top, BorderLayout.NORTH);

        // Bottom profile section
        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.setLayout(new BorderLayout(10, 0));
        profilePanel.setBorder(new EmptyBorder(15, 18, 22, 18));

        JLabel avatar = new JLabel(getInitials());
        avatar.setOpaque(true);
        avatar.setBackground(new Color(92, 85, 190));
        avatar.setForeground(WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(39, 39));

        JPanel profileText = new JPanel();
        profileText.setOpaque(false);
        profileText.setLayout(new BoxLayout(profileText, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(getUserName());
        nameLabel.setForeground(WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel roleLabel = new JLabel("Student account");
        roleLabel.setForeground(new Color(165, 179, 211));
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));

        profileText.add(nameLabel);
        profileText.add(Box.createVerticalStrut(4));
        profileText.add(roleLabel);

        profilePanel.add(avatar, BorderLayout.WEST);
        profilePanel.add(profileText, BorderLayout.CENTER);

        sidebar.add(profilePanel, BorderLayout.SOUTH);

        getContentPane().add(sidebar, BorderLayout.WEST);
    }

    private JButton createNavButton(
            String icon,
            String text,
            boolean selected,
            Runnable action) {

        JButton button = new JButton("  " + icon + "     " + text);

        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setForeground(selected ? WHITE : new Color(200, 210, 230));
        button.setBackground(selected ? NAVY_LIGHT : NAVY);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        button.setPreferredSize(new Dimension(195, 43));
        button.setBorder(new EmptyBorder(0, 12, 0, 5));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.addActionListener(e -> action.run());

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(NAVY_LIGHT);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(selected ? NAVY_LIGHT : NAVY);
            }
        });

        return button;
    }

    // =====================================================
    // MAIN AREA
    // =====================================================

    private void createMainArea() {

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(BACKGROUND);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(WHITE);
        topBar.setBorder(new EmptyBorder(17, 30, 17, 30));

        JLabel pageLabel = new JLabel("STUDENT SPACE");
        pageLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        pageLabel.setForeground(MUTED);

        JButton helpButton = new JButton("?  Help");
        helpButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        helpButton.setBackground(WHITE);
        helpButton.setForeground(TEXT);
        helpButton.setFocusPainted(false);
        helpButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 229, 238)),
                new EmptyBorder(7, 12, 7, 12)));

        helpButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Welcome to Campus Connect!\n"
                        + "Use the sidebar to explore the available sections.",
                        "Help",
                        JOptionPane.INFORMATION_MESSAGE));

        topBar.add(pageLabel, BorderLayout.WEST);
        topBar.add(helpButton, BorderLayout.EAST);

        mainArea.add(topBar, BorderLayout.NORTH);

        // Main content area
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        showHome();

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainArea.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(mainArea, BorderLayout.CENTER);
    }

    // =====================================================
    // HOME DASHBOARD
    // =====================================================

    private void showHome() {

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout(0, 22));

        JPanel home = new JPanel();
        home.setOpaque(false);
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));

        // Welcome heading
        JLabel welcome = new JLabel(
                "Hey, " + getFirstName() + "!");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(TEXT);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Your next opportunity could start with one connection.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        subtitle.setBorder(new EmptyBorder(7, 0, 22, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        home.add(welcome);
        home.add(subtitle);

        // Welcome banner
        JPanel banner = new JPanel(new BorderLayout(15, 10));
        banner.setBackground(new Color(232, 231, 255));
        banner.setBorder(new EmptyBorder(23, 25, 23, 25));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        banner.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bannerText = new JPanel();
        bannerText.setOpaque(false);
        bannerText.setLayout(new BoxLayout(
                bannerText, BoxLayout.Y_AXIS));

        JLabel bannerTitle = new JLabel(
                "Make your campus connections count.");
        bannerTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        bannerTitle.setForeground(new Color(55, 48, 116));

        JLabel bannerSubtitle = new JLabel(
                "Find guidance, discover opportunities, and plan ahead.");
        bannerSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bannerSubtitle.setForeground(new Color(91, 84, 150));
        bannerSubtitle.setBorder(new EmptyBorder(7, 0, 12, 0));

        JButton exploreButton = new JButton("Explore your network  →");
        stylePrimaryButton(exploreButton);

        exploreButton.addActionListener(e ->
                showSection(
                        "Explore Alumni",
                        "Discover alumni by company, industry, or expertise.",
                        "Connect with graduates who can share their experiences."));

        bannerText.add(bannerTitle);
        bannerText.add(bannerSubtitle);
        bannerText.add(exploreButton);

        JLabel bannerIcon = new JLabel("✦");
        bannerIcon.setFont(new Font("SansSerif", Font.BOLD, 45));
        bannerIcon.setForeground(PURPLE);

        banner.add(bannerText, BorderLayout.CENTER);
        banner.add(bannerIcon, BorderLayout.EAST);

        home.add(banner);
        home.add(Box.createVerticalStrut(25));

        // Section heading
        JLabel quickTitle = new JLabel("Your space to grow");
        quickTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        quickTitle.setForeground(TEXT);
        quickTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel quickSubtitle = new JLabel(
                "Choose what you want to work on today.");
        quickSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        quickSubtitle.setForeground(MUTED);
        quickSubtitle.setBorder(new EmptyBorder(5, 0, 15, 0));
        quickSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        home.add(quickTitle);
        home.add(quickSubtitle);

        // Quick action cards
        JPanel cards = new JPanel(new GridLayout(2, 2, 15, 15));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        cards.add(createFeatureCard(
                "01",
                "Find a mentor",
                "Get advice from someone who has been there.",
                PURPLE,
                () -> showSection(
                        "Mentorship",
                        "Build a connection with an experienced alumnus.",
                        "Explore mentors and manage your mentorship requests.")));

        cards.add(createFeatureCard(
                "02",
                "Explore opportunities",
                "Discover internships and job openings.",
                TEAL,
                () -> showSection(
                        "Opportunities",
                        "Find internships, jobs, and career opportunities.",
                        "Explore opportunities shared by the Campus Connect community.")));

        cards.add(createFeatureCard(
                "03",
                "Plan your career",
                "Turn your career goals into actionable steps.",
                ORANGE,
                () -> showSection(
                        "Career Roadmap",
                        "Plan your journey from campus to career.",
                        "Explore skills, milestones, and learning goals for your future.")));

        cards.add(createFeatureCard(
                "04",
                "Learn from seniors",
                "Read real experiences and practical advice.",
                new Color(67, 139, 208),
                () -> showSection(
                        "Experience Posts",
                        "Learn from the experiences of your seniors.",
                        "Discover placement stories, interview tips, and career advice.")));

        home.add(cards);
        home.add(Box.createVerticalStrut(25));

        // Bottom information panel
        JPanel bottomPanel = new JPanel(new BorderLayout(15, 0));
        bottomPanel.setBackground(WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(232, 235, 242)),
                new EmptyBorder(18, 20, 18, 20)));
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bottomText = new JPanel();
        bottomText.setOpaque(false);
        bottomText.setLayout(new BoxLayout(
                bottomText, BoxLayout.Y_AXIS));

        JLabel bottomTitle = new JLabel("Small steps, big possibilities.");
        bottomTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        bottomTitle.setForeground(TEXT);

        JLabel bottomSubtitle = new JLabel(
                "Start by exploring the people and opportunities around you.");
        bottomSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        bottomSubtitle.setForeground(MUTED);
        bottomSubtitle.setBorder(new EmptyBorder(5, 0, 0, 0));

        bottomText.add(bottomTitle);
        bottomText.add(bottomSubtitle);

        JLabel sparkle = new JLabel("✧");
        sparkle.setForeground(PURPLE);
        sparkle.setFont(new Font("SansSerif", Font.BOLD, 27));

        bottomPanel.add(bottomText, BorderLayout.CENTER);
        bottomPanel.add(sparkle, BorderLayout.EAST);

        home.add(bottomPanel);

        contentPanel.add(home, BorderLayout.NORTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =====================================================
    // FEATURE CARD
    // =====================================================

    private JPanel createFeatureCard(
            String number,
            String title,
            String description,
            Color accent,
            Runnable action) {

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(16, 17, 15, 17)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        numberLabel.setForeground(accent);

        JLabel arrow = new JLabel("↗");
        arrow.setFont(new Font("SansSerif", Font.BOLD, 17));
        arrow.setForeground(accent);

        top.add(numberLabel, BorderLayout.WEST);
        top.add(arrow, BorderLayout.EAST);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(
                textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setForeground(TEXT);

        JLabel descLabel = new JLabel(
                "<html><div style='width:210px;'>"
                + description + "</div></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descLabel.setForeground(MUTED);
        descLabel.setBorder(new EmptyBorder(6, 0, 0, 0));

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        card.add(top, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accent),
                        new EmptyBorder(16, 17, 15, 17)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230, 234, 242)),
                        new EmptyBorder(16, 17, 15, 17)));
            }
        });

        return card;
    }

    // =====================================================
    // OTHER SECTIONS
    // =====================================================

    private void showSection(
            String title,
            String subtitle,
            String description) {

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JPanel section = new JPanel();
        section.setOpaque(false);
        section.setLayout(new BoxLayout(
                section, BoxLayout.Y_AXIS));

        JButton backButton = new JButton("←  Back to overview");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        backButton.setForeground(PURPLE);
        backButton.setBackground(BACKGROUND);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> showHome());

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(TEXT);
        heading.setBorder(new EmptyBorder(25, 0, 8, 0));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subheading = new JLabel(subtitle);
        subheading.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subheading.setForeground(MUTED);
        subheading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel information = new JPanel();
        information.setLayout(new BoxLayout(
                information, BoxLayout.Y_AXIS));
        information.setBackground(WHITE);
        information.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(25, 25, 25, 25)));
        information.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 160));
        information.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoTitle = new JLabel("Welcome to " + title);
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
        infoTitle.setForeground(TEXT);

        JLabel infoDescription = new JLabel(
                "<html><div style='width:500px;'>"
                + description
                + "</div></html>");
        infoDescription.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        infoDescription.setForeground(MUTED);
        infoDescription.setBorder(new EmptyBorder(10, 0, 0, 0));

        information.add(infoTitle);
        information.add(infoDescription);

        JLabel note = new JLabel(
                "This section is ready for its feature implementation.");
        note.setFont(new Font("SansSerif", Font.ITALIC, 11));
        note.setForeground(MUTED);
        note.setBorder(new EmptyBorder(18, 0, 0, 0));
        note.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(backButton);
        section.add(heading);
        section.add(subheading);
        section.add(Box.createVerticalStrut(25));
        section.add(information);
        section.add(note);

        contentPanel.add(section, BorderLayout.NORTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =====================================================
    // HELPER METHODS
    // =====================================================

    private void stylePrimaryButton(JButton button) {

        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(WHITE);
        button.setBackground(PURPLE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 13, 9, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private String getUserName() {
        if (user != null
                && user.getName() != null
                && !user.getName().trim().isEmpty()) {
            return user.getName().trim();
        }
        return "Student";
    }

    private String getFirstName() {
        String name = getUserName();
        int space = name.indexOf(' ');

        if (space > 0) {
            return name.substring(0, space);
        }

        return name;
    }

    private String getInitials() {
        String name = getUserName();
        String[] parts = name.trim().split("\\s+");

        if (parts.length >= 2) {
            return (parts[0].substring(0, 1)
                    + parts[1].substring(0, 1)).toUpperCase();
        }

        return name.substring(0, 1).toUpperCase();
    }
}