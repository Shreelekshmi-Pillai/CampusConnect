package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class StudentDAO {
    public Map<String,Object> getStudentProfile(int userId) {
        String sql = "SELECT u.user_id,u.name,u.email,p.department,p.semester,p.skills,p.career_goal FROM users u JOIN student_profiles p ON p.user_id=u.user_id WHERE u.user_id=?";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,userId); try(ResultSet rs=ps.executeQuery()) { if(rs.next()) return row(rs); }
        } catch(SQLException e){ System.err.println("getStudentProfile: "+e.getMessage()); }
        return null;
    }
    public boolean saveStudentProfile(int userId,String department,int semester,String skills,String careerGoal) {
        String sql="INSERT INTO student_profiles(user_id,department,semester,skills,career_goal) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE department=VALUES(department),semester=VALUES(semester),skills=VALUES(skills),career_goal=VALUES(career_goal)";
        try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,userId);ps.setString(2,department);ps.setInt(3,semester);ps.setString(4,skills);ps.setString(5,careerGoal);return ps.executeUpdate()>0;
        }catch(SQLException e){System.err.println("saveStudentProfile: "+e.getMessage());return false;}
    }
    public List<Map<String,Object>> getAllStudents(){
        List<Map<String,Object>> out=new ArrayList<>(); String sql="SELECT u.user_id,u.name,u.email,u.status,p.department,p.semester,p.skills,p.career_goal FROM users u JOIN student_profiles p ON p.user_id=u.user_id ORDER BY u.name";
        try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next())out.add(row(rs));}catch(SQLException e){System.err.println("getAllStudents: "+e.getMessage());}return out;
    }
    private Map<String,Object> row(ResultSet rs)throws SQLException{Map<String,Object> m=new LinkedHashMap<>();ResultSetMetaData md=rs.getMetaData();for(int i=1;i<=md.getColumnCount();i++)m.put(md.getColumnLabel(i),rs.getObject(i));return m;}
}
