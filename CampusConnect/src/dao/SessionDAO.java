package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class SessionDAO {
    public boolean scheduleSession(int requestId, Timestamp sessionTime, String meetingMode, String meetingLink) {
        String sql="INSERT INTO mentorship_sessions(request_id,session_time,meeting_mode,meeting_link,status) SELECT ?,?,?,?,'SCHEDULED' WHERE EXISTS(SELECT 1 FROM mentorship_requests WHERE request_id=? AND status='ACCEPTED')";
        try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,requestId);ps.setTimestamp(2,sessionTime);ps.setString(3,meetingMode.toUpperCase());ps.setString(4,meetingLink);ps.setInt(5,requestId);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("scheduleSession: "+e.getMessage());return false;}
    }
    public List<Map<String,Object>> getSessionsForUser(int userId){String sql="SELECT ms.*,mr.student_id,mr.alumni_id,su.name student_name,au.name alumni_name FROM mentorship_sessions ms JOIN mentorship_requests mr ON mr.request_id=ms.request_id JOIN users su ON su.user_id=mr.student_id JOIN users au ON au.user_id=mr.alumni_id WHERE mr.student_id=? OR mr.alumni_id=? ORDER BY ms.session_time DESC";return query(sql,userId,userId);}
    public boolean updateSessionStatus(int sessionId,String status){if(!List.of("SCHEDULED","COMPLETED","CANCELLED").contains(status==null?"":status.toUpperCase()))return false;try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement("UPDATE mentorship_sessions SET status=? WHERE session_id=?")){ps.setString(1,status.toUpperCase());ps.setInt(2,sessionId);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("updateSessionStatus: "+e.getMessage());return false;}}
    private List<Map<String,Object>> query(String sql,int a,int b){List<Map<String,Object>> out=new ArrayList<>();try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,a);ps.setInt(2,b);try(ResultSet rs=ps.executeQuery()){while(rs.next()){Map<String,Object> m=new LinkedHashMap<>();ResultSetMetaData md=rs.getMetaData();for(int i=1;i<=md.getColumnCount();i++)m.put(md.getColumnLabel(i),rs.getObject(i));out.add(m);}}}catch(SQLException e){System.err.println("getSessionsForUser: "+e.getMessage());}return out;}
}
