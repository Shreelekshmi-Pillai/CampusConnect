package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class RoadmapDAO {
    public List<Map<String,Object>> getAllRoadmaps(){List<Map<String,Object>> out=new ArrayList<>();String sql="SELECT roadmap_id,title,target_role,steps FROM career_roadmaps ORDER BY title";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next()){Map<String,Object> m=new LinkedHashMap<>();m.put("roadmap_id",rs.getInt("roadmap_id"));m.put("title",rs.getString("title"));m.put("target_role",rs.getString("target_role"));m.put("steps",rs.getString("steps"));out.add(m);}}catch(SQLException e){System.err.println("getAllRoadmaps: "+e.getMessage());}return out;}
    public boolean addRoadmap(String title,String targetRole,String steps){String sql="INSERT INTO career_roadmaps(title,target_role,steps) VALUES(?,?,?)";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,title);ps.setString(2,targetRole);ps.setString(3,steps);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("addRoadmap: "+e.getMessage());return false;}}
    public boolean updateRoadmap(int id,String title,String targetRole,String steps){String sql="UPDATE career_roadmaps SET title=?,target_role=?,steps=? WHERE roadmap_id=?";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,title);ps.setString(2,targetRole);ps.setString(3,steps);ps.setInt(4,id);return ps.executeUpdate()==1;}catch(SQLException e){System.err.println("updateRoadmap: "+e.getMessage());return false;}}
}
