package ui;

import dao.AlumniDAO;
import dao.MentorshipDAO;
import dao.OpportunityDAO;
import model.User;
import service.AdminService;
import util.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/** Minimal role-aware landing page while individual feature screens are being developed. */
public class RoleDashboardFrame extends JFrame {
    private final User user;
    private final Color navy = new Color(20, 43, 74);
    private final Color teal = new Color(13, 148, 136);

    public RoleDashboardFrame(User user) {
        this.user = user;
        setTitle("Campus Connect | " + user.getRole());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        build();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(18,18));
        root.setBorder(BorderFactory.createEmptyBorder(24,28,24,28));
        root.setBackground(new Color(248,250,252));
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false);
        JLabel title = new JLabel("Campus Connect"); title.setFont(new Font("SansSerif",Font.BOLD,26)); title.setForeground(navy);
        JLabel welcome = new JLabel("Welcome, " + user.getName() + "  •  " + user.getRole()); welcome.setForeground(new Color(71,85,105));
        JPanel labels = new JPanel(); labels.setOpaque(false); labels.setLayout(new BoxLayout(labels,BoxLayout.Y_AXIS)); labels.add(title); labels.add(Box.createVerticalStrut(6)); labels.add(welcome);
        JButton logout = new JButton("Log out"); logout.setBackground(teal); logout.setForeground(Color.WHITE); logout.setFocusPainted(false);
        logout.addActionListener(e -> { SessionManager.logout(); new LoginFrame().setVisible(true); dispose(); });
        header.add(labels,BorderLayout.WEST); header.add(logout,BorderLayout.EAST); root.add(header,BorderLayout.NORTH);
        JPanel actions = new JPanel(new GridLayout(0,2,14,14)); actions.setOpaque(false);
        if ("STUDENT".equals(user.getRole())) {
            addAction(actions,"Browse verified alumni",this::showAlumni);
            addAction(actions,"Browse opportunities",this::showOpportunities);
            addAction(actions,"My mentorship requests",this::showStudentRequests);
        } else if ("ALUMNI".equals(user.getRole())) {
            addAction(actions,"Mentorship requests",this::showAlumniRequests);
            addAction(actions,"Browse opportunities",this::showOpportunities);
        } else {
            addAction(actions,"Verify pending alumni",this::showPendingAlumni);
            addAction(actions,"View all accounts",this::showAllUsers);
        }
        JLabel note = new JLabel("Core account and database workflows are connected. Feature screens can be expanded from this dashboard.");
        note.setForeground(new Color(100,116,139));
        JPanel center = new JPanel(new BorderLayout(0,16)); center.setOpaque(false); center.add(actions,BorderLayout.CENTER); center.add(note,BorderLayout.SOUTH);
        root.add(center,BorderLayout.CENTER); setContentPane(root);
    }

    private void addAction(JPanel panel,String text,Runnable action) {
        JButton button = new JButton(text); button.setFont(new Font("SansSerif",Font.BOLD,15)); button.setForeground(navy); button.setBackground(Color.WHITE); button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)),BorderFactory.createEmptyBorder(24,20,24,20)));
        button.addActionListener(e -> action.run()); panel.add(button);
    }

    private void showAlumni() {
        List<String[]> rows = new AlumniDAO().getAllAlumni();
        showTable("Verified Alumni",new String[]{"User ID","Name","Email","Graduation Year","Company","Designation","Industry","Expertise"},rows);
    }
    private void showOpportunities() {
        List<String[]> rows = new OpportunityDAO().getAllOpportunities();
        showTable("Opportunities",new String[]{"ID","Title","Company","Type","Description","Eligibility","Deadline","Application URL","Alumni ID","Alumni Name"},rows);
    }
    private void showStudentRequests() {
        List<String[]> rows = new MentorshipDAO().getRequestsForStudent(user.getUserId());
        showTable("My Mentorship Requests",new String[]{"Request ID","Student ID","Student Name","Alumni ID","Alumni Name","Message","Status","Created At"},rows);
    }
    private void showAlumniRequests() {
        List<String[]> rows = new MentorshipDAO().getRequestsForAlumni(user.getUserId());
        if (rows.isEmpty()) { JOptionPane.showMessageDialog(this,"No mentorship requests found."); return; }
        String[] cols={"Request ID","Student ID","Student Name","Alumni ID","Alumni Name","Message","Status","Created At"};
        JTable table = new JTable(toModel(cols,rows)); JScrollPane pane=new JScrollPane(table); pane.setPreferredSize(new Dimension(900,300));
        Object[] options={"Accept selected","Reject selected","Close"}; int choice=JOptionPane.showOptionDialog(this,pane,"Mentorship Requests",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[2]);
        int selected=table.getSelectedRow(); if (selected>=0 && choice<2) { int requestId=Integer.parseInt(String.valueOf(table.getValueAt(selected,0))); boolean ok=new MentorshipDAO().updateRequestStatus(requestId,choice==0?"ACCEPTED":"REJECTED"); JOptionPane.showMessageDialog(this,ok?"Request updated.":"Could not update request."); }
    }
    private void showPendingAlumni() {
        List<String[]> rows=new AdminService().viewPendingAlumni();
        if(rows.isEmpty()){JOptionPane.showMessageDialog(this,"No pending alumni profiles.");return;}
        String[] cols={"User ID","Name","Email","Graduation Year","Company","Designation","Industry","Expertise"};
        JTable table=new JTable(toModel(cols,rows)); JScrollPane pane=new JScrollPane(table); pane.setPreferredSize(new Dimension(900,300));
        Object[] options={"Verify selected","Reject selected","Close"}; int choice=JOptionPane.showOptionDialog(this,pane,"Pending Alumni",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[2]);
        int selected=table.getSelectedRow(); if(selected>=0 && choice<2){int id=Integer.parseInt(String.valueOf(table.getValueAt(selected,0)));boolean ok=choice==0?new AdminService().verifyAlumni(id):new AdminService().rejectAlumni(id);JOptionPane.showMessageDialog(this,ok?"Alumni status updated.":"Could not update status.");}
    }
    private void showAllUsers() {
        List<Map<String,Object>> rows=new AdminService().viewAllUsers(); String[] cols={"user_id","name","email","role","status","verification_notes"};
        DefaultTableModel model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        for(Map<String,Object> row:rows){Object[] vals=new Object[cols.length];for(int i=0;i<cols.length;i++)vals[i]=row.get(cols[i]);model.addRow(vals);}
        JTable table=new JTable(model);table.setAutoCreateRowSorter(true);JOptionPane.showMessageDialog(this,new JScrollPane(table),"All Accounts",JOptionPane.PLAIN_MESSAGE);
    }
    private void showTable(String title,String[] cols,List<String[]> rows){JTable table=new JTable(toModel(cols,rows));table.setAutoCreateRowSorter(true);JOptionPane.showMessageDialog(this,new JScrollPane(table),title,JOptionPane.PLAIN_MESSAGE);}
    private DefaultTableModel toModel(String[] cols,List<String[]> rows){DefaultTableModel model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};for(String[] row:rows)model.addRow(row);return model;}
}
