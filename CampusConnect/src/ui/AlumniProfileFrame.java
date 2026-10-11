
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlumniProfileFrame extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;

    private final String alumniId;
    private final String alumniName;
    private final String department;
    private final String graduationYear;
    private final String company;
    private final String jobRole;
    private final String skills;
    private final String bio;

    public AlumniProfileFrame() {
        this(
            "1",
            "Arun Kumar",
            "Computer Science",
            "2024",
            "Google",
            "Software Engineer",
            "Java, SQL, Spring Boot, Problem Solving",
            "I enjoy building software solutions and helping "
                + "students understand the transition from college "
                + "to a professional career."
        );
    }

    public AlumniProfileFrame(
            String alumniId,
            String alumniName,
            String department,
            String graduationYear,
            String company,
            String jobRole,
            String skills,
            String bio) {

        this.alumniId = alumniId;
        this.alumniName = alumniName;
        this.department = department;
        this.graduationYear = graduationYear;
        this.company = company;
        this.jobRole = jobRole;
        this.skills = skills;
        this.bio = bio;

        setTitle("Campus Connect | Alumni Profile");
        setSize(1050, 740);
        setMinimumSize(new Dimension(850, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);
        root.setBorder(new EmptyBorder(25, 30, 25, 30));
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createProfileContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 22, 0));

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Alumni Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(dark);

        JLabel subtitle = new JLabel(
                "Learn from experience. Build meaningful connections.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setBorder(new EmptyBorder(7, 0, 0, 0));

        titles.add(title);
        titles.add(subtitle);

        JButton backButton = createSecondaryButton("← Back");
        backButton.addActionListener(e -> dispose());

        header.add(titles, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createProfileContent() {
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(285, 0));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.add(createIdentityCard());
        left.add(Box.createVerticalStrut(18));
        left.add(createContactCard());

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        right.add(createCareerCard());
        right.add(Box.createVerticalStrut(18));
        right.add(createSkillsCard());
        right.add(Box.createVerticalStrut(18));
        right.add(createAboutCard());

        JScrollPane scrollPane = new JScrollPane(right);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        content.add(left, BorderLayout.WEST);
        content.add(scrollPane, BorderLayout.CENTER);

        return content;
    }

    private JPanel createIdentityCard() {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avatar = new JLabel(getInitials(alumniName),
                SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(224, 231, 255));
        avatar.setForeground(primary);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 32));
        avatar.setPreferredSize(new Dimension(100, 100));
        avatar.setMinimumSize(new Dimension(100, 100));
        avatar.setMaximumSize(new Dimension(100, 100));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel(
                "<html><div style='text-align:center;width:220px'>"
                + escapeHtml(alumniName) + "</div></html>");
        name.setFont(new Font("SansSerif", Font.BOLD, 20));
        name.setForeground(dark);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setBorder(new EmptyBorder(17, 0, 7, 0));

        JLabel role = new JLabel(
                "<html><div style='text-align:center;width:220px'>"
                + escapeHtml(jobRole) + "</div></html>");
        role.setFont(new Font("SansSerif", Font.PLAIN, 13));
        role.setForeground(muted);
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel employer = new JLabel(
                "at " + company);
        employer.setFont(new Font("SansSerif", Font.BOLD, 13));
        employer.setForeground(primary);
        employer.setAlignmentX(Component.CENTER_ALIGNMENT);
        employer.setBorder(new EmptyBorder(8, 0, 14, 0));

        JLabel verified = new JLabel("Alumni Profile");
        verified.setOpaque(true);
        verified.setBackground(new Color(220, 252, 231));
        verified.setForeground(new Color(22, 101, 52));
        verified.setFont(new Font("SansSerif", Font.BOLD, 11));
        verified.setBorder(new EmptyBorder(7, 12, 7, 12));
        verified.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(avatar);
        card.add(name);
        card.add(role);
        card.add(employer);
        card.add(verified);
        card.add(Box.createVerticalStrut(10));

        return card;
    }

    private JPanel createContactCard() {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = sectionTitle("Academic Details");
        card.add(title);
        card.add(Box.createVerticalStrut(15));

        addDetail(card, "Department", department);
        addDetail(card, "Graduation Year", graduationYear);

        return card;
    }

    private JPanel createCareerCard() {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(sectionTitle("Professional Journey"));
        card.add(Box.createVerticalStrut(15));

        JPanel career = new JPanel(new BorderLayout(14, 0));
        career.setOpaque(false);

        JPanel marker = new JPanel();
        marker.setBackground(primary);
        marker.setPreferredSize(new Dimension(4, 60));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel position = new JLabel(jobRole);
        position.setFont(new Font("SansSerif", Font.BOLD, 15));
        position.setForeground(dark);

        JLabel organisation = new JLabel(company);
        organisation.setFont(new Font("SansSerif", Font.PLAIN, 13));
        organisation.setForeground(primary);
        organisation.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel period = new JLabel("Current position");
        period.setFont(new Font("SansSerif", Font.PLAIN, 11));
        period.setForeground(muted);
        period.setBorder(new EmptyBorder(5, 0, 0, 0));

        details.add(position);
        details.add(organisation);
        details.add(period);

        career.add(marker, BorderLayout.WEST);
        career.add(details, BorderLayout.CENTER);

        card.add(career);

        return card;
    }

    private JPanel createSkillsCard() {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(sectionTitle("Skills & Expertise"));
        card.add(Box.createVerticalStrut(13));

        JPanel tags = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 8, 7));
        tags.setOpaque(false);

        if (skills != null && !skills.trim().isEmpty()) {
            String[] skillList = skills.split(",");

            for (String skill : skillList) {
                String value = skill.trim();

                if (!value.isEmpty()) {
                    JLabel tag = new JLabel(value);
                    tag.setOpaque(true);
                    tag.setBackground(new Color(238, 242, 255));
                    tag.setForeground(primary);
                    tag.setFont(new Font("SansSerif", Font.BOLD, 11));
                    tag.setBorder(new EmptyBorder(7, 10, 7, 10));
                    tags.add(tag);
                }
            }
        }

        card.add(tags);

        return card;
    }

    private JPanel createAboutCard() {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(sectionTitle("About"));
        card.add(Box.createVerticalStrut(12));

        JTextArea description = new JTextArea(
                bio == null ? "" : bio);
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(muted);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setFocusable(false);
        description.setOpaque(false);
        description.setRows(5);

        card.add(description);

        return card;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(230, 234, 242)),
                new EmptyBorder(20, 20, 20, 20)));

        return card;
    }

    private JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 17));
        label.setForeground(dark);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private void addDetail(JPanel panel, String label, String value) {
        JLabel heading = new JLabel(label);
        heading.setFont(new Font("SansSerif", Font.PLAIN, 11));
        heading.setForeground(muted);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel detail = new JLabel(
                value == null || value.trim().isEmpty()
                        ? "Not provided" : value);
        detail.setFont(new Font("SansSerif", Font.BOLD, 13));
        detail.setForeground(dark);
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);
        detail.setBorder(new EmptyBorder(5, 0, 14, 0));

        panel.add(heading);
        panel.add(detail);
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(primary);
        button.setBackground(white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 224, 235)),
                new EmptyBorder(9, 14, 9, 14)));
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        return button;
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "A";
        }

        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }

        return (parts[0].substring(0, 1)
                + parts[parts.length - 1].substring(0, 1))
                .toUpperCase();
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    public String getAlumniId() {
        return alumniId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AlumniProfileFrame().setVisible(true));
    }
}
