package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class MentorshipDAO {
    public boolean sendRequest(int studentId,int alumniId,String message){
        String sql="INSERT INTO mentorship_requests(student_id,alumni_id,message,status) SELECT ?,?,?, 'PENDING' WHERE EXISTS(SELECT 1 FROM student_profiles WHERE user_id=?) AND EXISTS(SELECT 1 FROM alumni_profiles WHERE user_id=? AND verification_status='VERIFIED')";
        try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,studentId);ps.setInt(2,alumniId);ps.setString(3,message);ps.setInt(4,studentId);ps.setInt(5,alumniId);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("sendRequest: "+e.getMessage());return false;}
    }
    // Each row: [request_id, student_id, student_name, alumni_id, alumni_name, message, status, created_at]
    public List<String[]> getRequestsForAlumni(int alumniId){return requests("r.alumni_id=?",alumniId);}
    public List<String[]> getRequestsForStudent(int studentId){return requests("r.student_id=?",studentId);}
    private List<String[]> requests(String condition,int id){List<String[]> list=new ArrayList<>();String sql="SELECT r.request_id,r.student_id,su.name student_name,r.alumni_id,au.name alumni_name,r.message,r.status,r.created_at FROM mentorship_requests r JOIN users su ON su.user_id=r.student_id JOIN users au ON au.user_id=r.alumni_id WHERE "+condition+" ORDER BY r.created_at DESC";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(new String[]{rs.getString("request_id"),rs.getString("student_id"),rs.getString("student_name"),rs.getString("alumni_id"),rs.getString("alumni_name"),rs.getString("message"),rs.getString("status"),String.valueOf(rs.getTimestamp("created_at"))});}}catch(SQLException e){System.err.println("requests: "+e.getMessage());}return list;}
    public boolean updateRequestStatus(int requestId,String status){if(!List.of("ACCEPTED","REJECTED").contains(status==null?"":status.toUpperCase()))return false;String sql="UPDATE mentorship_requests SET status=? WHERE request_id=? AND status='PENDING'";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,status.toUpperCase());ps.setInt(2,requestId);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("updateRequestStatus: "+e.getMessage());return false;}}
}
