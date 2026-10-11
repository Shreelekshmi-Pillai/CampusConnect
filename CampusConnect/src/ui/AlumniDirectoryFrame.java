
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AlumniDirectoryFrame extends JFrame {

    private final Color background = new Color(245, 247, 251);
    private final Color primary = new Color(79, 70, 229);
    private final Color dark = new Color(30, 41, 59);
    private final Color muted = new Color(100, 116, 139);
    private final Color white = Color.WHITE;

    private JTextField searchField;
    private JComboBox<String> departmentFilter;
    private JComboBox<String> companyFilter;
    private JTable alumniTable;
    private DefaultTableModel tableModel;

    private final List<Alumni> alumniList = new ArrayList<>();

    public AlumniDirectoryFrame() {
        setTitle("Campus Connect | Alumni Directory");
        setSize(1150, 720);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        loadSampleData();
        refreshTable();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);
        root.setBorder(new EmptyBorder(25, 30, 25, 30));
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 25, 0));

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Alumni Directory");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(dark);

        JLabel subtitle = new JLabel(
                "Connect with graduates, explore careers, and find mentors.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setBorder(new EmptyBorder(8, 0, 0, 0));

        titles.add(title);
        titles.add(subtitle);

        JButton backButton = new JButton("← Back to Dashboard");
        styleSecondaryButton(backButton);
        backButton.addActionListener(e -> dispose());

        header.add(titles, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(0, 18));
        main.setOpaque(false);

        main.add(createSearchPanel(), BorderLayout.NORTH);
        main.add(createDirectoryPanel(), BorderLayout.CENTER);

        return main;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(white);
        panel.setBorder(new EmptyBorder(18, 20, 18, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Find the right connection");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setForeground(dark);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(heading);
        panel.add(Box.createVerticalStrut(14));

        JPanel filters = new JPanel(new GridBagLayout());
        filters.setOpaque(false);
        filters.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setToolTipText(
                "Search name, company, job role, or expertise");

        departmentFilter = new JComboBox<>(new String[]{
                "All Departments",
                "Computer Science",
                "Electronics",
                "Electrical",
                "Mechanical",
                "Civil",
                "Other"
        });

        companyFilter = new JComboBox<>(new String[]{
                "All Companies",
                "Google",
                "Microsoft",
                "TCS",
                "Infosys",
                "Other"
        });

        styleComboBox(departmentFilter);
        styleComboBox(companyFilter);

        JButton searchButton = createPrimaryButton("Search");
        JButton resetButton = createSecondaryButton("Reset");

        gbc.gridx = 0;
        gbc.weightx = 2;
        filters.add(searchField, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        filters.add(departmentFilter, gbc);

        gbc.gridx = 2;
        filters.add(companyFilter, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        filters.add(searchButton, gbc);

        gbc.gridx = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        filters.add(resetButton, gbc);

        panel.add(filters);

        searchButton.addActionListener(e -> refreshTable());

        searchField.addActionListener(e -> refreshTable());

        departmentFilter.addActionListener(e -> refreshTable());
        companyFilter.addActionListener(e -> refreshTable());

        resetButton.addActionListener(e -> {
            searchField.setText("");
            departmentFilter.setSelectedIndex(0);
            companyFilter.setSelectedIndex(0);
            refreshTable();
        });

        return panel;
    }

    private JPanel createDirectoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(white);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Discover Alumni");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(dark);

        JLabel count = new JLabel("Search results");
        count.setFont(new Font("SansSerif", Font.PLAIN, 12));
        count.setForeground(muted);

        header.add(title, BorderLayout.WEST);
        header.add(count, BorderLayout.EAST);

        String[] columns = {
                "Name", "Department", "Company",
                "Job Role", "Expertise"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        alumniTable = new JTable(tableModel);
        alumniTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        alumniTable.setForeground(dark);
        alumniTable.setRowHeight(42);
        alumniTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        alumniTable.setShowVerticalLines(false);
        alumniTable.setGridColor(new Color(235, 238, 244));
        alumniTable.setSelectionBackground(
                new Color(224, 231, 255));
        alumniTable.setSelectionForeground(dark);
        alumniTable.setFillsViewportHeight(true);

        alumniTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        alumniTable.getTableHeader().setBackground(
                new Color(241, 245, 249));
        alumniTable.getTableHeader().setForeground(dark);
        alumniTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40));
        alumniTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(alumniTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(230, 234, 242)));

        JPanel bottom = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 10, 0));
        bottom.setOpaque(false);

        JButton viewButton = createPrimaryButton("View Profile");
        JButton mentorButton = createSecondaryButton("Request Mentorship");

        bottom.add(viewButton);
        bottom.add(mentorButton);

        viewButton.addActionListener(e -> viewSelectedAlumni());
        mentorButton.addActionListener(e -> requestMentorship());

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // Sample data for UI testing only.
    // Replace this method with database retrieval before deployment.

    private void loadSampleData() {
        alumniList.clear();

        alumniList.add(new Alumni(
                "Arun Kumar", "Computer Science",
                "Google", "Software Engineer",
                "Java, Backend Development"));

        alumniList.add(new Alumni(
                "Anjali Menon", "Computer Science",
                "Microsoft", "Cloud Engineer",
                "Cloud Computing, Azure"));

        alumniList.add(new Alumni(
                "Rahul Nair", "Electronics",
                "TCS", "Systems Engineer",
                "Embedded Systems, IoT"));

        alumniList.add(new Alumni(
                "Meera Thomas", "Computer Science",
                "Infosys", "Data Analyst",
                "Python, Data Analytics"));

        alumniList.add(new Alumni(
                "Nikhil Raj", "Electrical",
                "Other", "Automation Engineer",
                "Automation, Control Systems"));

        alumniList.add(new Alumni(
                "Sneha S", "Mechanical",
                "Other", "Design Engineer",
                "CAD, Product Design"));
    }

    private void refreshTable() {
        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        String query = searchField.getText()
                .trim().toLowerCase();

        String selectedDepartment =
                (String) departmentFilter.getSelectedItem();

        String selectedCompany =
                (String) companyFilter.getSelectedItem();

        int resultCount = 0;

        for (Alumni alumni : alumniList) {

            boolean matchesSearch =
                    alumni.name.toLowerCase().contains(query)
                    || alumni.company.toLowerCase().contains(query)
                    || alumni.jobRole.toLowerCase().contains(query)
                    || alumni.expertise.toLowerCase().contains(query)
                    || alumni.department.toLowerCase().contains(query);

            boolean matchesDepartment =
                    "All Departments".equals(selectedDepartment)
                    || alumni.department.equalsIgnoreCase(
                            selectedDepartment);

            boolean matchesCompany =
                    "All Companies".equals(selectedCompany)
                    || alumni.company.equalsIgnoreCase(
                            selectedCompany);

            if (matchesSearch && matchesDepartment && matchesCompany) {
                tableModel.addRow(new Object[]{
                        alumni.name,
                        alumni.department,
                        alumni.company,
                        alumni.jobRole,
                        alumni.expertise
                });

                resultCount++;
            }
        }

        setTitle("Campus Connect | Alumni Directory ("
                + resultCount + " results)");
    }

    private void viewSelectedAlumni() {
        int selectedRow = alumniTable.getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Please select an alumnus to view their profile.");
            return;
        }

        int modelRow = alumniTable.convertRowIndexToModel(selectedRow);

        String name = tableModel.getValueAt(modelRow, 0).toString();
        String department = tableModel.getValueAt(modelRow, 1).toString();
        String company = tableModel.getValueAt(modelRow, 2).toString();
        String role = tableModel.getValueAt(modelRow, 3).toString();
        String expertise = tableModel.getValueAt(modelRow, 4).toString();

        JPanel profile = new JPanel(new GridLayout(0, 1, 5, 5));
        profile.add(new JLabel("Name: " + name));
        profile.add(new JLabel("Department: " + department));
        profile.add(new JLabel("Company: " + company));
        profile.add(new JLabel("Job role: " + role));
        profile.add(new JLabel("Expertise: " + expertise));

        JOptionPane.showMessageDialog(
                this, profile, "Alumni Profile",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void requestMentorship() {
        int selectedRow = alumniTable.getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Please select an alumnus first.");
            return;
        }

        int modelRow = alumniTable.convertRowIndexToModel(selectedRow);
        String name = tableModel.getValueAt(modelRow, 0).toString();

        String message = JOptionPane.showInputDialog(
                this,
                "Write a short message explaining why you want mentorship:",
                "Request Mentorship from " + name,
                JOptionPane.PLAIN_MESSAGE);

        if (message == null) {
            return;
        }

        if (message.trim().isEmpty()) {
            showMessage("Please enter a message before submitting.");
            return;
        }

        showMessage(
                "Your message has been entered.\n\n"
                + "To submit a real request, connect this action "
                + "to MentorshipDAO and save it in MySQL.");
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(white);
        button.setBackground(primary);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(11, 15, 11, 15));
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        styleSecondaryButton(button);
        return button;
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(primary);
        button.setBackground(white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 224, 235)),
                new EmptyBorder(9, 12, 9, 12)));
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setBackground(white);
        combo.setForeground(dark);
        combo.setPreferredSize(new Dimension(145, 38));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Campus Connect",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static class Alumni {
        private final String name;
        private final String department;
        private final String company;
        private final String jobRole;
        private final String expertise;

        Alumni(String name, String department, String company,
               String jobRole, String expertise) {
            this.name = name;
            this.department = department;
            this.company = company;
            this.jobRole = jobRole;
            this.expertise = expertise;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AlumniDirectoryFrame().setVisible(true));
    }
}
