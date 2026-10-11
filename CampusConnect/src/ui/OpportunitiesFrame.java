package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OpportunitiesFrame extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;
    private final Color borderColor = new Color(226, 232, 240);

    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> typeFilter;
    private JTable opportunityTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    private final Object[][] opportunities = {
        {
            "Software Development Intern",
            "Google",
            "Internship",
            "Computer Science",
            "Bengaluru",
            "Open"
        },
        {
            "Java Developer Intern",
            "Infosys",
            "Internship",
            "Computer Science",
            "Kochi",
            "Open"
        },
        {
            "Data Analyst",
            "TCS",
            "Full-time",
            "Computer Science",
            "Thiruvananthapuram",
            "Open"
        },
        {
            "Embedded Systems Intern",
            "Qualcomm",
            "Internship",
            "Electronics",
            "Bengaluru",
            "Open"
        },
        {
            "Graduate Engineer Trainee",
            "L&T",
            "Full-time",
            "Electrical",
            "Chennai",
            "Open"
        },
        {
            "Mechanical Design Intern",
            "Bosch",
            "Internship",
            "Mechanical",
            "Coimbatore",
            "Open"
        },
        {
            "Web Development Intern",
            "Startup Opportunity",
            "Internship",
            "Computer Science",
            "Remote",
            "Open"
        }
    };

    public OpportunitiesFrame() {
        setTitle("Campus Connect | Opportunities");
        setSize(1120, 730);
        setMinimumSize(new Dimension(900, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        loadOpportunities();
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

        JLabel title = new JLabel("Career Opportunities");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(dark);

        JLabel subtitle = new JLabel(
                "Discover internships and jobs shared by your alumni network.");
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
        introText.setLayout(new BoxLayout(
                introText, BoxLayout.Y_AXIS));
        introText.setOpaque(false);

        JLabel introTitle = new JLabel(
                "Find an opportunity that moves you forward.");
        introTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        introTitle.setForeground(dark);

        JLabel introSubtitle = new JLabel(
                "Explore roles, build experience and take your next step.");
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

        searchField = new JTextField(18);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(220, 38));
        searchField.setToolTipText(
                "Search by role, company or location");

        categoryFilter = new JComboBox<>(new String[] {
                "All Departments",
                "Computer Science",
                "Electronics",
                "Electrical",
                "Mechanical",
                "Civil"
        });
        categoryFilter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        categoryFilter.setPreferredSize(new Dimension(155, 38));

        typeFilter = new JComboBox<>(new String[] {
                "All Types",
                "Internship",
                "Full-time"
        });
        typeFilter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        typeFilter.setPreferredSize(new Dimension(120, 38));

        JButton searchButton = createButton(
                "Search", primary, white);

        filterPanel.add(searchField);
        filterPanel.add(categoryFilter);
        filterPanel.add(typeFilter);
        filterPanel.add(searchButton);

        JPanel upperContent = new JPanel();
        upperContent.setLayout(new BoxLayout(
                upperContent, BoxLayout.Y_AXIS));
        upperContent.setBackground(background);

        upperContent.add(introCard);
        upperContent.add(Box.createVerticalStrut(20));
        upperContent.add(filterPanel);

        // Opportunity table
        String[] columns = {
            "Opportunity",
            "Company",
            "Type",
            "Department",
            "Location",
            "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        opportunityTable = new JTable(tableModel);
        opportunityTable.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        opportunityTable.setRowHeight(43);
        opportunityTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        opportunityTable.setShowGrid(false);
        opportunityTable.setIntercellSpacing(new Dimension(0, 0));
        opportunityTable.setSelectionBackground(
                new Color(224, 231, 255));
        opportunityTable.setSelectionForeground(dark);
        opportunityTable.setFillsViewportHeight(true);

        opportunityTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        opportunityTable.getTableHeader().setBackground(
                new Color(248, 250, 252));
        opportunityTable.getTableHeader().setForeground(muted);
        opportunityTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40));
        opportunityTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(opportunityTable);
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

        statusLabel = new JLabel("Browse the available opportunities.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(muted);

        JPanel actionButtons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtons.setOpaque(false);

        JButton detailsButton = createButton(
                "View Details", white, dark);
        JButton applyButton = createButton(
                "Apply / Contact", primary, white);

        detailsButton.addActionListener(e -> viewDetails());
        applyButton.addActionListener(e -> applyForOpportunity());

        actionButtons.add(detailsButton);
        actionButtons.add(applyButton);

        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(actionButtons, BorderLayout.EAST);

        JPanel lowerContent = new JPanel(new BorderLayout());
        lowerContent.setBackground(background);
        lowerContent.add(tableCard, BorderLayout.CENTER);
        lowerContent.add(bottomPanel, BorderLayout.SOUTH);

        content.add(upperContent, BorderLayout.NORTH);
        content.add(lowerContent, BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);

        // Events
        searchButton.addActionListener(e -> filterOpportunities());
        searchField.addActionListener(e -> filterOpportunities());
        categoryFilter.addActionListener(e -> filterOpportunities());
        typeFilter.addActionListener(e -> filterOpportunities());
    }

    private void loadOpportunities() {
        tableModel.setRowCount(0);

        for (Object[] opportunity : opportunities) {
            tableModel.addRow(opportunity);
        }

        statusLabel.setText(
                opportunities.length + " opportunities listed.");
    }

    private void filterOpportunities() {
        String search = searchField.getText()
                .trim().toLowerCase();

        String department = (String)
                categoryFilter.getSelectedItem();

        String type = (String) typeFilter.getSelectedItem();

        tableModel.setRowCount(0);
        int count = 0;

        for (Object[] opportunity : opportunities) {
            String role = opportunity[0].toString();
            String company = opportunity[1].toString();
            String opportunityType = opportunity[2].toString();
            String dept = opportunity[3].toString();
            String location = opportunity[4].toString();

            boolean matchesSearch =
                    role.toLowerCase().contains(search)
                    || company.toLowerCase().contains(search)
                    || location.toLowerCase().contains(search)
                    || dept.toLowerCase().contains(search);

            boolean matchesDepartment =
                    "All Departments".equals(department)
                    || dept.equals(department);

            boolean matchesType =
                    "All Types".equals(type)
                    || opportunityType.equals(type);

            if (matchesSearch && matchesDepartment && matchesType) {
                tableModel.addRow(opportunity);
                count++;
            }
        }

        statusLabel.setText(count + " opportunity/opportunities found.");
    }

    private void viewDetails() {
        int row = opportunityTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an opportunity first.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String role = tableModel.getValueAt(row, 0).toString();
        String company = tableModel.getValueAt(row, 1).toString();
        String type = tableModel.getValueAt(row, 2).toString();
        String department = tableModel.getValueAt(row, 3).toString();
        String location = tableModel.getValueAt(row, 4).toString();
        String status = tableModel.getValueAt(row, 5).toString();

        String details =
                "POSITION DETAILS\n\n"
                + "Role: " + role + "\n"
                + "Company: " + company + "\n"
                + "Type: " + type + "\n"
                + "Department: " + department + "\n"
                + "Location: " + location + "\n"
                + "Status: " + status + "\n\n"
                + "Description:\n"
                + "This is a sample opportunity listing. "
                + "Eligibility, responsibilities, deadlines "
                + "and application instructions should be added "
                + "when the opportunity is posted by an alumnus.";

        JOptionPane.showMessageDialog(
                this,
                details,
                "Opportunity Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void applyForOpportunity() {
        int row = opportunityTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an opportunity first.",
                    "No Selection",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String role = tableModel.getValueAt(row, 0).toString();
        String company = tableModel.getValueAt(row, 1).toString();
        String status = tableModel.getValueAt(row, 5).toString();

        if (!"Open".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(
                    this,
                    "This opportunity is no longer open.",
                    "Applications Closed",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea messageArea = new JTextArea(5, 28);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageArea.setText(
                "Hello, I am interested in the "
                + role + " opportunity. Please share the "
                + "application process and further details.");

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(new JLabel(
                "Contact the opportunity poster about " + role
                + " at " + company + ":"),
                BorderLayout.NORTH);
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Apply / Contact",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (messageArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a message.",
                        "Message Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Application/contact database integration comes later.
            JOptionPane.showMessageDialog(
                    this,
                    "Your message is ready!\n\n"
                    + "Opportunity: " + role
                    + "\nCompany: " + company
                    + "\n\nDatabase integration is not connected yet.",
                    "Application / Contact",
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
                new OpportunitiesFrame().setVisible(true));
    }
}