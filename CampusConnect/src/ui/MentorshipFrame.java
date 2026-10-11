package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MentorshipFrame extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;
    private final Color borderColor = new Color(226, 232, 240);

    private JTable mentorTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> departmentFilter;
    private JLabel statusLabel;

    public MentorshipFrame() {
        setTitle("Campus Connect | Mentorship");
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        loadSampleMentors();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(white);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, borderColor),
                new EmptyBorder(18, 26, 18, 26)));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(
                headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);

        JLabel title = new JLabel("Find a Mentor");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(dark);

        JLabel subtitle = new JLabel(
                "Learn from alumni who have already taken the next step.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(muted);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        JButton backButton = createButton("Back", white, dark);
        backButton.addActionListener(e -> dispose());

        header.add(headingPanel, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(background);
        content.setBorder(new EmptyBorder(22, 26, 22, 26));

        // Intro card
        JPanel introCard = new JPanel(new BorderLayout(15, 0));
        introCard.setBackground(new Color(238, 242, 255));
        introCard.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel introText = new JPanel();
        introText.setLayout(new BoxLayout(introText, BoxLayout.Y_AXIS));
        introText.setOpaque(false);

        JLabel introTitle = new JLabel(
                "Your next step starts with a conversation.");
        introTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        introTitle.setForeground(dark);

        JLabel introSubtitle = new JLabel(
                "Connect with alumni for career advice, skills and guidance.");
        introSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        introSubtitle.setForeground(muted);

        introText.add(introTitle);
        introText.add(Box.createVerticalStrut(7));
        introText.add(introSubtitle);

        JLabel icon = new JLabel("↗");
        icon.setFont(new Font("SansSerif", Font.BOLD, 34));
        icon.setForeground(primary);

        introCard.add(introText, BorderLayout.CENTER);
        introCard.add(icon, BorderLayout.EAST);

        // Search and filters
        JPanel filterPanel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(background);

        searchField = new JTextField(22);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(240, 38));
        searchField.setToolTipText("Search name, company or expertise");

        departmentFilter = new JComboBox<>(new String[] {
                "All Departments",
                "Computer Science",
                "Electronics",
                "Electrical",
                "Mechanical",
                "Civil"
        });
        departmentFilter.setFont(new Font("SansSerif", Font.PLAIN, 13));
        departmentFilter.setPreferredSize(new Dimension(170, 38));

        JButton searchButton = createButton(
                "Search", primary, white);

        filterPanel.add(searchField);
        filterPanel.add(departmentFilter);
        filterPanel.add(searchButton);

        JPanel upperContent = new JPanel();
        upperContent.setLayout(new BoxLayout(
                upperContent, BoxLayout.Y_AXIS));
        upperContent.setBackground(background);

        upperContent.add(introCard);
        upperContent.add(Box.createVerticalStrut(20));
        upperContent.add(filterPanel);

        // Mentor table
        String[] columns = {
                "Alumni Name",
                "Department",
                "Company",
                "Job Role",
                "Expertise"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        mentorTable = new JTable(tableModel);
        mentorTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        mentorTable.setRowHeight(42);
        mentorTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        mentorTable.setShowGrid(false);
        mentorTable.setIntercellSpacing(new Dimension(0, 0));
        mentorTable.setSelectionBackground(new Color(224, 231, 255));
        mentorTable.setSelectionForeground(dark);
        mentorTable.setFillsViewportHeight(true);

        mentorTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        mentorTable.getTableHeader().setBackground(
                new Color(248, 250, 252));
        mentorTable.getTableHeader().setForeground(muted);
        mentorTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40));
        mentorTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(mentorTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        scrollPane.getViewport().setBackground(white);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(white);
        tableCard.setBorder(BorderFactory.createLineBorder(borderColor));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Bottom actions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(background);
        bottomPanel.setBorder(new EmptyBorder(12, 0, 0, 0));

        statusLabel = new JLabel("Select an alumnus to continue.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(muted);

        JPanel actionButtons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtons.setOpaque(false);

        JButton profileButton = createButton(
                "View Profile", white, dark);
        JButton requestButton = createButton(
                "Request Mentorship", primary, white);

        profileButton.addActionListener(e -> viewProfile());
        requestButton.addActionListener(e -> requestMentorship());

        actionButtons.add(profileButton);
        actionButtons.add(requestButton);

        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(actionButtons, BorderLayout.EAST);

        JPanel lowerContent = new JPanel(new BorderLayout());
        lowerContent.setBackground(background);
        lowerContent.add(tableCard, BorderLayout.CENTER);
        lowerContent.add(bottomPanel, BorderLayout.SOUTH);

        content.add(upperContent, BorderLayout.NORTH);
        content.add(lowerContent, BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);

        searchButton.addActionListener(e -> filterMentors());
        searchField.addActionListener(e -> filterMentors());
        departmentFilter.addActionListener(e -> filterMentors());
    }

    private void loadSampleMentors() {
        addMentor("Arun Kumar", "Computer Science",
                "Google", "Software Engineer",
                "Java, DSA, Interview Preparation");

        addMentor("Anjali Menon", "Computer Science",
                "Infosys", "Systems Engineer",
                "Web Development, SQL, Career Planning");

        addMentor("Rahul Nair", "Electronics",
                "Qualcomm", "Embedded Engineer",
                "Embedded Systems, C, Electronics");

        addMentor("Meera S", "Electrical",
                "L&T", "Design Engineer",
                "Electrical Design, Projects, Interview Tips");

        addMentor("Nikhil Das", "Computer Science",
                "TCS", "Data Analyst",
                "Python, SQL, Data Analytics");

        addMentor("Sneha Thomas", "Mechanical",
                "Bosch", "Graduate Engineer",
                "CAD, Manufacturing, Resume Guidance");
    }

    private void addMentor(String name, String department,
                           String company, String role,
                           String expertise) {
        tableModel.addRow(new Object[] {
                name, department, company, role, expertise
        });
    }

    private void filterMentors() {
        String search = searchField.getText()
                .trim().toLowerCase();

        String department = (String)
                departmentFilter.getSelectedItem();

        tableModel.setRowCount(0);

        Object[][] mentors = {
            {"Arun Kumar", "Computer Science", "Google",
                    "Software Engineer",
                    "Java, DSA, Interview Preparation"},
            {"Anjali Menon", "Computer Science", "Infosys",
                    "Systems Engineer",
                    "Web Development, SQL, Career Planning"},
            {"Rahul Nair", "Electronics", "Qualcomm",
                    "Embedded Engineer",
                    "Embedded Systems, C, Electronics"},
            {"Meera S", "Electrical", "L&T",
                    "Design Engineer",
                    "Electrical Design, Projects, Interview Tips"},
            {"Nikhil Das", "Computer Science", "TCS",
                    "Data Analyst",
                    "Python, SQL, Data Analytics"},
            {"Sneha Thomas", "Mechanical", "Bosch",
                    "Graduate Engineer",
                    "CAD, Manufacturing, Resume Guidance"}
        };

        int count = 0;

        for (Object[] mentor : mentors) {
            String name = mentor[0].toString();
            String dept = mentor[1].toString();
            String company = mentor[2].toString();
            String role = mentor[3].toString();
            String expertise = mentor[4].toString();

            boolean matchesSearch =
                    name.toLowerCase().contains(search)
                    || dept.toLowerCase().contains(search)
                    || company.toLowerCase().contains(search)
                    || role.toLowerCase().contains(search)
                    || expertise.toLowerCase().contains(search);

            boolean matchesDepartment =
                    "All Departments".equals(department)
                    || dept.equals(department);

            if (matchesSearch && matchesDepartment) {
                tableModel.addRow(mentor);
                count++;
            }
        }

        statusLabel.setText(count + " mentor(s) found.");
    }

    private void viewProfile() {
        int row = mentorTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an alumnus first.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String name = tableModel.getValueAt(row, 0).toString();
        String department = tableModel.getValueAt(row, 1).toString();
        String company = tableModel.getValueAt(row, 2).toString();
        String role = tableModel.getValueAt(row, 3).toString();
        String expertise = tableModel.getValueAt(row, 4).toString();

        new AlumniProfileFrame(
                "1",
                name,
                department,
                "Not provided",
                company,
                role,
                expertise,
                "This alumni profile is provided as a sample. "
                + "Connect with this alumnus to learn more about "
                + "their career journey."
        ).setVisible(true);
    }

    private void requestMentorship() {
        int row = mentorTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an alumnus to request mentorship.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String name = tableModel.getValueAt(row, 0).toString();
        String company = tableModel.getValueAt(row, 2).toString();

        JTextArea messageArea = new JTextArea(5, 28);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageArea.setText(
                "Hello, I am a student interested in learning "
                + "more about your career path. Would you be "
                + "available to mentor me?");

        JScrollPane messageScroll = new JScrollPane(messageArea);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(new JLabel(
                "Send a mentorship request to " + name
                + " (" + company + "):"), BorderLayout.NORTH);
        panel.add(messageScroll, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Request Mentorship",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String message = messageArea.getText().trim();

            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a message before sending.",
                        "Message Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Database saving will be added using MentorshipDAO.
            JOptionPane.showMessageDialog(
                    this,
                    "Your request form is ready!\n\n"
                    + "Mentor: " + name
                    + "\n\nDatabase integration is not connected yet.",
                    "Mentorship Request",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(fg);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        bg.equals(white) ? borderColor : bg),
                new EmptyBorder(10, 15, 10, 15)));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MentorshipFrame().setVisible(true));
    }
}