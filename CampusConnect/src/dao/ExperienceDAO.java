package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ExperienceDAO {
    public List<Map<String,Object>> getAllPosts(){List<Map<String,Object>> out=new ArrayList<>();String sql="SELECT e.post_id,e.alumni_id,u.name alumni_name,e.company,e.role,e.content,e.created_at FROM experience_posts e JOIN users u ON u.user_id=e.alumni_id JOIN alumni_profiles a ON a.user_id=e.alumni_id WHERE u.status='ACTIVE' AND a.verification_status='VERIFIED' ORDER BY e.created_at DESC";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next()){Map<String,Object> m=new LinkedHashMap<>();ResultSetMetaData md=rs.getMetaData();for(int i=1;i<=md.getColumnCount();i++)m.put(md.getColumnLabel(i),rs.getObject(i));out.add(m);}}catch(SQLException e){System.err.println("getAllPosts: "+e.getMessage());}return out;}
    public boolean addPost(int alumniId,String company,String role,String content){String sql="INSERT INTO experience_posts(alumni_id,company,role,content) SELECT ?,?,?,? WHERE EXISTS(SELECT 1 FROM alumni_profiles WHERE user_id=? AND verification_status='VERIFIED')";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,alumniId);ps.setString(2,company);ps.setString(3,role);ps.setString(4,content);ps.setInt(5,alumniId);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("addPost: "+e.getMessage());return false;}}
}
